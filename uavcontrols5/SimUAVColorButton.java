/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVColorButton
 * Extends: UAVButton
 * Purpose: Toggles the color (full color to black & white and vice versa).
 */

package uavcontrols5;

import javax.swing.ImageIcon;

public class SimUAVColorButton extends SimUAVButton {    
    
    /*
     * SimUAVColorButton
     * param: N/A
     * returns: SimUAVColorButton
     * function: constructor
     */
    SimUAVColorButton() {
        super("Toggle Plane Color", "Color", 
              new ImageIcon(System.getProperty("user.dir") +
                            "/images/color.jpg"));
    }
}
