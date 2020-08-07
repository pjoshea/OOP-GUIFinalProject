/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVGridButton
 * Extends: UAVButton
 * Purpose: Toggles the grid lines
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVGridButton extends SimUAVButton{
    
    /*
     * SimUAVGridButton
     * param: N/A
     * returns: SimUAVGridButton
     * function: constructor
     */
    public SimUAVGridButton() {
        super("Toggle grid lines", "Grid", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/grid.jpg"));
    }
}
