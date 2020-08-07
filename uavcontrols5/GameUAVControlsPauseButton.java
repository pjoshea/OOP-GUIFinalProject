/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVControlsPauseButton
 * Extends: GameUAVGraphicalButton
 * Purpose: Provides the controls pause button, which appears on the paused
 *          screen in the middle of a game, and tells the game to display 
 *          control information without exiting the current game.
 * Notes: The controls pause screen has yet to be implemented, and as such this
 *        button doesn't appear on the in-game pause screen.
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.Point2D;


class GameUAVControlsPauseButton extends GameUAVGraphicalButton {
    protected static final String cmd = "goto:ControlScreenPausedGame";
    private static final String defaultText = "Controls";
    private static final Color defaultBCol = Color.WHITE;
    private static final Color defaultTCol = Color.WHITE;
    private static final Color defaultBGCol = new Color(0,0,51);
    private static final Font defaultF = new Font(Font.DIALOG, Font.PLAIN, 25);
    
    /*
     * GameUAVControlsPauseButton
     * param: map, parent graphical state, position, dimensions
     * returns: GameUAVControlsPauseButton
     * function: constructor
     */
    public GameUAVControlsPauseButton(UAVMap m, GameUAVGraphicalState par, 
                                      Point2D p, Dimension d) {
        super(m, par, p, d, defaultText, defaultBCol, defaultTCol, 
              defaultBGCol, defaultF, cmd);
    }
}
