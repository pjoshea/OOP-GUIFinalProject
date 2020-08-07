/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVModColorTool
 * Extends: SimUAVTextualTool
 * Purpose: Creates a frame which allows the user to adjust the color
 *          for all planes in the simulation to a user-specified color
 */

package uavcontrols5;

import javax.swing.JComboBox;


public class SimUAVModColorTool extends SimUAVTextualTool {
    protected JComboBox [] changeColor;
    private String [] allowedColors = {"", "Black", "White", "Red", "Green", 
                                         "Orange", "Yellow", "Blue", "Purple", 
                                         "Pink", "Brown" };
    /*
     * SimUAVModColorTool
     * param: map
     * returns: SimUAVModColorTool
     * function: constructor
     */
    SimUAVModColorTool(UAVMap map) {
        super("modColor", map);
        setTitle("Modify Color");
        finalize.setCommand("modifyColor");
        int textFieldCount = SimUAVPlaneTypes.NUM_TYPES + 1;

        textFields = new JComboBox[textFieldCount];
        for (int i = 0; i < textFieldCount; i++) {
            textFields[i] = new JComboBox(allowedColors);
        }
        
        arrangeTabs("Color");
        fillWindow();
        pack();
        setVisible(true);
    }
}

