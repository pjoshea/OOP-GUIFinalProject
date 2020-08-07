/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVIncreaseAngularSpeedButton
 * Extends: UAVButton
 * Purpose: Increases the angular speed of the selected plane
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVIncreaseAngularSpeedButton extends SimUAVButton {

    /*
     * SimUAVIncreaseAngularSpeedButton
     * param: N/A
     * returns: SimUAVIncreaseAngularSpeedButton
     * function: constructor
     */
    public SimUAVIncreaseAngularSpeedButton() {
        super("Increase the selected plane's angular speed", 
              "Increase Angular Speed", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/increase_angular_speed.jpg"));
    }
    
}
