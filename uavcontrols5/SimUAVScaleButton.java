/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVScaleButton
 * Extends: UAVButton
 * Purpose: Toggles the visibility of the scale
 */

package uavcontrols5;

import javax.swing.ImageIcon;

public class SimUAVScaleButton extends SimUAVButton {
    
    /*
     * SimUAVScaleButton
     * param: N/A
     * returns: SimUAVScaleButton
     * function: constructor
     */
    SimUAVScaleButton() {
        super("Toggle yardstick", "Scale",
              new ImageIcon(System.getProperty("user.dir") +
                            "/images/scale.jpg"));
    }
}
