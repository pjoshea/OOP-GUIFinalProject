/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVRepairPlaneButton
 * Extends: UAVButton
 * Purpose: A button which will return crashed planes to the air.
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVRepairPlaneButton extends SimUAVButton {

    /*
     * SimUAVRepairPlaneButton
     * param: N/A
     * returns: SimUAVRepairPlaneButton
     * function: constructor
     */
    public SimUAVRepairPlaneButton() {
        super("Repair the selected plane", "Repair Plane", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/repair.jpg"));
    }    
}
