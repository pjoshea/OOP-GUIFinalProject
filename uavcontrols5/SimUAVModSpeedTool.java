/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVModSpeedTool
 * Extends: SimUAVNumericalTool
 * Purpose: Creates a frame which allows the user to adjust the speed
 *          for all planes in the simulation by a user-specified amount
 */

package uavcontrols5;

class SimUAVModSpeedTool extends SimUAVNumericalTool {   
    /*
     * SimUAVModSpeedTool
     * param: map
     * returns: SimUAVModSpeedTool
     * function: constructor
     */
    public SimUAVModSpeedTool(UAVMap map) {
        super("modSpeed", map);
        setTitle("Modify Speed");
        arrangeTabs("Speed", "(kph)");
        finalize.setCommand("modifySpeed");
        fillWindow();
        pack();
        setVisible(true);
    }
    
    /*
     * getCurrentValue
     * param: plane
     * returns: String representation of the current value of the speed
     * function: get method for the current value
     */
    @Override
    protected String getCurrentValue(SimUAVPlane plane) {
        return Double.toString(plane.getSpeed());
    }
}
