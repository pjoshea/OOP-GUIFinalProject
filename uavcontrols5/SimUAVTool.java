/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVTool
 * Extends: JFrame
 * Purpose: Provides methods for working with data common to its subclasses,
 *          which allow the user to modify data about the simulation.
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class SimUAVTool extends JFrame implements ChangeListener
{
    protected UAVMap map;
    protected String toolType;
    
    protected JPanel contentPanel;
    protected SimUAVFinalizeButton finalize;
    protected JTabbedPane tabbedPane;
    protected final Color bgColor = UIManager.getColor( "Panel.background" );
    protected SimUAVPlaneTypes simPlaneTypes;
    
    /*
     * SimUAVTool
     * param: type, map
     * returns: SimUAVTool
     * function: constructor
     */
    public SimUAVTool(String toolType, UAVMap map) {
        this.map = map;
        this.toolType = toolType;
        
        setLocation(1000, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        finalize = new SimUAVFinalizeButton(this.map);
    }
    
    /*
     * fillWindow
     * param: N/A
     * returns: N/A
     * function: adds the tabbed pane to the frame
     */
    protected void fillWindow(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        tabbedPane.setVisible(true);
        add(tabbedPane, c);
        
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.SOUTHEAST;
        add(finalize, c);
    }
    
    /*
     * getCurrentValue
     * param: plane
     * returns: String representation of the current value
     * function: generic get method
     * notes: placeholder for outside access to subclasses
     */
    protected String getCurrentValue(SimUAVPlane plane) {return null;}

    /*
     * stateChanged
     * param: ChangeEvent
     * returns: N/A
     * function: resizes the tab to fit
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        Component tab = tabbedPane.getSelectedComponent();
        tabbedPane.setPreferredSize(tab.getPreferredSize());
        pack();
    }
    
}
