/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVDecreaseSpeedButton
 * Extends: UAVButton
 * Purpose: Decreases the speed of the selected plane.
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVDecreaseSpeedButton extends SimUAVButton {
    
    /*
     * SimUAVDecreaseSpeedButton
     * param: N/A
     * returns: SimUAVDecreaseSpeedButton
     * function: constructor
     */
    public SimUAVDecreaseSpeedButton() {
        super("Decrease the selected plane's speed", "Decrease Speed", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/decrease_speed.jpg"));
    }
}
