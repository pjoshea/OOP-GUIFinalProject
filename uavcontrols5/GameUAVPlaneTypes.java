/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Enum: GameUAVPlaneTypes
 * Purpose: Provides a list of all extensions of the GameUAVPlane abstract class
 *          which is useful for knowing which class a 
 *          specific instance of the GameUAVPlane is.
 */

package uavcontrols5;

public enum GameUAVPlaneTypes {
    PLAYER  ("Player", 3, UAVColors.playerColors),
    STRIKER ("Striker", 4, UAVColors.strikerColors),
    FIGHTER ("Fighter", 4, UAVColors.fighterColors);
    
    String name;
    int vertices;
    UAVColors [] allowableColors;
    
    /*
     * GameUAVPlaneTypes
     * param: typename, number of vertices, array of associated colors
     * returns: GameUAVPlaneTypes
     * function: constructor
     */
    GameUAVPlaneTypes(String n, int v, UAVColors [] c) {
        name = n;
        vertices = v;
        allowableColors = c;
    }
}
