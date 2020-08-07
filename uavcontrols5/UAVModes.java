/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Enum: UAVModes
 * Purpose: Provides a type which holds the two different modes that the 
 *          program can operate in - game or simulation
 */

package uavcontrols5;

public enum UAVModes {
    GAME ("Game", "game"),
    SIMULATION ("Simulation", "sim");
    
    private final String type;
    private final String abbreviation;
    
    /*
     * UAVModes
     * param: String representation of the type, String abbreviation of type
     * returns: UAVModes
     * function: constructor
     */
    UAVModes(String type, String abbreviation) { 
        this.type = type; 
        this.abbreviation = abbreviation;
    }
    /**********************************
     ******** Set & Get Methods *******
     **********************************/
    String getName() { return type; }
    String getAbbr() { return abbreviation; }
}
