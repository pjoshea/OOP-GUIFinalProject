/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVModAngSpeedTool
 * Extends: SimUAVNumericalTool
 * Purpose: Creates a frame which allows the user to adjust the angular speed 
 *          for all planes in the simulation by a user-specified amount
 */

package uavcontrols5;

class SimUAVModAngSpeedTool extends SimUAVNumericalTool {

    /*
     * SimUAVModAngSpeedTool
     * param: map
     * returns: SimUAVModAngSpeedTool
     * function: constructor
     */
    public SimUAVModAngSpeedTool(UAVMap map) {
        super("modAngSpeed", map);
        setTitle("Change Angular Speed");
        arrangeTabs("Angular Speed", "(degrees per second)");
        finalize.setCommand("changeAngSpeed");
        fillWindow();
        pack();
        setVisible(true);
    }
    
    /*
     * getCurrentValue
     * param: plane
     * returns: the current value of the angular speed as a String
     * function: get method for the current value 
     */
    @Override
    protected String getCurrentValue(SimUAVPlane plane) {
        return Double.toString(plane.getDirection());
    }
}
