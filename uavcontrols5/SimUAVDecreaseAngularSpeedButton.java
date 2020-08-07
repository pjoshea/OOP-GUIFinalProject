/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVDecreaseAngularSpeedButton
 * Extends: UAVButton
 * Purpose: Decreases the angular speed at which the selected plane is rotating.
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVDecreaseAngularSpeedButton extends SimUAVButton {

    /*
     * SimUAVDecreaseAngularSpeedButton
     * param: N/A
     * returns: SimUAVDecreaseAngularSpeedButton
     * function: constructor
     */
    public SimUAVDecreaseAngularSpeedButton() {
        super("Decrease the selected plane's angular speed", 
              "Decrease Angular Speed", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/decrease_angular_speed.jpg"));
    }
    
}
