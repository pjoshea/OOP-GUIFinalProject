/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVQuitDialogState
 * Extends: GameUAVGraphicalState
 * Purpose: The Quit Dialog gamestate contains a confirm and a deny button.
 *          If the user selects confirm, the program will go to the 
 *          completed game gamestate, if they select deny, then the 
 *          user will return to the previous gamestate.
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

class GameUAVQuitDialogState extends GameUAVGraphicalState {
    private static final String DEF_TITLE = "Give Up";
    private static final Font queryFont = new Font(Font.DIALOG, Font.PLAIN, 25);
    private static final String query = "Are you sure you want to give up?";
    
    private final Rectangle queryBorder;
    
    private GameUAVConfirmButton confirmBtn;
    private GameUAVDenyButton denyBtn;
    
    /*
     * GameUAVQuitDialogState
     * param: map, canvas
     * returns: GameUAVQuitDialogState
     * function: constructor
     */
    public GameUAVQuitDialogState(UAVMap m, UAVCanvas c)
    {
        super(m, c, DEF_TITLE, GameUAVScreens.QuitGameDialog);
        queryBorder = new Rectangle();
        confirmBtn = new GameUAVConfirmButton(map, this, new Point2D.Double(c.getWidth()/2 - 75, c.getHeight()/2 + 50), new Dimension(50, 20));
        denyBtn = new GameUAVDenyButton(map, this, new Point2D.Double(c.getWidth()/2 + 75, c.getHeight()/2 + 50), new Dimension(50, 20));
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
        g.setStroke(new BasicStroke(5));
        g.draw(queryBorder);
        
        g.setStroke(new BasicStroke(4));
        g.setFont(queryFont);
        FontMetrics fm = g.getFontMetrics();
        int hz = (canvas.getWidth() - fm.stringWidth(query))/2;
        g.drawString(title, (int) (canvas.getX() + hz), 25);
        
        confirmBtn.drawButton(g);
        denyBtn.drawButton(g);
    }
    
    /*
     * accept
     * param: command String
     * returns: N/A
     * function: when the confirm button is pressed, the game ends; 
     *           when the deny button is pressed, the quit dialog graphical 
     *           state closes, returning to the paused game graphical state
     */
    @Override
    public void accept(String cmd) {
        switch (cmd) {
            case GameUAVConfirmButton.cmd :
                break;
            case GameUAVDenyButton.cmd :
                break;
        }
    }
    
    /*
     * deactivate
     * param: N/A
     * returns: N/A
     * function: disables the confirm and deny buttons
     */
    @Override
    public void deactivate(){
        confirmBtn.setActive(false);
        denyBtn.setActive(false);
    }
}
