/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVIncreaseAltitudeButton
 * Extends: UAVButton
 * Purpose: Increases the altitude of the selected plane
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVIncreaseAltitudeButton extends SimUAVButton {
    
    /*
     * SimUAVIncreaseAltitudeButton
     * param: N/A
     * returns: SimUAVIncreaseAltitudeButton
     * function: constructor
     */
    public SimUAVIncreaseAltitudeButton() {
        super("Increase the selected plane's altitude", "Increase Altitude", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/increase_altitude.jpg"));
    }   
}
