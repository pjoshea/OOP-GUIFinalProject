/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVMainMenuState
 * Extends: GameUAVGraphicalState
 * Purpose: The Main Menu allows the user to quit to the desktop, to examine 
 *          past high scores, to learn the control scheme, 
 *          and to begin a new game.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class GameUAVMainMenuState extends GameUAVGraphicalState {
    private final Font titleFont = new Font(Font.DIALOG, Font.PLAIN, 25);
    private static final String DEF_TITLE = "Main Menu";
    
    private GameUAVStartButton startBtn;
    private GameUAVScoreButton scoreBtn;
    private GameUAVControlsButton controlBtn;
    
    /*
     * GameUAVMainMenuState
     * param: map, canvas
     * returns: GameUAVMainMenuState
     * function: constructor
     */
    public GameUAVMainMenuState(UAVMap m, UAVCanvas c) {
        super(m, c, DEF_TITLE, GameUAVScreens.MainMenu);
        startBtn = new GameUAVStartButton(map, this, 
                                    new Point2D.Double((c.getWidth()/2 - 150), 
                                                       (c.getHeight()/2 - 100)),
                                    new Dimension(300, 100));
        scoreBtn = new GameUAVScoreButton(map, this, 
                                    new Point2D.Double((c.getWidth()/2 - 225), 
                                                       (c.getHeight()/2 + 100)),
                                    new Dimension(150, 50));
        controlBtn = new GameUAVControlsButton(map, this, 
                                    new Point2D.Double((c.getWidth()/2 + 75), 
                                                       (c.getHeight()/2 + 100)),
                                    new Dimension(150, 50));
    }
    
    /*
     * draw
     * param: Graphics2D on which to draw
     * returns: N/A
     * function: draw method
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1));        
        g.setFont(titleFont);
        FontMetrics tfm = g.getFontMetrics();
        int hAlign = (canvas.getWidth() - tfm.stringWidth(title))/2;
        g.drawString(title, hAlign, 30);
        
        startBtn.drawButton(g);
        scoreBtn.drawButton(g);
        controlBtn.drawButton(g);
    }
    
    /*
     * accept
     * param: command String
     * returns: N/A
     * function: start button begins the game, score button takes the user 
     *           to the High Scores Menu, the controls button takes the user 
     *           to the Controls Menu
     */
    @Override
    public void accept(String cmd) {
        switch (cmd) {
            case GameUAVStartButton.cmd:
                canvas.goTo(GameUAVScreens.ActiveGame);
                break;
            case GameUAVScoreButton.cmd:
                canvas.goTo(GameUAVScreens.ScoresMenu);
                break;
            case GameUAVControlsButton.cmd:
                canvas.goTo(GameUAVScreens.ControlsMenu);
                break;
        }
    }
    
    /*
     * deactivate
     * param: N/A
     * returns: N/A
     * function: disables the start, scores, and control buttons
     */
    @Override
    public void deactivate() {
        startBtn.setActive(false);
        scoreBtn.setActive(false);
        controlBtn.setActive(false);
    }
}
