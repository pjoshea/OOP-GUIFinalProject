/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: UAVMap
 * Purpose: Creates and holds all of the objects that appear in the simulation 
 *          or game. Creates the canvas. Provides methods which members can 
 *          call to affect other members or trigger systematic changes.
 */

package uavcontrols5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.Timer;


public class UAVMap implements ActionListener {
    private Main parent;
    private UAVCanvas canvas;
    private GameUAVMouseManager mm;
    private UAVPlane [] planeArray;
    private Timer timer;
    private boolean fullscreen;
    
    //delay is the number of milliseconds between redraws
    private double delay = 35; 
    private double fps = 1000/delay;
    private final UAVModes mode;
    
    //game-only objects
    private GameUAVPlayer player;
    private boolean aimMode = false;
    
    private int waveNumber;
    private int gamePlaneNumber;
    private int numDestroyedPlanes;
    
    private GameUAVLaser [] laserArray;
    private int laserNum;
        
    private Rectangle playArea;
    private GameUAVWalls walls;
    private final int WALL_DISTANCE = 50;
    
    protected boolean gamePaused = true;
    private double scoreNum;
    private String scoreText;
    private final int SCORE_DIGITS = 7;
    private final double PER_REFRESH_SCORE_INCREASE = .001;
    
    private final int INFO_LEFT_OFFSET = 20;
    private final int INFO_VERTICAL_OFFSET = 20;
    
    
    //simulation-only objects
    private SimUAVToolBar toolBar;
    private SimUAVMenu smenu;
    private int simPlaneNumber = 0;
    private boolean animate = false;
    
    /*
     * UAVMap
     * param: parent, program mode, indicator of whether or not the program is 
     *        in fullscreen mode
     * returns: UAVMap
     * function: constructor
     */
    public UAVMap(Main parent, UAVModes mode, boolean fs) {
        this.parent = parent;
        this.mode = mode;
        fullscreen = fs;
        canvas = new UAVCanvas(this, mode, fullscreen);
        
        if (mode == UAVModes.GAME) { 
            mm = new GameUAVMouseManager(this, canvas);
            playArea = new Rectangle(new Point(-1000, 1000), 
                                     new Dimension(2000, 2000));
            planeArray = new GameUAVPlane[16];
            laserArray = new GameUAVLaser[10];
            walls = new GameUAVWalls(this, canvas);
        } else {
            planeArray = new SimUAVPlane[10];
            animate = true;
        }
    }
    
    /*
     * actionPerformed
     * param: the tick fired by the Timer
     * return: N/A
     * function: separates the tick according to mode, then tells canvas 
     *           to redraw no matter what.
     * notes: tells canvas to redraw regardless because pausing works by not 
     *        propagating the timer's event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (mode == UAVModes.GAME && !gamePaused) { gameTick(); mm.tick();} 
        if (mode == UAVModes.SIMULATION && animate) { simulationTick(); } 
        canvas.redraw();
    }
    
    
/*******************************************************************************
 ******************************Simulation Methods*******************************
 ******************************************************************************/
    
    /*
     * initializeSimulation
     * param: N/A
     * return: N/A
     * function: creates a SimUAVPlaneCreatorWindow which the user fills in to 
     *           create the first plane in the simulation
     */
    public void initializeSimulation() {
        new SimUAVPlaneCreatorWindow(this);
    }
    
    /*
     * addConnections
     * param: toolbar, menu
     * return: N/A
     * function: gives access to the toolbar and the menu
     */
    public void addConnections(SimUAVToolBar tBar, SimUAVMenu uavMenu) {
        toolBar = tBar;
        smenu = uavMenu;
    }
    
    /*
     * simToggleAnimation
     * param: N/A
     * return: N/A
     * function: toggles the animating of the planes
     */
    public void simToggleAnimation() {
        animate = !animate;
        System.out.println("User toggled animation");
        toolBar.toggleAnimationButton();
    }
    
    /*
     * simulationTick
     * param: N/A
     * return: N/A
     * function: propagates the tick to all of the planes, and checks to see 
     *           if any of them have collided
     */
    private void simulationTick() {
        for (int i = 0; i < simPlaneNumber; i++) {
            UAVPlane p1 = planeArray[i];
            if (!p1.crashed) {
                p1.tick(fps);
                for (int j = 0; j < i; j++) {
                    UAVPlane p2 = planeArray[j];
                    if (!p2.crashed) {
                        Rectangle p2Bounds = p2.dShape.getBounds();
                        if (p1.dShape.intersects(p2Bounds)){
                            checkCollision(p1, p2); 
                        }
                        if (p1.dShape.contains(p2Bounds)) {
                            rectifyCollision(p1, p2);
                        }
                    }
                }
            }
        }
    }
    
    /*
     * drawPlanes
     * param: Graphics2D, whether or not to color and/or fill, and the 
     *        percentage zoom
     * return: N/A
     * function: iterates through the array of planes, telling each 
     *           to draw itself, along with how to draw itself.
     */
    public void drawPlanes(Graphics2D g, boolean color, boolean fill) {
        for(int i = 0; i < simPlaneNumber; i++) {
            planeArray[i].draw(g, color, fill);
        }
    }
    
    /*
     * toggleGrid
     * param: N/A
     * return: N/A
     * function: toggles the grid lines on and off
     */
    public void toggleGrid() {
        canvas.toggleGrid();
    }
    
    /*
     * toggleColor
     * param: N/A
     * return: N/A
     * function: toggles the planes' color on and off
     */
    public void toggleColor() {
        canvas.toggleColor();
    }

    /*
     * toggleFill
     * param: N/A
     * return: N/A
     * function: toggles the planes' fill on and off
     */
    public void toggleFill() {
        canvas.toggleFill();
    }

    /*
     * toggleScale
     * param: N/A
     * return: N/A
     * function: toggles the scale on and off
     */
    public void toggleScale() {
        canvas.toggleScale();
    }
    
    /*
     * addPlane
     * param: plane
     * return: N/A
     * function: adds the plane to the list of planes and then redraws
     */
    public void addPlane(SimUAVPlane p) {
        createPlane(p);
        redrawCanvas();
    }
    
    /*
     * simExpandPlaneArray
     * param: N/A
     * return: N/A
     * function: expands the array that holds the planes for the simulation
     */
    private void simExpandPlaneArray() {
        //Here, we assume that not enough planes will be added to justify 
        //increasing the interval between expansions of the array 
        //multiplicatively
        UAVPlane [] newArray = new UAVPlane[simPlaneNumber + 5];
        System.arraycopy(planeArray, 0, newArray, 0, planeArray.length);
        planeArray = newArray;
    }
    
    /*
     * getSinglePlaneID
     * param: plane's index
     * return: plane's ID
     * function: gives the id of the plane in the array at the index given
     */
    public String getSinglePlaneID(int counter) {
        return planeArray[counter].id;
    }

    /*
     * getNumberPlanes
     * param: N/A
     * return: number of planes
     * function: returns the number of planes that have been created.
     */
    public int getNumberPlanes() {
        return simPlaneNumber;
    }
    
    /*
     * simExpandPlaneArray
     * param: possible ID
     * return: true if the ID is unused, false if not
     * function: checks to see if the ID is available
     */
    public boolean idAvailable(String text) {
        for (int i = 0; i< simPlaneNumber; i++) {
            if (planeArray[i].id.equals(text)) {
                return false;
            }
        }
        return true;
    }
    
    /*
     * modifyPlanesNumericallybySubtype
     * param: plane type, the property to be changed, the amount 
     *        by which the property indicated is to change
     * return: N/A
     * function: changes the property given, in all planes of the given type,
     *           by the given amount
     * notes: type contains the type of UAVPlane to be modified, or null, 
     *        if every UAVPlane is going to be modified
     */
    public void modifyPlanesNumericallybySubtype(SimUAVPlaneTypes type, 
                                                 String property, int change) 
    {
        double current;
        for (int i = 0; i < simPlaneNumber; i++) {
            if (planeArray[i].stype == type || type == null) {
                switch (property) {
                    case "Speed":
                        current = ((SimUAVPlane) planeArray[i]).getSpeed();
                        ((SimUAVPlane) planeArray[i]).setSpeed((int) (change + current));
                        break;
                    case "Altitude":
                        current = ((SimUAVPlane) planeArray[i]).getAltitude();
                        ((SimUAVPlane) planeArray[i]).setAltitude((int) (change + current));
                        break;
                    case "Direction":
                        current = planeArray[i].getDirection();
                        planeArray[i].setDirection(change + current);
                        break;
                    case "Angular Speed":
                        current = ((SimUAVPlane) planeArray[i]).getAngularSpeed();
                        ((SimUAVPlane) planeArray[i]).setAngularSpeed(change + current);
                        break;
                }
            }
        }
    }
    
    /*
     * modifyPlaneColorbySubtype
     * param: plane type, the new color
     * return: N/A
     * function: changes the color of all planes of the given type
     *           to the given color
     * notes: type contains the type of UAVPlane to be modified, or null, 
     *        if every UAVPlane is going to be modified
     */
    public void modifyPlaneColorbySubtype(SimUAVPlaneTypes type, 
                                          UAVColors change) 
    {
        for (int i = 0; i < simPlaneNumber; i++) {
            if (planeArray[i].stype == type  || type == null) {
                planeArray[i].setColor(change);
            }
        }
    }
    
    /*
     * changePlaneID
     * param: old ID for plane, new ID for plane
     * return: N/A
     * function: changes the name of the plane with the given ID
     *           to be the second String parameter
     */
    public void changePlaneID(String oldName, String newName) {
        for (int i =0; i< simPlaneNumber; i++) {
            if (planeArray[i].id.equals(oldName)) {
                planeArray[i].setName(newName);
            }
        }
    }
    
    /*
     * setPlaneSelected
     * param: plane ID
     * return: N/A
     * function: selects the plane corresponding to the ID given
     */
    protected void setPlaneSelected(String planeID) {
        for (int i = 0; i < simPlaneNumber; i++) {
            ((SimUAVPlane) planeArray[i]).setSelected(false);
            if (planeID.equals(planeArray[i].id)) {
                ((SimUAVPlane) planeArray[i]).setSelected(true);
            }
        }
    }

    /*
     * canvasClicked
     * param: the point clicked
     * return: N/A
     * function: Selects the plane at the location of the click, if there is a
     *           plane at that location
     */
    public void canvasClicked(Point2D p) {
        String message = "Mouse down at " + p.getX() + ", " + p.getY() + ".";
        p = canvas.translateActualtoDisplay(p);       
        for (int i = 0; i < simPlaneNumber; i++) {
            ((SimUAVPlane) planeArray[i]).setSelected(false);
            if (planeArray[i].pickDisplayCorrelation(p)) {
                ((SimUAVPlane) planeArray[i]).setSelected(true);
                message += " Plane: " + planeArray[i].id;
            }
        }
        System.out.println(message);
    }
    
    /*
     * modifySelectedPlane
     * param: the property to change, the amount by which the 
     *        property should change
     * return: N/A
     * function: Changes the given property of the selected plane 
     *           by the given amount
     */
    public void modifySelectedPlane(String changeType, int number) {
        for (int i=0; i<simPlaneNumber;i++) {
            if (((SimUAVPlane) planeArray[i]).isSelected()) {
                switch (changeType) {
                    case "Turn" :
                        planeArray[i].turn(number);
                        break;
                    case "Height":
                        ((SimUAVPlane) planeArray[i]).changeHeight(number);
                        break;
                    case "Speed":
                        planeArray[i].changeSpeed(number);
                        break;
                    case "AngSpeed":
                        ((SimUAVPlane) planeArray[i]).changeAngSpeed(number);
                        break;
                    case "Repair":
                        ((SimUAVPlane) planeArray[i]).repair();
                        break;
                }
            }
        }
    }
    
    
    /*
     * zoom
     * param: zoom level
     * return: N/A
     * function: tells the canvas to zoom to the appropriate level
     */
    protected void zoom(int zoomlvl) {
        canvas.zoom(zoomlvl);
    }

    
    
    
/*******************************************************************************
 ********************************Game Methods***********************************
 ******************************************************************************/
    
    /*
     * initializeGame
     * param: N/A
     * return: N/A
     * function: Initializes the counters for each array of game objects, 
     *           as well as the score and the score's String representation
     */
    public void initializeGame() {
        laserNum = 0;
        gamePlaneNumber = 0;
        waveNumber = 0;
        
        scoreNum = 0;
        scoreText = "0000000";
    }
    
    /*
     * beginGame
     * param: N/A
     * return: N/A
     * function: Begins the game, creates the player
     */
    void beginGame() {
        gamePaused = false;
        player = new GameUAVPlayer(canvas, this, "player", UAVColors.PLAYER, 
                                   new Point2D.Double(0,0), 
                                   new Dimension(20, 40));
        redrawCanvas();
    }
    
    /*
     * gameTick
     * param: N/A
     * return: N/A
     * function: Propagates the tick to the game objects and then moves 
     *           the canvas's visible area.
     */
    private void gameTick() {
        movePlayer();
        moveHostilePlanes();
        moveLasers();
        scoreNum+=PER_REFRESH_SCORE_INCREASE;
        canvas.moveVisibleArea();
    }
    
    /*
     * drawGameObjects
     * param: Graphics2D
     * return: N/A
     * function: tells all game objects to draw themselves 
     *           (player, hostile planes, lasers, walls, score)
     */
    public void drawGameObjects(Graphics2D g) {
        player.draw(g);
        for (int i = 0; i < gamePlaneNumber; i++) {
            planeArray[i].draw(g);
        }
        for (int i = 0; i < laserNum; i++) {
            laserArray[i].draw(g);
        }
        walls.draw(g);
        drawScore(g);
    }
    
    /*
     * drawScore
     * param: Graphics2D
     * return: N/A
     * function: matches the score's String representation to the 
     *           score's numerical value, then draws it 
     */
    private void drawScore(Graphics g) {
        scoreText = "";
        double digits = Math.floor(Math.log10(scoreNum));
        digits = (digits < 0) ? 0: digits; 
        
        for (int i = SCORE_DIGITS -1; i > digits; i--) {
            scoreText += "0";
        }
        scoreText += (int) scoreNum;
        Color c = new Color(255,255,255, 220);
        g.setColor(c);
        g.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        g.drawString(scoreText, INFO_LEFT_OFFSET, INFO_VERTICAL_OFFSET);
    }
    
    /*
     * gameExpandPlaneArray
     * param: N/A
     * return: N/A
     * function: expands the array of hostile planes and deletes planes 
     *           which have already been destroyed.
     */
    private void gameExpandPlaneArray() {
        int destroyed = 0;
        UAVPlane [] newArray = new UAVPlane[gamePlaneNumber + 5];
        
        for (int i = 0; i < planeArray.length; i++) {
            if (!planeArray[i].crashed) { 
                newArray[i - destroyed] = planeArray[i];
            } else {
                destroyed++;
            }
        }
        planeArray = newArray;
    }
    
    /*
     * movePlayer
     * param: N/A
     * return: N/A
     * function: propagates the tick to the player and then checks to see if 
     *           it has crashed into the walls.
     */
    private void movePlayer() {
        player.tick(fps);
        if (!player.crashed && walls.nearby(player.center, WALL_DISTANCE)) {
            walls.checkPlaneCollision(player);
        }
    }
    
    /*
     * moveHostilePlanes
     * param: N/A
     * return: N/A
     * function: propagates the tick to the hostile planes and then checks 
     *           for collisions.
     */
    private void  moveHostilePlanes() {
        for (int i = 0; i < gamePlaneNumber; i++) {
            GameUAVPlane plane = (GameUAVPlane) planeArray[i];
            if (!plane.crashed) {
                plane.tick(fps);
                if (!plane.crashed && walls.nearby(plane.center, WALL_DISTANCE)) {
                    walls.checkPlaneCollision(plane);
                }
                Rectangle playerBounds = player.dShape.getBounds();
                if (plane.dShape.intersects(playerBounds)){
                    checkCollision(plane, player);
                }
                if (plane.dShape.contains(playerBounds)) {
                    rectifyCollision(plane, player);
                }
            }
        }
    }
    
    /*
     * moveLasers
     * param: N/A
     * return: N/A
     * function: propagates the tick to the lasers and then checks 
     *           for collisions.
     */
    private void moveLasers() {
        for (int i = 0; i < laserNum; i++) {
            GameUAVLaser laser = laserArray[i];
            if (laser.isActive()) {
                laser.tick(fps);
                if (player.dShape.contains(laser.getDisplayLeader()) && laser.isHostile()) {
                    checkCollision(player, laser);
                }
                for (int j = 0; j < gamePlaneNumber; j++) {
                    if (planeArray[j].dShape.contains(laser.getDisplayLeader()) && !laser.isHostile()) {
                        checkCollision(planeArray[j], laser);
                    }
                }
            }
        }
    }
    
    /*
     * checkCollision
     * param: plane, laser
     * return: N/A
     * function: tests for collision between the plane and the laser
     * notes: assumes that a laser will not pass through a plane
     *        entirely before the call happens. If the laser crosses through 
     *        one of the corners quickly enough, they would appear to have 
     *        collided, but this function wouldn't catch the collision.
     */
    private void checkCollision(UAVPlane p, GameUAVLaser l) {
        Line2D [] planeLines = p.getDisplayLineSegments();
        Line2D laserLine = l.getDisplayLine();
        boolean collided = false;
        
        for (int i = 0; i < p.numEdges; i++) {
            collided = collided || (planeLines[i].intersectsLine(laserLine));
        }
        collided = collided || (p.pickDisplayCorrelation(l.getDisplayLeader()));
        
        if (collided) {
            l.collide();
            p.takeDamage();
        }
    }
    
    /*
     * createLaser
     * param: direction, location, whether the laser was fired by an enemy
     * return: N/A
     * function: creates a laser with the given directionality, position 
     *           and hostility.
     */
    public void createLaser(double direction, Point2D.Double location, 
                            boolean hostile)
    {
        if (laserNum == laserArray.length) { expandLaserArray();}
        laserArray[laserNum] = new GameUAVLaser(canvas, this, location,
                                                direction, hostile);
        laserNum++;
    }

    /*
     * expandLaserArray
     * param: N/A
     * return: N/A
     * function: expands the array of lasers, removing the lasers that 
     *           have become inactive
     */
    private void expandLaserArray() {
        int numInactive = 0;
        GameUAVLaser [] array = new GameUAVLaser[laserNum * 2];
        for (int i = 0; i < laserArray.length; i++) {
            if (laserArray[i].isActive()) {
                array[i - numInactive] = laserArray[i].copy();
            } else {
                numInactive++;
            }
        }
        laserNum -= numInactive;
        laserArray = array;
    }

    /*
     * getBounds
     * param: N/A
     * return: the bounds of the play area
     * function: get method
     */
    Rectangle getBounds() {
        return (Rectangle) playArea.clone();
    }
    
    /*
     * getPlayer
     * param: N/A
     * return: the player character
     * function: get method
     */
    GameUAVPlayer getPlayer() {
        return player;
    }

    /*
     * getWalls
     * param: N/A
     * return: the walls
     * function: get method
     */
    GameUAVWalls getWalls() {
        return walls;
    }

    /*
     * retrieveHighScores
     * param: N/A
     * return: the high scores as an array
     * function: retrieves the scores from the text file stored in the 
     *           program's assets
     * notes: this assumes that the text file is stored in the same package as 
     *        the other files, and that the user invokes the program 
     *        from the package's directory
     */
    GameUAVScore [] retrieveHighScores() {
        File scoreFile = new File(System.getProperty("user.dir") + "/highScores.txt");
        ArrayList<String> scList = new ArrayList();
        try {
            scList = (ArrayList<String>) Files.readAllLines(scoreFile.toPath());
        } catch(IOException io) {}
        
        GameUAVScore[] scores = new GameUAVScore[scList.size()];
       
        for (int i=0; i < scores.length; i++) {
            scores[i] = new GameUAVScore(i+1, scList.get(i).substring(0, 7), 
                              Integer.parseInt(scList.get(i).substring(8, 16)));
        }
        
        return scores;
    }
    
    /*
     * getGameInfo
     * param: N/A
     * return: information about the current game
     * function: get method
     */
    String[] getGameInfo() {
        String [] info = new String[3];
        info[0] = "" + (int) scoreNum;
        info[1] = "" + waveNumber;
        info[2] = "" + numDestroyedPlanes;
        return info;
    }

    /*
     * togglePausedGame
     * param: N/A
     * return: N/A
     * function: sets the gamePaused boolean (which determines whether or not 
     *           to propagate the tick) to the given value.
     */
    public void togglePausedGame(boolean b) {
        gamePaused = b;
    }
    
    /*
     * quitGame
     * param: N/A
     * return: N/A
     * function: empties the data which are pertinent to the current game 
     *           and reinitializes, then calls for a redraw.
     */
    void quitGame() {
        System.out.println("Game Quit");
        player = null;
        playArea = new Rectangle(new Point(-1000, 1000), 
                                     new Dimension(2000, 2000));
        planeArray = new GameUAVPlane[16];
        laserArray = new GameUAVLaser[10];
        walls = new GameUAVWalls(this, canvas);
        
        initializeGame();
        canvas.reset();
        canvas.redraw();
    }
    
    
    /*
     * closeApplication
     * param: N/A
     * return: N/A
     * function: closes the application entirely
     */
    void closeApplication() {
        /*
        if (fullscreen) {
            GraphicsDevice gd = GraphicsEnvironment.
                                              getLocalGraphicsEnvironment().
                                              getDefaultScreenDevice();
            gd.getFullScreenWindow().dispose();
        } else {
            parent.dispose();
            timer.stop();
        }*/
        parent.dispose();
        timer.stop();
    }
    
    /*
     * getAimMode
     * param: N/A
     * return: aimMode
     * function: get method
     */
    protected boolean getAimMode() {
        return aimMode;
    }
    
    /*
     * setAimMode
     * param: aimMode
     * return: N/A
     * function: set method for aimMode, but also changes the rate at which 
     *           game objects move
     * notes: artificially slows movement by telling the objects that the 
     *        amount that they should move in each frame should be less 
     *        (communicated by fps, a number originally meaning the number 
     *        of frames that occur in a second, increasing threefold,
     *        but leaving the delay between redraws the same, effectively 
     *        drawing the movement of one second over the course of 
     *        three seconds worth of redraws).
     */
    void setAimMode(boolean b) {
        if (aimMode && !b) {
            fps = 1000/delay;
        }
        if (!aimMode && b) {
            fps = 3000/delay ;
        }
        aimMode = b;
    }
    
    /*
     * getRedrawsPerSecond
     * param: N/A
     * return: frames per second (when not in aim mode)
     * function: get method
     * notes: as previously mentioned, in aim mode, the  "number of redraws per 
     *        second" is tripled, but the redraws per second actually 
     *        remains constant
     */
    protected double getRedrawsPerSecond() {
        return fps;
    }
    
    
/*******************************************************************************
 *******************************Neutral Methods*********************************
 ******************************************************************************/
    
    /*
     * getCanvas
     * param: N/A
     * return: the canvas
     * function: get method
     */
    public UAVCanvas getCanvas() {
        return canvas;
    }

    /*
     * redrawCanvas
     * param: N/A
     * return: N/A
     * function: tells the canvas to redraw. Supposed to be clarifying shorthand
     */
    public void redrawCanvas() {
        canvas.redraw();
    }
    
    /*
     * checkCollision
     * param: plane i, plane j
     * return: N/A
     * function: tests for collision between the two given planes
     * notes: like the function that tests for collision between a plane and 
     *        a laser, this function assumes that any collision between two 
     *        planes will have them overlapping in space for at least one frame
     */
    private void checkCollision(UAVPlane i, UAVPlane j) {
        Line2D [] iSegments = i.getDisplayLineSegments();
        Line2D [] jSegments = j.getDisplayLineSegments();
        
        for (int l = 0; l < i.numEdges; l++) {
            for (int m = 0; m < j.numEdges; m++) {
                if (iSegments[l].intersectsLine(jSegments[m])) {
                    rectifyCollision( i, j);
                    l = i.numEdges;
                    m = j.numEdges;
                }
            }
        }
        //even if no line segments intersect, one plane might be entirely
        //inside another, so we check to see if a point (the leader,
        //chosen for convenience) on one of the planes is inside the other 
        //plane, and vice versa.
        if ( i.pickDisplayCorrelation(j.getDisplayCenter()) ||
             j.pickDisplayCorrelation(i.getDisplayCenter())) {
            rectifyCollision(i,j);
        }
    }
    
    /*
     * rectifyCollision
     * param: plane i, plane j
     * return: N/A
     * function: enforces collision rules when the collision has already been 
     *           determined to have occurred between the two given planes
     * notes: in simulation mode, this tests to see if the two planes are close 
     *        enough on the z-axis (altitude) to have collided (each plane is 
     *        assumed to have a standard depth of 4, altitude occurring 
     *        at the exact center of the plane)
     */
    private void rectifyCollision(UAVPlane i, UAVPlane j) {
        if (mode == UAVModes.GAME) {
            i.takeDamage();
            j.takeDamage();
        } else {
            int altitudeDifference = Math.abs(((SimUAVPlane) i).getAltitude() - 
                                              ((SimUAVPlane) j).getAltitude());
            if (altitudeDifference < 4) {
                if (Math.min(((SimUAVPlane) i).getAltitude(), 
                             ((SimUAVPlane) j).getAltitude())
                     == ((SimUAVPlane) i).getAltitude()) {
                    i.crashPlane();
                } else {
                    j.crashPlane();
                }
            }
        }
        
    }
    
    /*
     * createPlane
     * param: plane
     * return: N/A
     * function: adds the given plane to the relevant array of planes (according 
     *           to the program's mode), expanding the array if necessary
     */
    private void createPlane(UAVPlane plane) {
        if (mode == UAVModes.GAME) {
            if (planeArray.length == gamePlaneNumber) { gameExpandPlaneArray();}
            planeArray[gamePlaneNumber] = plane;
            gamePlaneNumber++;
        } else {
            if (planeArray.length == simPlaneNumber) { simExpandPlaneArray();}
            planeArray[simPlaneNumber] = plane;
            simPlaneNumber++;
        }
    }
    
    /*
     * getPlaneClone
     * param: plane's index
     * return: the plane at the given index of the array
     * function: get method
     */
    public UAVPlane getPlaneClone(int planeIndex) {
        return planeArray[planeIndex].copyPlane();
    }
    
    /*
     * startTimer
     * param: N/A
     * return: N/A
     * function: initializes the timer and then starts it
     */
    public void startTimer() {
        timer = new Timer((int) delay, this);
        timer.start();
    }
    
    
    /*
     * getParent
     * param: N/A
     * return: the instance of main that instantiated this map
     * function: get method
     */
    protected Main getParent() {
        return parent;
    }
}
