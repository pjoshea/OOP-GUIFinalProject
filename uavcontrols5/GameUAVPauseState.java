/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVPauseState
 * Extends: GameUAVGraphicalState
 * Purpose: The Pause state draws the game objects, and then draws the 
 *          pause screen over them, with buttons which either return to the 
 *          active game or go to the completed game state.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


class GameUAVPauseState extends GameUAVGraphicalState {
    private final Font titleFont = new Font(Font.DIALOG, Font.ITALIC, 25);
    private final Font infoFont = new Font(Font.DIALOG, Font.PLAIN, 16);
    private static final String DEF_TITLE = "Paused";
    
    private GameUAVResumeButton resumeBtn;
    private GameUAVEnterQuitDialogButton quitBtn;
    private GameUAVControlsPauseButton controlsBtn;
    
    private Rectangle infoBox;
    private static final Dimension infoBoxDim = new Dimension(250, 200);
    
    private static final String [] infoStrings = {"Score", "Wave", "Hostiles Killed"};
    
    /*
     * GameUAVPauseState
     * param: map, canvas
     * returns: GameUAVPauseState
     * function: constructor
     */
    public GameUAVPauseState(UAVMap m, UAVCanvas c) 
    {
        super(m, c, DEF_TITLE, GameUAVScreens.PausedGame);
        infoBox = new Rectangle(new Point((c.getWidth() - infoBoxDim.width)/2, 
                                          (c.getHeight() - infoBoxDim.height)/2), 
                                infoBoxDim);
        resumeBtn = new GameUAVResumeButton(map, this,
                                    new Point2D.Double((c.getWidth()/2 - 150), 
                                                       (c.getHeight()/2 + 125)),
                                    new Dimension(120, 40));
        quitBtn = new GameUAVEnterQuitDialogButton(map, this,
                                    new Point2D.Double((c.getWidth()/2 + 30), 
                                                       (c.getHeight()/2 + 125)),
                                    new Dimension(120, 40));
    }
    
    /*
     * draw
     * param: Graphics2D on which to draw
     * returns: N/A
     * function: draw method
     */
    @Override
    public void draw(Graphics2D g) {
        infoBox = new Rectangle(new Point((canvas.getWidth() - infoBoxDim.width)/2, 
                                          (canvas.getHeight() - infoBoxDim.height)/2), 
                                infoBoxDim);
        map.drawGameObjects(g);
        g.setColor(new Color(0,0,51, 150));
        g.fill(new Rectangle(0,0,canvas.getWidth(), canvas.getHeight()));
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        g.setStroke(new BasicStroke(10));
        g.setFont(titleFont);
        FontMetrics fm = g.getFontMetrics();
        g.drawString(title, (canvas.getWidth() - fm.stringWidth(title))/2, 30);
        
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1));
        g.setFont(infoFont);
        fm = g.getFontMetrics();
        
        String [] gInfo = map.getGameInfo();
        for (int i =0; i < infoStrings.length; i++) {
            g.drawString(infoStrings[i], (int) infoBox.getX() + 20, 
                         (int) infoBox.getY() + 20 * i);
            int hz = infoBox.width - fm.stringWidth(gInfo[i]) - 20;
            g.drawString(gInfo[i], (int) infoBox.getX() + hz,
                         (int) infoBox.getY() + 20 * i);
        }
        resumeBtn.drawButton(g);
        quitBtn.drawButton(g);
    }
    
    /*
     * accept
     * param: command String
     * returns: N/A
     * function: when the resume button is pressed, the game unpauses; 
     *           when the quit button is pressed, the game ends
     */
    @Override
    public void accept(String cmd) {
        switch (cmd) {
            case GameUAVResumeButton.cmd :
                canvas.goTo(GameUAVScreens.ActiveGame);
                break;
            case GameUAVQuitButton.cmd :
                canvas.goTo(GameUAVScreens.MainMenu);
                break;
        }
    }
    
    /*
     * deactivate
     * param: N/A
     * returns: N/A
     * function: disables the resume and quit buttons
     */
    @Override
    public void deactivate() {
        resumeBtn.setActive(false);
        quitBtn.setActive(false);
    }
}
