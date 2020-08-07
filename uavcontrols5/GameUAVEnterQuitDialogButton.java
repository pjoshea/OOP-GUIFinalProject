/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVEnterQuitDialogButton
 * Extends: GameUAVGraphicalButton
 * Purpose: Provides the enter quit dialog button, which tells the program to 
 *          switch to the QuitGame Dialog game state
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;

class GameUAVEnterQuitDialogButton extends GameUAVGraphicalButton{
    private static final String defaultText = "Quit";
    private static final String cmd = "create:QuitDialog";
    private static final Color defaultBCol = Color.WHITE;
    private static final Color defaultTCol = Color.WHITE;
    private static final Color defaultBGCol = new Color(0,0,51);
    private static final Font defaultF = new Font(Font.DIALOG, Font.PLAIN, 25);
    
    /*
     * GameUAVEnterQuitDialogButton
     * param: map, parent graphical state, position, dimensions
     * returns: GameUAVEnterQuitDialogButton
     * function: constructor
     */
    public GameUAVEnterQuitDialogButton(UAVMap m, GameUAVGraphicalState gs,
                                        Point2D p, Dimension d)
    {
        super(m, gs, p, d, defaultText, defaultBCol, defaultTCol,
              defaultBGCol, defaultF, cmd);
    }
}
