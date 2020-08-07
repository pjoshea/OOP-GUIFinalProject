/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVFinalizeButton
 * Extends: UAVButton
 * Purpose: Tells the program that the user wants to apply the changes 
 *          made in the tool window that is currently open and then 
 *          close the tool window.
 */

package uavcontrols5;

import java.awt.Dimension;

class SimUAVFinalizeButton extends SimUAVButton {
    
    /*
     * SimUAVFinalizeButton
     * param: map
     * returns: SimUAVFinalizeButton
     * function: constructor
     */
    public SimUAVFinalizeButton(UAVMap map) {
        super("Okay", "OK");
        setPreferredSize(new Dimension(100, 30));
    }
    
    /*
     * setCommand
     * param: command
     * returns: N/A
     * function: sets the action command to the new command
     */
    public void setCommand(String command) {
        actionCommand = command;
        setActionCommand(actionCommand);
    }
}
