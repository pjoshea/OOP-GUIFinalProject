/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVWiddershinsTurnButton
 * Extends: UAVButton
 * Purpose: Turns the selected plane counterclockwise by a predetermined amount.
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVWiddershinsTurnButton extends SimUAVButton {
    
    /*
     * SimUAVWiddershinsTurnButton
     * param: N/A
     * returns: SimUAVWiddershinsTurnButton
     * function: constructor
     */
    public SimUAVWiddershinsTurnButton() {
        super("Turn Widdershins", "Widdershins", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/counterclockwise_turn.jpg"));
    }    
}
