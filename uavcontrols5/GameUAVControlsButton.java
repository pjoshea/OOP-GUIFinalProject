/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVControlsButton
 * Extends: GameUAVGraphicalButton
 * Purpose: Provides the controls button, which appears on the main menu and 
 *          when pushed, tells the program to go to the controls menu.
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;

public class GameUAVControlsButton extends GameUAVGraphicalButton{
    protected static final String cmd = "goto:Controls";
    private static final String defaultText = "Controls";
    private static final Color defaultBCol = Color.WHITE;
    private static final Color defaultTCol = Color.WHITE;
    private static final Color defaultBGCol = new Color(0,0,51);
    private static final Font defaultF = new Font(Font.DIALOG, Font.PLAIN, 25);
    
    /*
     * GameUAVControlsButton
     * param: map, parent graphical state, position, dimensions
     * returns: GameUAVControlsButton
     * function: constructor
     */
    public GameUAVControlsButton(UAVMap m, GameUAVGraphicalState gs, Point2D p, 
                                 Dimension d) 
    {
        super(m, gs, p, d, defaultText, defaultBCol, defaultTCol, 
              defaultBGCol, defaultF, cmd);
    }
}
