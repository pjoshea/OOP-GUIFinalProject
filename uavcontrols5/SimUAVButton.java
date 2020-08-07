/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: UAVButton
 * Extends: JButton
 * Purpose: Provides a superclass for methods useful to all buttons.
 */

package uavcontrols5;

import java.awt.Insets;
import javax.swing.*;

abstract class SimUAVButton extends JButton {
    String text, actionCommand;
    Icon icon;
    
    /*
     * UAVButton
     * param: title, action command, image
     * returns: UAVButton 
     * function: constructor
     */
    SimUAVButton(String title, String keyword, Icon graphic){
        text = title;
        this.setToolTipText(text);
        
        actionCommand = keyword;
        setActionCommand(actionCommand);
        
        this.icon = graphic;
        this.setIcon(icon);
        
        setEnabled(true);
        setMargin(new Insets(0,0,0,0));
        setVisible(true);
    }    
    
    /*
     * UAVButton
     * param: title, action command
     * returns: UAVButton
     * function: constructor
     */
    SimUAVButton(String title, String keyword){
        text = title;
        this.setToolTipText(text);
        this.setText(text);
        
        actionCommand = keyword;
        setActionCommand(actionCommand);
        
        setEnabled(true);
        setMargin(new Insets(0,0,0,0));
        setVisible(true);
    }    

    /*
     * toggle
     * param: N/A
     * returns: N/A
     * function: placeholder for useful applications in subclasses.
     */
    public void toggle() {}
}