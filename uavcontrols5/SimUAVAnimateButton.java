/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVAnimateButton
 * Extends: UAVButton
 * Purpose: Pauses or unpauses the animation of the planes.
 */

package uavcontrols5;

import javax.swing.ImageIcon;

class SimUAVAnimateButton extends SimUAVButton {
    private boolean playing = true;
    private final String playText = "Pause Animation";
    private final String pauseText = "Play Animation";
    private ImageIcon playIcon = new ImageIcon(System.getProperty("user.dir") + 
                                               "/images/pause.jpg");
    private ImageIcon pauseIcon = new ImageIcon(System.getProperty("user.dir") + 
                                                "/images/play.jpg");
    
    /*
     * SimUAVAnimateButton
     * param: N/A
     * returns: SimUAVAnimateButton
     * function: constructor
     */
    public SimUAVAnimateButton() {
        super("Pause Animation", "Animate", 
              new ImageIcon(System.getProperty("user.dir") + 
                            "/images/pause.jpg"));
    }
    
    
    /*
     * toggle
     * param: N/A
     * returns: N/A
     * function: toggles the look of the button.
     */
    @Override
    public void toggle() {
        if (!playing) { 
            icon = playIcon; 
            playing = true; 
            setIcon(playIcon);
            setToolTipText(playText);
        } else { 
            icon = pauseIcon;
            setIcon(pauseIcon);
            playing = false;
            setToolTipText(pauseText);
        }
    }
}
