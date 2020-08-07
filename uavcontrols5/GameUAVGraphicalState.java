/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVGraphicalState
 * Extends: JComponent
 * Purpose: Graphical States allow the program to determine what to display and 
 *          which Graphical Buttons ought to be listening to Mouse Events from 
 *          the canvas.
 */

package uavcontrols5;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

public abstract class GameUAVGraphicalState extends JComponent 
                                        implements MouseListener
{
    public final UAVMap map;
    public final UAVCanvas canvas;
    protected String title;
    protected GameUAVScreens type;

    /*
     * GameUAVGraphicalState
     * param: map, canvas, title, screenType
     * returns: GameUAVGraphicalState
     * function: constructor
     */
    GameUAVGraphicalState(UAVMap m, UAVCanvas c, String t, GameUAVScreens type) 
    {
        map = m;
        canvas = c;
        title = t;
        this.type = type;
        canvas.addMouseListener(this);
    }
  
/****************************************************************************
 ******************************* Placeholders *******************************
 ****************************************************************************/
    
    public void draw(Graphics2D g) {}
    
    public void accept(String command) {}
    
    public void deactivate() {}
    
    /**
     * Invoked when the mouse button has been clicked (pressed
     * and released) on a component.
     */
    public void mouseClicked(MouseEvent e) {}

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e) {}

    /**
     * Invoked when a mouse button has been released on a component.
     */
    public void mouseReleased(MouseEvent e) {}

    /**
     * Invoked when the mouse enters a component.
     */
    public void mouseEntered(MouseEvent e) {}

    /**
     * Invoked when the mouse exits a component.
     */
    public void mouseExited(MouseEvent e) {} 
}
