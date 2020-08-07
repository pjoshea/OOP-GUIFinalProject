/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVClockwiseTurnButton
 * Extends: UAVButton
 * Purpose: rotates the selected plane clockwise by a predetermined amount
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVClockwiseTurnButton extends SimUAVButton {
    
    /*
     * SimUAVClockwiseTurnButton
     * param: N/A
     * returns: SimUAVClockwiseTurnButton
     * function: constructor
     */
    public SimUAVClockwiseTurnButton() {
        super("Turn Clockwise", "Clockwise", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/clockwise_turn.jpg"));
    }   
}
