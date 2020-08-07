/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Enum: SimUAVPlaneTypes
 * Purpose: Creates a type which holds the different 
 *          subclasses of the SimUAVPlane
 */

package uavcontrols5;

public enum SimUAVPlaneTypes {
    TOY ("Toy", 3),
    DELIVERY ("Delivery", 4);

    protected final static int NUM_TYPES = 2;
    private final String name;
    private final int vertices;
    
    /*
     * SimUAVPlaneTypes
     * param: String representation of the name, number of vertices 
     *        associated with the plane type 
     * returns: SimUAVPlaneTypes
     * function: constructor
     */
    SimUAVPlaneTypes(String name, int vertices) {
        this.name = name;
        this.vertices = vertices;
    }
    
    /*
     * getNames
     * param: N/A
     * returns: String array
     * function: returns an array containing the names associated
     *           with the enum constants
     */
    protected static String [] getNames() {
        SimUAVPlaneTypes [] typeList = SimUAVPlaneTypes.values();
        String [] names = new String[NUM_TYPES];
        
        for (int i = 0; i< NUM_TYPES; i++) {
            names[i] = typeList[i].name;
        }
        return names;
    }
    
    /*
     * getName
     * param: index number of the desired name
     * returns: String representation of the name of the enum constant
     * function: taking an index, this returns the name of the enum constant 
     *           corresponding to that index
     */
    protected static String getName(int i) {
        SimUAVPlaneTypes [] typeList = SimUAVPlaneTypes.values();
        return typeList[i].name;
    }
    
    /***********************************************************
     *********************** Get Methods ***********************
     ***********************************************************/
    
    protected String getName() {
        return name;
    }
    
    protected int getVerticesNum() {
        return vertices;
    }
}
