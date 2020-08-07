/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVConfirmButton
 * Extends: GameUAVGraphicalButton
 * Purpose: Provides the confirm button, which appears in the in-game dialogs,
 *          and tells the graphical state that it appears in that the query the 
 *          state posed was answered in the affirmative.
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;

class GameUAVConfirmButton extends GameUAVGraphicalButton {
    protected static final String cmd = "act:Confirm";
    private static final String defaultText = "Yes";
    private static final Color defaultBCol = Color.WHITE;
    private static final Color defaultTCol = Color.WHITE;
    private static final Color defaultBGCol = new Color(0,0,51);
    private static final Font defaultF = new Font(Font.DIALOG, Font.PLAIN, 25);
    
    /*
     * GameUAVConfirmButton
     * param: map, parent graphical state, position, dimensions
     * returns: GameUAVConfirmButton
     * function: constructor
     */
    public GameUAVConfirmButton(UAVMap m, GameUAVGraphicalState gs, Point2D p, 
                            Dimension d) {
        super(m, gs, p, d, defaultText, defaultBCol, defaultTCol, defaultBGCol,
              defaultF, cmd);
    }
}
