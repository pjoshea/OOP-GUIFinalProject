/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVExitDialogState
 * Extends: GameUAVGraphicalState
 * Purpose: The Exit Dialog allows the user to decide whether or not to 
 *          exit to the desktop closing the application completely.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

class GameUAVExitDialogState extends GameUAVGraphicalState {
    private static final String DEF_TITLE = "Close Application?";
    private final Font titleFont = new Font(Font.DIALOG, Font.PLAIN, 25);
    private final Font queryFont = new Font(Font.DIALOG, Font.PLAIN, 20);
    
    private Rectangle queryBox;
    private String query = "Do you want to exit to the desktop?";
    private GameUAVConfirmButton confirmBtn;
    private GameUAVDenyButton denyBtn;
    
    /*
     * GameUAVExitDialogState
     * param: map, canvas
     * returns: GameUAVExitDialogState
     * function: constructor
     */
    public GameUAVExitDialogState(UAVMap m, UAVCanvas c) {
        super(m, c, DEF_TITLE, GameUAVScreens.ExitApplicationDialog);
        queryBox = new Rectangle(canvas.getWidth()/2 - 200,
                                 canvas.getHeight()/2 - 100, 400, 100);
        confirmBtn = new GameUAVConfirmButton(map, this, 
                    new Point2D.Double(canvas.getWidth()/2 - 150,
                                       canvas.getHeight()/2 + 25),
                    new Dimension(100, 50));
        denyBtn = new GameUAVDenyButton(map, this,
                    new Point2D.Double(canvas.getWidth()/2 + 50, 
                                       canvas.getHeight()/2 + 25), 
                    new Dimension(100, 50));
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
        g.drawString(title, hAlign, 25);
        
        g.setStroke(new BasicStroke(3));
        g.draw(queryBox);
        
        g.setStroke(new BasicStroke(1));
        g.setFont(queryFont);
        FontMetrics fm = g.getFontMetrics();
        int hz = fm.stringWidth(query)/2;
        int vt = fm.getAscent()/2;
        g.drawString(query, canvas.getWidth()/2 - hz, canvas.getHeight()/2 - 50 + vt);
        
        confirmBtn.drawButton(g);
        denyBtn.drawButton(g);
    }
    
    /*
     * accept
     * param: command String
     * returns: N/A
     * function: confirm button quits application entirely, deny button exits
     *           to the previous graphical state
     */
    @Override
    public void accept(String cmd) {
        switch (cmd) {
            case GameUAVConfirmButton.cmd:
                map.closeApplication();
                break;
            case GameUAVDenyButton.cmd:
                canvas.goTo(GameUAVScreens.MainMenu);
        }
    }
    
    /*
     * deactivate
     * param: N/A
     * returns: N/A
     * function: disables the confirm and deny buttons
     */
    @Override
    public void deactivate() {
        confirmBtn.setActive(false);
        denyBtn.setActive(false);
    }
}
