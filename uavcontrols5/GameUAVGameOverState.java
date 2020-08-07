/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVGameOverState
 * Extends: GameUAVGraphicalState
 * Purpose: The Game Over gamestate appears when the player has been reduced to 
 *          0 health, and tells the user where their score ranked compared to 
 *          everyone else who had played (on the same copy of the program), 
 *          before allowing them to exit to the main menu.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class GameUAVGameOverState extends GameUAVGraphicalState {
    private static String DEF_TITLE = "Game Over";
    private final Font titleFont = new Font(Font.DIALOG, Font.PLAIN, 25);
    
    private GameUAVReturnButton returnBtn;

    /*
     * GameUAVGameOverState
     * param: map, canvas
     * returns: GameUAVGameOverState
     * function: constructor
     */
    public GameUAVGameOverState(UAVMap m, UAVCanvas c) {
        super(m, c, DEF_TITLE, GameUAVScreens.CompletedGame);
        
        returnBtn = new GameUAVReturnButton(m, this,
                                    new Point2D.Double(canvas.getWidth()/2 - 75,
                                                       canvas.getHeight()*.75),
                                    new Dimension(canvas.getWidth()/10, 
                                                  canvas.getHeight()/20));
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
    }
    
    /*
     * accept
     * param: command String
     * returns: N/A
     * function: return button brings the user back to the Main Menu
     */
    @Override
    public void accept(String cmd) {
        switch (cmd) {
            case GameUAVReturnButton.cmd :
                canvas.goTo(GameUAVScreens.MainMenu);
        }
    }
    
    /*
     * deactivate
     * param: N/A
     * returns: N/A
     * function: disables the return button
     */
    @Override
    public void deactivate() {
        returnBtn.setActive(false);
    }
}
