/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVActiveGameState
 * Extends: GameUAVGraphicalState
 * Purpose: Provides the Active Game state, which will draw all of the game 
 *          objects that the map is holding when called to do so.
 * Notes: This class abstracts away the call to map to draw the game objects
 *        and provides a means of determining whether or not aimMode is active.
 */

package uavcontrols5;

import java.awt.Graphics2D;

class GameUAVActiveGameState extends GameUAVGraphicalState {
    private static final String DEF_TITLE = "Evasion";
    public boolean aimMode = false;
    
    public GameUAVActiveGameState(UAVMap m, UAVCanvas c) {
        super(m, c, DEF_TITLE, GameUAVScreens.ActiveGame);
    }
        
    /**
     * draw
     * @param: Graphics2D
     * @notes: code abstracting away the drawGameObjects call to the map
     */
    @Override
    public void draw(Graphics2D g) {
        map.drawGameObjects(g);
    }
    
    
    /**
     * setAimMode
     * @param: boolean 
     * @notes: toggles aimMode
     */
    void setAimMode(boolean b) {
        aimMode = b;
    }
}
