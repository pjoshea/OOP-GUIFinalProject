/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: UAVCanvas
 * Extends: JComponent
 * Purpose: The area in which all things are drawn. Manages the view options for
 *          the simulation mode, as well as the moving display of the game mode,
 *          and a means of translation between within component coordinates, 
 *          and "real" coordinates.
 */

package uavcontrols5;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.abs;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;


public class UAVCanvas extends JComponent implements MouseListener {  
    private int wid, hgt;
    private Point center;
    private final UAVMap map;
    private final UAVModes mode;
    private boolean fullscreen;
    
    //Simulation-only objects
    private boolean grid = true;
    private int gridSpacing = 25;
    private boolean color = true;
    private boolean fill = true;
    private boolean scale = true;
    private int zoom = 100;
    double zoomRatio = 1.0;
    private int scaleLength = 100;
    private char[] scaleText = {'1','0','0'};
    private char[] unitText = {' ', 'm','e','t','e','r','s'};

    //Game-only Objects
    private GameUAVPlayer player;
    private Rectangle2D.Double visibleRect;
    private Point2D referencePoint;
    
    private GameUAVGraphicalState current;

    private class ExitAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (current.type) {
                case ActiveGame:
                    goTo(GameUAVScreens.PausedGame);
                    break;
                case PausedGame:
                    goTo(GameUAVScreens.ActiveGame);
                    break;
                case CompletedGame: case ScoresMenu:
                case ControlsMenu:
                    goTo(GameUAVScreens.MainMenu);
                    break;
                case MainMenu:
                    goTo(GameUAVScreens.ExitApplicationDialog);
                    break;
                case QuitGameDialog:
                case ExitApplicationDialog:
                    current.accept(GameUAVDenyButton.cmd);
                    break;
            }
        }
    }
    
    private class EnterAimAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (current.type == GameUAVScreens.ActiveGame) {
                System.out.println("aim mode enabled");
                ((GameUAVActiveGameState) current).setAimMode(true);
                map.setAimMode(true);
                getInputMap().remove(KeyStroke.getKeyStroke("SPACE"));
                getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "exitAim");
            }
        }
    }
    
    private class ExitAimAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (current.type == GameUAVScreens.ActiveGame) {
                System.out.println("aim mode disabled");
                ((GameUAVActiveGameState) current).setAimMode(false);
                map.setAimMode(false);
                getInputMap().remove(KeyStroke.getKeyStroke("released SPACE"));
                getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "enterAim");
            }
        }
    }
    
   
/******************************************************************************
 ******************             Neutral Methods             *******************
 ******************************************************************************/
    
    /*
     * UAVCanvas
     * param: map, mode, whether or not the program is fullscreen
     * returns: UAVCanvas
     * function: constructor
     */
    UAVCanvas(UAVMap map, UAVModes mode, boolean fullscreen) {
        this.map = map;
        this.mode = mode;
        this.fullscreen = fullscreen;
        createKeyBindings(mode);
        addMouseListener(this);
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        center = new Point();
    }
    
    /*
     * paintComponent
     * param: Graphics object which is the basis on which 
     *        nearly everything is drawn.
     * returns: N/A
     * function: sets up all of the draw calls, clears the canvas, then adds any 
     *           view options
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        wid = getWidth();
        hgt = getHeight();
        center = new Point(wid/2, hgt/2);
        Color bg = (mode == UAVModes.GAME) ? new Color(0,0,51) : Color.WHITE;
        g2.setColor(bg);
        g2.fillRect(0, 0, wid, hgt);
        if (mode == UAVModes.GAME) {
            if (current == null) { initializeGame(g2); }
            current.draw(g2);
        } else {
            if (grid) {addGrid(g2);}
            if (scale) {addScale(g2);}
            map.drawPlanes(g2, color, fill);
        }
    }
    

    /*
     * getCanvasCenter
     * param: N/A 
     * returns: the center of the canvas in the "real" coordinate system
     * function: get method
     */
    public Point2D getCanvasCenter() {
        if (mode == UAVModes.GAME) {
            return referencePoint;
        }
        return center;
    }
    
    /*
     * redraw
     * param: N/A 
     * returns: N/A
     * function: allows for other classes to call for a repaint
     */
    public void redraw() {
        paintComponent(this.getGraphics());
    }
    
    /*
     * mousePressed
     * param: MouseEvent
     * returns: N/A
     * function: in simulation mode, reports the location of the mouse click, 
     *           in game mode, tells map that the user tried to fire the laser
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Point2D p =  new Point(e.getX(), e.getY());
        if (mode == UAVModes.GAME && current.type == GameUAVScreens.ActiveGame){ 
            player.fireLaser();
            player.automaticFire(true); 
        } 
        if (mode == UAVModes.SIMULATION) {
            p = translateDisplaytoActual(p);        
            map.canvasClicked(p);
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    /*
     * mouseReleased
     * param: MouseEvent
     * returns: N/A
     * function: in game mode, tells map that the user stopped autofiring.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (current.type == GameUAVScreens.ActiveGame) {
            player.automaticFire(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

/******************************************************************************
 ******************           Simulation Methods            *******************
 ******************************************************************************/

    /*
     * addGrid
     * param: Graphics object on which to draw
     * returns: Graphics object, after being drawn on
     * function: adds gridlines to the simulation mode for visibility
     */
    public Graphics addGrid(Graphics g) {
        //drawing axes
        g.setColor(Color.DARK_GRAY);
        g.drawLine(0, center.y, wid, center.y);
        g.drawLine(center.x, 0, center.x, hgt);
        
        gridSpacing = scaleLength/4;
        g.setColor(Color.LIGHT_GRAY);
        for (int i = center.x + gridSpacing; i < wid; i+=gridSpacing) {
            g.drawLine(i, 0, i, hgt);
        }
        for (int i = center.x -gridSpacing; i > 0; i-=gridSpacing) {
            g.drawLine(i, 0, i, hgt);
        }
        for (int i = center.y + gridSpacing; i < hgt; i+=gridSpacing) {
            g.drawLine( 0, i, wid, i);
        }
        for (int i = center.y - gridSpacing; i > 0; i-=gridSpacing) {
            g.drawLine(0, i, wid, i);
        }
        
        return g;
    }
    
    /*
     * addScale
     * param: Graphics object on which to draw
     * returns: Graphics object, after being drawn on
     * function: adds a scale to the simulation mode
     */
    public Graphics addScale(Graphics g) {
        int leftEnd = wid - (scaleLength + 25);
        int rightEnd = wid - 25;
        int ascender = hgt - 28;
        int baseline = hgt - 25;
        int descender = hgt - 22;
        
        g.setColor(Color.BLACK);
        g.drawLine(leftEnd, baseline, rightEnd, baseline);
        g.drawLine(leftEnd, ascender, leftEnd, descender);
        g.drawLine(rightEnd, ascender, rightEnd, descender);
        
        char[] text = new char[scaleText.length + unitText.length];
        System.arraycopy(scaleText, 0, text, 0, scaleText.length);
        System.arraycopy(unitText, 0, text, scaleText.length, unitText.length);
        g.drawChars( text, 0, text.length, (rightEnd - 70), (hgt-10));
        
        return g;
    }
    
    /*
     * zoom
     * param: zoom level
     * returns: N/A
     * function: sets zoom to be equal to the given number and redraws
     */
    protected void zoom(int zoom) {
        if (this.zoom != zoom) {
            this.zoom = zoom;
            zoomRatio = (float)zoom/100.0;
            updateScaleText();
            recalculateScaleLength();
            redraw();
        }
    }
    
    /*
     * recalculateScaleLength
     * param: N/A
     * returns: N/A
     * function: changes the drawing length of the scale line (a view option for 
     *           simulation mode) to match the stated size of the scale.
     * notes: the scale's length is changed with the zoom level because a scale
     *        is no longer useful if it's significantly different 
     *        from the distances it is being used to approximate
     */
    private void recalculateScaleLength() {
        double baseline = 100;
        if (zoom >= 201)                { baseline = 400; } 
        if (zoom >= 134 && zoom <= 200) { baseline = 200; }
        if (zoom >= 101 && zoom <= 133) { baseline = 133; }
        if (zoom >= 81  && zoom <= 100) { baseline = 100; }
        if (zoom >= 68  && zoom <=  80) { baseline = 80;  }
        if (zoom >= 58  && zoom <=  67) { baseline = 67;  }
        if (zoom >= 51  && zoom <=  57) { baseline = 57;  }
        if (zoom >= 34  && zoom <=  50) { baseline = 50;  }
        if (zoom >= 26  && zoom <=  33) { baseline = 33;  }
        
        scaleLength = (int)((double) 100 * ((double) zoom / baseline));
    }

    /*
     * updateScaleText
     * param: N/A
     * returns: N/A
     * function: changes the text that appears next to the scale line
     * notes: the scale's length is changed with the zoom level because a scale
     *        is no longer useful if it's significantly different 
     *        from the distances it is being used to approximate
     */
    private void updateScaleText() {
        //four times original == 400% zoom
        if (zoom >= 201) { 
            scaleText[0] = ' ';
            scaleText[1] = '2';
            scaleText[2] = '5';
        } 
        //twice original == 200% zoom
        if (zoom >= 134 && zoom <= 200) {
            scaleText[0] = ' ';
            scaleText[1] = '5';
            scaleText[2] = '0';
        }
        //4/3rds original == 133% zoom approx.
        if (zoom >= 101 && zoom <= 133) {
            scaleText[0] = ' ';
            scaleText[1] = '7';
            scaleText[2] = '5';
        }
        //full size == 100% zoom
        if (zoom >= 81 && zoom <= 100) {
            scaleText[0] = '1';
            scaleText[1] = '0';
            scaleText[2] = '0';
        }
        //125% of original == 80% zoom
        if (zoom >= 68 && zoom <= 80) {
            scaleText[0] = '1';
            scaleText[1] = '2';
            scaleText[2] = '5';
        }
        //150% of original == 67% zoom approx.
        if (zoom >= 58 && zoom <= 67) {
            scaleText[0] = '1';
            scaleText[1] = '5';
            scaleText[2] = '0';
        }
        //175% of original == 57% zoom approx.
        if (zoom >= 51 && zoom <= 57) { 
            scaleText[0] = '1';
            scaleText[1] = '7';
            scaleText[2] = '5';
        }
        //1/2 original == 50% zoom
        if (zoom >= 34 && zoom <= 50) {
            scaleText[0] = '2';
            scaleText[1] = '0';
            scaleText[2] = '0';
        }
        //1/3 original == 33% zoom
        if (zoom >= 26 && zoom <= 33) {
            scaleText[0] = '3';
            scaleText[1] = '0';
            scaleText[2] = '0';
        }
        //1/4 original == 25% zoom
        if (zoom == 25) {
            scaleText[0] = '4';
            scaleText[1] = '0';
            scaleText[2] = '0';
        }
    }

    /**************************************************************************
     ************* Toggle Viewing Options for Simulation Mode *****************
     **************************************************************************/
    public void toggleGrid() {
        grid = !grid;
        System.out.println("User toggled grid");
        redraw();
    }
    
    public void toggleColor() {
        color = !color;
        System.out.println("User toggled color");
        redraw();
    }

    public void toggleFill() {
        fill = !fill;
        System.out.println("User toggled fill");
        redraw();
    }

    public void toggleScale() {
        scale = !scale;
        System.out.println("User toggled scale");
        redraw();
    }
    
    
    

/******************************************************************************
 ******************              Game Methods               *******************
 ******************************************************************************/
    /*
     * goTo
     * param: the type of state to switch to
     * returns: N/A
     * function: switches graphical states to an instance of the type 
     *           passed in as a parameter
     */
    void goTo(GameUAVScreens state) {
        switch (state) {
            case ActiveGame:
                if (current.type.equals(GameUAVScreens.PausedGame)) {
                    map.togglePausedGame(false);
                } else {
                    map.beginGame();
                }
                break;
            case PausedGame: 
                map.togglePausedGame(true);
                break;
        }
        current = state.getInstanceOf(map, this);
    }
    
    /*
     * initializeGame
     * param: Graphics2D object on which to draw
     * returns: N/A
     * function: sets up the program in game mode
     */
    private void initializeGame(Graphics2D g) {
        setFocusable(true);
        requestFocusInWindow();
        referencePoint = new Point2D.Double(0,0);
        visibleRect = new Rectangle.Double((double) (- wid)/2, (double) (hgt)/2, 
                                           wid, hgt);
        player = map.getPlayer();
        current = new GameUAVMainMenuState(map, this);
    }
 
    /*
     * reset
     * param: N/A 
     * returns: N/A
     * function: overwrites information about the current coordinate system
     */
    public void reset() {
        center = new Point();
        current = null;
    }

    /*
     * getVisibleArea
     * param: N/A
     * returns: the visible area (in the "real" coordinate system)
     * function: get method
     */
    public Rectangle2D.Double getVisibleArea() {
        return visibleRect;
    }
    
    /*
     * moveVisibleArea
     * param: N/A
     * returns: N/A
     * function: moves the visible area to keep the player at the center 
     *           of the screen at all times
     */
    public void moveVisibleArea() {
        if (player == null) {
            player = map.getPlayer();
        }    

        double deltaX = player.getDeltaX();
        double deltaY = player.getDeltaY();
        
        referencePoint = new Point2D.Double(referencePoint.getX() + deltaX, 
                                            referencePoint.getY() + deltaY);
        visibleRect = new Rectangle.Double(referencePoint.getX() - wid/2, 
                                           referencePoint.getY() + hgt/2, 
                                           wid, hgt);
    }
    
    /*
     * createKeyBindings
     * param: the mode in which the program is launched
     * returns: N/A
     * function: binds the escape key to pausing and to opening exit/quit dialogs
     *           binds the space key to entering aim mode
     */
    private void createKeyBindings(UAVModes mode) {
        if (mode == UAVModes.GAME) {
            Action exit = new ExitAction();
            getInputMap().put(KeyStroke.getKeyStroke("released ESCAPE"), "exit");
            getActionMap().put("exit", exit);
            
            Action enterAim = new EnterAimAction();
            getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "enterAim");
            getActionMap().put("enterAim", enterAim);
            
            Action exitAim = new ExitAimAction();
            getInputMap().put(KeyStroke.getKeyStroke("released SPACE"), "exitAim");
            getActionMap().put("exitAim", exitAim);
        }
    }
    
    /*
     * getCurrentState
     * param: N/A
     * returns: the current graphical state
     * function: get method
     */
    GameUAVGraphicalState getCurrentState() {
        return current;
    }
    
/******************************************************************************
 *************                Conversion Functions                *************
 ************* Translating between the coordinate system native   *************
 ************* to the Graphics object and the coordinates system  *************
 *************        which all of the program objects use        *************
 ******************************************************************************/
    
/******************************************************************************
 *************              Static Frame of Reference             *************
 ******************************************************************************/
    
    protected Point translateDisplaytoActual(Point2D p) {
        Point displayP = new Point((int)((double)(p.getX() - center.x) / zoomRatio), 
                                   (int)((double)(center.y - p.getY()) / zoomRatio));
        return displayP;
    }
    
    protected Point translateActualtoDisplay(Point2D p) {
        Point actualP = new Point((int)((double)p.getX() * zoomRatio) + center.x, 
                                  center.y - (int)((double)p.getY() / zoomRatio));
        return actualP;
    }
    
    protected int translateXActualtoDisplay(int xActual) {
        return ((int) ((double) xActual * zoomRatio) + center.x);
    }

    protected int translateYActualtoDisplay(int yActual) {
        return (center.y - (int) ((double) yActual * zoomRatio));
    }

    protected int translateXDisplaytoActual(int xDisplay) {
        return ((int) ((double) (xDisplay - center.x) / zoomRatio));
    }
    
    protected int translateYDisplaytoActual(int yDisplay) {
        return ((int) ((double) (center.y - yDisplay) / zoomRatio));
    }
    
    protected Line2D [] translateEdgesActualtoDisplay(Line2D [] edges) {
        Line2D [] dEdges = new Line2D.Double[edges.length];
        for (int i = 0; i < edges.length; i++) {
            dEdges[i] = new Line2D.Double(
                                    translateActualtoDisplay(edges[i].getP1()), 
                                    translateActualtoDisplay(edges[i].getP2()));
        }
        return dEdges;    
    }

    protected Polygon translateShapeActualtoDisplay(Polygon shape) {
        Polygon dShape = new Polygon();
        for (int i = 0; i < shape.npoints; i++) {
            dShape.addPoint(translateXActualtoDisplay(shape.xpoints[i]),
                            translateYActualtoDisplay(shape.ypoints[i]));
        }
        return dShape;
    }

/******************************************************************************
 *************              Moving Frame of Reference             *************
 ******************************************************************************/
    
    protected Point2D translateMovingDisplaytoActual(Point2D p) {
        int xsign = (center.x > p.getX()) ? -1: 1;
        int ysign = (center.y > p.getY()) ? 1: -1;
        return new Point2D.Double(referencePoint.getX() + xsign * abs(center.x - p.getX()), 
                                  referencePoint.getY() + ysign * abs(center.y - p.getY()));  
    }
    
    protected int translateXMovingDisplaytoActual(int displayX) {
        int sign = (center.x > displayX) ? -1: 1;
        return (int) referencePoint.getX() + sign * abs(center.x - displayX);
    }
    
    protected double translateXMovingDisplaytoActual(double displayX) {
        int sign = (center.x > displayX) ? -1: 1;
        return referencePoint.getX() + sign * abs(center.x - displayX);
    }
    
    protected int translateYMovingDisplaytoActual(int displayY) {
        int sign = (center.y > displayY) ? 1: -1;
        return (int) referencePoint.getY() + sign * abs(center.y - displayY);
    }
    
    protected double translateYMovingDisplaytoActual(double displayY) {
        int sign = (center.y > displayY) ? 1: -1;
        return referencePoint.getY() + sign * abs(center.y - displayY);
    }
    
    protected Point2D translateActualtoMovingDisplay(Point2D p) {
        int xsign = ((int) referencePoint.getX() > p.getX()) ? -1 : 1;
        int ysign = ((int) referencePoint.getY() > p.getY()) ? 1 : -1;
        return new Point2D.Double(center.x + xsign * abs(p.getX() - referencePoint.getX()), 
                                  center.y + ysign * abs(p.getY() - referencePoint.getY()));
    }
    
    protected int translateXActualtoMovingDisplay(int actualX) {
        int xsign = ((int) referencePoint.getX() > actualX) ? -1 : 1;
        return center.x + xsign * abs(actualX - (int) referencePoint.getX());
    }
    
    protected double translateXActualtoMovingDisplay(double actualX) {
        int xsign = (referencePoint.getX() > actualX) ? -1 : 1;
        return center.x + xsign * abs(actualX - referencePoint.getX());
    }

    protected int translateYActualtoMovingDisplay(int actualY) {
        int ysign = ((int) referencePoint.getY() > actualY) ? 1 : -1;
        return center.y + ysign * abs(actualY - (int) referencePoint.getY());
    }
    
    protected double translateYActualtoMovingDisplay(double actualY) {
        int ysign = (referencePoint.getY() > actualY) ? 1 : -1;
        return center.y + ysign * abs(actualY - referencePoint.getY());
    }
    
    protected Line2D [] translateEdgesActualtoMovingDisplay(Line2D [] edges) {
        Line2D [] dEdges = new Line2D.Double[edges.length];
        for (int i = 0; i < edges.length; i++) {
            dEdges[i] = new Line2D.Double(
                              translateActualtoMovingDisplay(edges[i].getP1()), 
                              translateActualtoMovingDisplay(edges[i].getP2()));
        }
        return dEdges;
    }

    protected Polygon translateShapeActualtoMovingDisplay(Polygon shape) {
        Polygon dShape = new Polygon();
        for (int i = 0; i < shape.npoints; i++) {
            dShape.addPoint(translateXActualtoMovingDisplay(shape.xpoints[i]),
                            translateYActualtoMovingDisplay(shape.ypoints[i]));
        }
        return dShape;
    }
    
    Rectangle translateRectActualtoMovingDisplay(Rectangle wall) {
        Rectangle r = new Rectangle();
        r.setLocation(translateXActualtoMovingDisplay(wall.x), 
                      translateXActualtoMovingDisplay(wall.y));
        r.setSize(wall.width, wall.height);
        return r;
    }

}
