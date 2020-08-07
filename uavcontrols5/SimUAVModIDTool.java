/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVModIDTool
 * Extends: SimUAVTool
 * Purpose: Creates a frame which allows the user to adjust the ID
 *          for all planes in the simulation to a user-specified name
 * Notes: apparently this is unfinished? I've completely forgot 
 *        why I gave up on it in the first place. I decided that there was a 
 *        rather large section of a previous incarnation(?) that was 
 *        worth saving, but I can't remember why I didn't just implement it
 */

package uavcontrols5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


class SimUAVModIDTool extends SimUAVTool implements DocumentListener, ActionListener {
    private JTextField [] changeName;
    private int numTextFields;
    
    /*
     * SimUAVModIDTool
     * param: map
     * returns: SimUAVModIDTool
     * function: constructor
     */
    public SimUAVModIDTool(UAVMap map) {
        super("modName", map);
        setTitle("Rename Plane");
        arrangeTabs();
        finalize.setCommand("renamePlane");
        fillWindow();
        pack();
        setVisible(true);
    }
    
    /*
     * arrangeTabs
     * param: N/A
     * returns: N/A
     * function: arranges the tabbed pane which holds the planes' IDs
     */
    private void arrangeTabs() {
        tabbedPane = new JTabbedPane();
        changeName = new JTextField[map.getNumberPlanes()];
        
        for (int i = 0; i < map.getNumberPlanes(); i++) {
            
        }
    }
    
    /*
     * getCurrentValue
     * param: plane
     * returns: the plane's ID
     * function: get method for current value
     */
    @Override
    protected String getCurrentValue(SimUAVPlane plane) {
        return plane.id;
    }
    
    /*
     * documentChange
     * param: DocumentEvent
     * returns: N/A
     * function: saves the id entered in the 
     */
    private void documentChange(DocumentEvent e) { }
    
    @Override
    public void changedUpdate(DocumentEvent e) {
        documentChange(e);
    }
    @Override
    public void removeUpdate(DocumentEvent e) {
        documentChange(e);
    }
    @Override
    public void insertUpdate(DocumentEvent e) {
        documentChange(e);
    }
    
    /*
     * actionPerformed
     * param: ActionEvent
     * returns: N/A
     * function: changes the name of the plane to the user-entered text
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < changeName.length; i++) {
            String oldName = changeName[i].getName();
            String newName = changeName[i].getText();
            if (!(oldName.equals(newName))) {
                map.changePlaneID(oldName, changeName[i].getText());
            }
        }
    }
}
