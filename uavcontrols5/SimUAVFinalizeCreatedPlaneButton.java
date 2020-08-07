/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVFinalizeButton
 * Extends: UAVButton
 * Purpose: Tells the program that the plane in the Plane Creator Window is
 *          done being modified.
 */

package uavcontrols5;

class SimUAVFinalizeCreatedPlaneButton extends SimUAVButton {
    
    /*
     * SimUAVFinalizeCreatedPlaneButton
     * param: map
     * returns: SimUAVFinalizeCreatedPlaneButton
     * function: constructor
     */
    public SimUAVFinalizeCreatedPlaneButton(UAVMap map) {
        super("Create Plane", "Create Plane");
    }
}
