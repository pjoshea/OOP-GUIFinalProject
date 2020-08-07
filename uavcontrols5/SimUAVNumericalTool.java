/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVNumericalTool
 * Extends: SimUAVTool
 * Purpose: A subclass of the SimUAVTool which provides methods designed 
 *          specifically for tools that only work with numbers
 */

package uavcontrols5;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SimUAVNumericalTool extends SimUAVTool implements ActionListener,
                                                         ChangeListener
{
    private JSpinner [] changeValues;
    private JLabel changeLabel;
    private int index = 0; //keeps track of which JSpinner is which
    
    /*
     * SimUAVNumericalTool
     * param: the tool's type in String form, the map
     * returns: SimUAVNumericalTool
     * function: constructor
     */
    SimUAVNumericalTool(String toolType, UAVMap map) {
        super(toolType, map);
        finalize.addActionListener(this);
    }

    /*
     * arrangeTabs
     * param: the plane's data member that is being changed,
     *        the units corresponding to that data member
     * returns: N/A
     * function: creates and arranges the tabs of the tool pane
     */
    protected void arrangeTabs(String keyword, String units) {
        tabbedPane = new JTabbedPane();
        
        //1 JSpinner for each plane type, and one for all of the planes
        changeValues = new JSpinner[SimUAVPlaneTypes.NUM_TYPES + 1];
        
        tabbedPane.addTab("Change " + keyword + "for all planes",
                          numericAllPlaneTab(keyword, units));
        index++;
        
        for (int i = 0; i < SimUAVPlaneTypes.NUM_TYPES; i++) {
            JPanel tab = numericTypeTab(keyword, units, SimUAVPlaneTypes.getName(i));
            tabbedPane.addTab(SimUAVPlaneTypes.getName(i), tab);
            index++;
        }
        
        tabbedPane.addChangeListener(this);
    }

    /*
     * numericTypeTab
     * param: the plane's data member that is being changed,
     *        the units corresponding to that data member, the plane's type
     * returns: a tool tab that will affect all of the planes of the given type
     * function: creates a tab which will change the data member that the tool 
     *           affects for all of the planes of the given type by the 
     *           user-defined quantity
     */
    protected JPanel numericTypeTab(String keyword, String units, String planeType) {
        JPanel tab = new JPanel();
        tab.setLayout(new GridBagLayout());
        tab.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 2, 10, 2), 
                BorderFactory.createTitledBorder("Change " + keyword + 
                                                 " for UAV Type: " +  planeType)
        ));
        
        GridBagConstraints c = new GridBagConstraints();
        JSpinner numberChoice = new JSpinner();
        
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        changeValues[index] = new JSpinner(new SpinnerNumberModel());
        tab.add(numberChoice, c);
        
        return tab;
    }

    /*
     * numericAllPlaneTab
     * param: the plane's data member that is being changed,
     *        the units corresponding to that data member
     * returns: a tool tab that will affect all of the planes
     * function: creates a tab which will change the data member that the tool 
     *           affects for all of the planes by the user-defined quantity
     */
    protected JPanel numericAllPlaneTab(String keyword, String units) {
        JPanel tab = new JPanel();
        tab.setPreferredSize(new Dimension(225, 225));
        tab.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        tab.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 2, 10, 2), 
                BorderFactory.createTitledBorder("Change " + keyword)
        ));
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.gridy = 1;
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints f = new GridBagConstraints();
        
        changeValues[index] = new JSpinner(new SpinnerNumberModel());
        changeLabel = new JLabel("Change " + keyword + " " + units,
                                 SwingConstants.CENTER);
        changeLabel.setLabelFor(changeValues[index]);
        
        f.fill = GridBagConstraints.NONE;
        f.anchor = GridBagConstraints.NORTH;
        f.gridy = 0;
        contentPanel.add(changeLabel, f);
        
        f.fill = GridBagConstraints.HORIZONTAL;
        f.anchor = GridBagConstraints.NORTH;
        f.gridy = 1;
        contentPanel.add(changeValues[index], f);
        
        contentPanel.setVisible(true);
        
        tab.add(contentPanel, c);
        return tab;
    }

    /*
     * actionPerformed
     * param: ActionEvent
     * returns: N/A
     * function: applies the changes of the current tab to all of the planes
     *           that the current tab affects
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int currentTab = tabbedPane.getSelectedIndex();
        int change = (int) changeValues[currentTab].getValue();
        
        SimUAVPlaneTypes type = null;
        if (currentTab == 1){ type = SimUAVPlaneTypes.TOY;}
        if (currentTab == 2){ type = SimUAVPlaneTypes.DELIVERY;}
        
        
        switch (e.getActionCommand()) {
            case "modifySpeed":
                map.modifyPlanesNumericallybySubtype(type, "Speed", change);
                break;
            case "modifyAltitude":
                map.modifyPlanesNumericallybySubtype(type, "Altitude", change);
                break;
            case "changeDirection":
                map.modifyPlanesNumericallybySubtype(type, "Direction", change);
                break;
            case "changeAngSpeed":
                map.modifyPlanesNumericallybySubtype(type, "Angular Speed",
                                                     change);
                break;
            default:
                System.out.println("Action Unsupported." 
                                   + e.getActionCommand());
                break;
        }
        setVisible(false);
    }
    
    /*
     * stateChanged
     * param: ChangeEvent
     * returns: N/A
     * function: resizes tabs to fit the tool's window
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        Component tab = tabbedPane.getSelectedComponent();
        tabbedPane.setPreferredSize(tab.getPreferredSize());
        pack();
    }

}
