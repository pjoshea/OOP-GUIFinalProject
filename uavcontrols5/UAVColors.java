/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Enum: UAVCanvas
 * Purpose: A type which delineates the possible colors for the planes in both 
 *          simulation and game modes, as well as providing their 
 *          related Color object.
 */

package uavcontrols5;

import java.awt.Color;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

public enum UAVColors {
    NONE   ("Blank", new Color(0,0,0,0)),
    BLACK  ("Black", Color.BLACK),
    WHITE  ("White", Color.WHITE),
    RED    ("Red", Color.RED),
    GREEN  ("Green", Color.GREEN),
    ORANGE ("Orange", Color.ORANGE),
    YELLOW ("Yellow", Color.YELLOW),
    BLUE   ("Blue", Color.BLUE),
    PURPLE ("Purple", new Color(128, 0, 128)),
    PINK   ("Pink", Color.PINK),
    BROWN  ("Brown", new Color(139, 69, 19)),
    
    BACKGROUND ("Background", new Color(0,0,51)),
    PLAYER     ("Player", new Color(255, 255, 0)),
    STRIKER1   ("Striker1", new Color(230, 46, 0)),
    STRIKER2   ("Striker2", new Color(187, 51, 255)),
    FIGHTER1   ("Fighter1", new Color(255, 153, 194)),
    FIGHTER2   ("Fighter2", new Color(153, 255, 187));


    protected static UAVColors[] playerColors  = {PLAYER};
    protected static UAVColors[] strikerColors = {STRIKER1, STRIKER2};
    protected static UAVColors[] fighterColors = {FIGHTER1, FIGHTER2};
    
    private final String name;
    private final Color color;
    private final static int numSimColors = 11;
    
    /*
     * UAVColors
     * param: String representation of the name of the color, 
     *        the associated Color value
     * returns: UAVColors
     * function: constructor
     */
    UAVColors(String name, Color color) {
        this.name = name;
        this.color = color;
    }
    
    public String getName() {
        return name;
    }
    
    public Color getColorValue() {
        return color;
    }
    
    /*
     * getComboBoxModel
     * param: N/A
     * returns: ComboBoxModel containing the simulation colors
     * function: creates a ComboBoxModel that contains the simulation's colors
     */
    public static ComboBoxModel getComboBoxModel() {
        UAVColors [] simulationColors = UAVColors.values();
        System.arraycopy(UAVColors.values(), 0, simulationColors, 0, numSimColors);
        return new DefaultComboBoxModel(UAVColors.values());
    }
}
