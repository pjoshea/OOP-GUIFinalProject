/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVModAltTool
 * Extends: SimUAVNumericalTool
 * Purpose: Creates a frame which allows the user to adjust the altitude for 
 *          all planes in the simulation by a user-specified amount
 */

package uavcontrols5;

class SimUAVModAltTool extends SimUAVNumericalTool {
    
    /*
     * SimUAVModAltTool
     * param: map
     * returns: SimUAVModAltTool
     * function: constructor 
     */
    public SimUAVModAltTool(UAVMap map) {
        super("modAlt", map);
        setTitle("Modify Altitude");
        arrangeTabs("Altitude", "(km)");
        finalize.setCommand("modifyAltitude");
        fillWindow();
        pack();
        setVisible(true);
    }
    
    /*
     * getCurrentValue
     * param: plane
     * returns: the current value of the altitude as a String
     * function: get method for the current value 
     */
    @Override
    protected String getCurrentValue(SimUAVPlane plane) {
        return Integer.toString(plane.getAltitude());
    }
}
