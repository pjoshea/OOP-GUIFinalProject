/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVTextualTool
 * Extends: SimUAVTool
 * Purpose: A subclass of SimUAVTool which provides methods for tools that only
 *          work with choices of data from predetermined lists.
 * Notes: Unlike in the UAVNumericalTool, the duty of initializing the array 
 *        of the JComboBoxes, so that the choices are tailored to the tool
 */

package uavcontrols5;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SimUAVTextualTool extends SimUAVTool implements ActionListener,
                                                       ChangeListener
{
    protected JComboBox [] textFields;
    protected JLabel textLabel;
    private int index = 0;
    
    /*
     * SimUAVTextualTool
     * param: type, map
     * returns: SimUAVTextualTool
     * function: constructor
     */
    SimUAVTextualTool(String toolType, UAVMap map) {
        super(toolType, map);
        finalize.addActionListener(this);
    }
    
    /*
     * arrangeTabs
     * param: the property to be changed
     * returns: N/A
     * function: creates and arranges the tabs for all planes and 
     *           for each type of plane.
     */
    protected void arrangeTabs(String keyword) {
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Change " + keyword + "for all planes",
                          textualAllPlaneTab(keyword));
        index++;
        
        for (int i = 0; i < SimUAVPlaneTypes.NUM_TYPES; i++) {
            JPanel tab = textualTypeTab(keyword, SimUAVPlaneTypes.getName(i));
            tabbedPane.addTab(SimUAVPlaneTypes.getName(i), tab);
            index++;
        }
        
        tabbedPane.addChangeListener(this);
    }

    /*
     * textualTypeTab
     * param: the property to be changed, the plane type that 
     *        the tab will affect 
     * returns: the completed tab
     * function: creates a tab which will modify the property that the tool 
     *           affects for the given plane type
     */
    protected JPanel textualTypeTab(String keyword, String planeType) {
        JPanel tab = new JPanel();
        tab.setLayout(new GridBagLayout());
        tab.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 2, 10, 2), 
                BorderFactory.createTitledBorder("Change " + keyword + 
                                                 " for UAV Type: " +  planeType)
        ));
        
        GridBagConstraints c = new GridBagConstraints();
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        tab.add(textFields[index], c);
        
        return tab;
    }

    /*
     * textualAllPlaneTab
     * param: the property to be changed
     * returns: the completed tab
     * function: creates a tab which will modify the property that the tool 
     *           affects for all planes
     */
    protected JPanel textualAllPlaneTab(String keyword) {
        JPanel tab = new JPanel();
        tab.setPreferredSize(new Dimension(225, 225));
        tab.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        tab.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 2, 10, 2), 
                BorderFactory.createTitledBorder("Change " + keyword)
        ));
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints f = new GridBagConstraints();
        
        f.fill = GridBagConstraints.NONE;
        f.anchor = GridBagConstraints.NORTH;
        f.gridy = 0;
        textLabel = new JLabel("Change " + keyword, SwingConstants.CENTER);
        textLabel.setLabelFor(textFields[0]);
        contentPanel.add(textLabel, f);
        
        f.fill = GridBagConstraints.HORIZONTAL;
        f.anchor = GridBagConstraints.NORTH;
        f.gridy = 1;
        contentPanel.add(textFields[index], f);
        
        contentPanel.setVisible(true);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.gridy = 1;
        tab.add(contentPanel, c);
        return tab;
    }
    
    /*
     * actionPerformed
     * param: ActionEvent
     * returns: N/A
     * function: applies the user-defined changes to the planes that the 
     *           current tab affects
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int currentTab = tabbedPane.getSelectedIndex();
        UAVColors change = (UAVColors) textFields[currentTab].getSelectedItem();
        
        SimUAVPlaneTypes type = null;
        if (currentTab == 1){ type = SimUAVPlaneTypes.TOY;}
        if (currentTab == 2){ type = SimUAVPlaneTypes.DELIVERY;}

        map.modifyPlaneColorbySubtype(type, change);
        setVisible(false);
    }
    
    /*
     * stateChanged
     * param: ChangeEvent
     * returns: N/A
     * function: resizes the tabs to fit the window
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        Component tab = tabbedPane.getSelectedComponent();
        tabbedPane.setPreferredSize(tab.getPreferredSize());
        pack();
    }
    
}
