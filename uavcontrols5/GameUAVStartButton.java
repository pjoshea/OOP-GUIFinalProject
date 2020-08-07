/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVStartButton
 * Extends: GameUAVGraphicalButton
 * Purpose: Tells the program to begin a new game.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

class GameUAVStartButton extends GameUAVGraphicalButton {
    protected static final String cmd = "act:BeginGame";
    private static final String defaultText = "Start!";
    private static final Color defaultBCol = Color.WHITE;
    private static final Color defaultTCol = Color.WHITE;
    private static final Color defaultBGCol = new Color(0,0,51);
    private static final Font defaultF = new Font(Font.DIALOG, Font.PLAIN, 50);
    
    /*
     * GameUAVStartButton
     * param: map, parent graphical state, position, dimensions
     * returns: GameUAVStartButton
     * function: constructor
     */
    public GameUAVStartButton(UAVMap m, GameUAVGraphicalState gs, Point2D p, Dimension d) {
        super(m, gs, p, d, defaultText, defaultBCol, defaultTCol, 
              defaultBGCol, defaultF, cmd);
        boxStrokeWidth = new BasicStroke(10);
    }
}
