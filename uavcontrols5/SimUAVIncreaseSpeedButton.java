/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVIncreaseSpeedButton
 * Extends: UAVButton
 * Purpose: Increases the speed of the selected plane
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVIncreaseSpeedButton extends SimUAVButton {

    /*
     * SimUAVIncreaseSpeedButton
     * param: N/A
     * returns: SimUAVIncreaseSpeedButton
     * function: constructor
     */
    public SimUAVIncreaseSpeedButton() {
        super("Increase the selected plane's speed", "Increase Speed", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/increase_speed.jpg"));
    }
    
}
