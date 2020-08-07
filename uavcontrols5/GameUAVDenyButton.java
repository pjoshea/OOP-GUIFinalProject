/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVDenyButton
 * Extends: GameUAVGraphicalButton
 * Purpose: Provides the deny button, which tells the dialog on which it appears
 *          that the query was responded to in the negative.
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;

class GameUAVDenyButton extends GameUAVGraphicalButton {
    protected static final String cmd = "act:Deny";
    private static final String defaultText = "No";
    private static final Color defaultBCol = Color.WHITE;
    private static final Color defaultTCol = Color.WHITE;
    private static final Color defaultBGCol = new Color(0,0,51);
    private static final Font defaultF = new Font(Font.DIALOG, Font.PLAIN, 25);
    
    /*
     * GameUAVDenyButton
     * param: map, parent graphical state, position, dimensions
     * returns: GameUAVDenyButton
     * function: constructor
     */
    public GameUAVDenyButton(UAVMap m, GameUAVGraphicalState gs, Point2D p, Dimension d) {
        super(m, gs, p, d, defaultText, defaultBCol, defaultTCol,
              defaultBGCol, defaultF, cmd);
    }
}
