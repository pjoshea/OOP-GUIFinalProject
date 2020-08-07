/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVQuitButton
 * Extends: GameUAVGraphicalButton
 * Purpose: Tells the program to go to the completed game gamestate.
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class GameUAVQuitButton extends GameUAVGraphicalButton {
    protected static final String cmd = "act:EndGame";
    private static final String defaultText = "Quit";
    private static final Color defaultBCol = Color.WHITE;
    private static final Color defaultTCol = Color.WHITE;
    private static final Color defaultBGCol = new Color(0,0,51);
    private static final Font defaultF = new Font(Font.DIALOG, Font.PLAIN, 25);
    
    /*
     * GameUAVQuitButton
     * param: map, parent graphical state, position, dimensions
     * returns: GameUAVQuitButton
     * function: constructor
     */
    public GameUAVQuitButton(UAVMap m, GameUAVGraphicalState gs, Point2D p, 
                             Dimension d) {
        super(m, gs, p, d, defaultText, defaultBCol, defaultTCol, 
              defaultBGCol, defaultF, cmd);
    }
}
