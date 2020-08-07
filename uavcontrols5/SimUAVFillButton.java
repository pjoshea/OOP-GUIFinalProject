/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVFillButton
 * Extends: UAVButton
 * Purpose: Toggles the fill of all planes (full color fill to filled 
 *          with background color)
 */

package uavcontrols5;

import javax.swing.ImageIcon;

public class SimUAVFillButton extends SimUAVButton {   
    
    /*
     * SimUAVFillButton
     * param: N/A
     * returns: SimUAVFillButton
     * function: constructor
     */
    SimUAVFillButton() {
        super("Toggle fill planes", "Fill", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/fill.jpg"));
    }
}
