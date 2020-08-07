/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVModDirTool
 * Extends: SimUAVNumericalTool
 * Purpose: Creates a frame which allows the user to adjust the direction
 *          for all planes in the simulation by a user-specified amount
 */


package uavcontrols5;

class SimUAVModDirTool extends SimUAVNumericalTool {

    /*
     * SimUAVModDirTool
     * param: map
     * returns: SimUAVModDirTool
     * function: constructor
     */
    public SimUAVModDirTool(UAVMap map) {
        super("modDir", map);
        setTitle("Change Direction");
        
        arrangeTabs("Direction", "(degrees)");
        
        finalize.setCommand("changeDirection");
        
        fillWindow();
        pack();
        setVisible(true);
    }
    
    /*
     * getCurrentValue
     * param: plane
     * returns: the current value of the direction as a String
     * function: get method for the current value 
     */
    @Override
    protected String getCurrentValue(SimUAVPlane plane) {
        return Double.toString(plane.getDirection());
    }
}
