/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVDecreaseAltitudeButton
 * Extends: UAVButton
 * Purpose: Decreases the altitude at which the selected plane is flying.
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVDecreaseAltitudeButton extends SimUAVButton {

    /*
     * SimUAVDecreaseAltitudeButton
     * param: N/A
     * returns: SimUAVDecreaseAltitudeButton
     * function: constructor
     */
    public SimUAVDecreaseAltitudeButton() {
        super("Decrease the selected plane's altitude", "Decrease Altitude", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/decrease_altitude.jpg"));
    }
    
}
