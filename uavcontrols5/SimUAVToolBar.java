/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVToolBar
 * Extends: JPanel
 * Purpose: Creates and contains all the buttons, handles all their actionEvents
 */

package uavcontrols5;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JSpinner;

public class SimUAVToolBar extends JPanel implements ActionListener {
    SimUAVMenu menu;
    UAVMap map;
    private JPanel view, action, commands;
    private JPanel numberPanel;
    private JSpinner numberBox;
    private SimUAVButton gridButton, colorButton, fillButton, scaleButton,
                      animateButton, clockwise, widdershins, incAltitude, 
                      decAltitude, incSpeed, decSpeed, incAngSpeed, decAngSpeed,
                      repairButton;
    
    private int MAX_TURN_ANGLE = 30;
    private int MAX_SPEED_CHANGE = 10;
    private int MAX_ALT_CHANGE = 10;
    private int MAX_ANG_SPEED_CHANGE = 36;
    
    /*
     * SimUAVToolBar
     * param: N/A
     * returns: SimUAVToolBar
     * function: constructor
     */
    public SimUAVToolBar() {
        setLayout(new GridBagLayout());
        createViewButtons();
        createActionButtons();
        createPlaneCommandButtons();
        leftAlignButtons();
    }
    
    /*
     * addConnections
     * param: menu, map
     * returns: N/A
     * function: gives access to the menu and the map
     */
    public void addConnections(SimUAVMenu uavMenu, UAVMap uavMap) {
        menu = uavMenu;
        map = uavMap;
    }
    
    /*
     * createViewButtons
     * param: N/A
     * returns: N/A
     * function: creates the JPanel holding the buttons which affect the view
     */
    private void createViewButtons() {
        view = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.ipadx = 2;
        gridButton = new SimUAVGridButton();
        gridButton.addActionListener(this);
        view.add(gridButton, c);
        
        c.gridx = 2;
        fillButton = new SimUAVFillButton();
        fillButton.addActionListener(this);
        view.add(fillButton, c);
        
        c.gridx = 3;
        colorButton = new SimUAVColorButton();
        colorButton.addActionListener(this);
        view.add(colorButton, c);
        
        c.gridx = 0;
        c.ipadx = 2;
        c.insets = new Insets(0,0,0,10);
        c.fill = GridBagConstraints.NONE;
        add(view, c);
    }
    
    /*
     * createActionButtons
     * param: N/A
     * returns: N/A
     * function: creates the JPanel holding the animate button
     */
    private void createActionButtons() {
        action = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        animateButton = new SimUAVAnimateButton();
        animateButton.addActionListener(this);
        action.add(animateButton);
        
        c.gridx = 1;
        c.ipadx = 2;
        c.insets = new Insets(0,0,0,5);
        c.fill = GridBagConstraints.NONE;
        add(action, c);
    }
    
    /*
     * createPlaneCommandButtons
     * param: N/A
     * returns: N/A
     * function: creates the JPanel holding the buttons which affect
     *           the currently selected plane
     */
    private void createPlaneCommandButtons() {
        commands = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 0;
        clockwise = new SimUAVClockwiseTurnButton();
        clockwise.addActionListener(this);
        commands.add(clockwise, c);
        
        c.gridx = 1;
        widdershins = new SimUAVWiddershinsTurnButton();
        widdershins.addActionListener(this);
        commands.add(widdershins, c);
        
        c.gridx = 2;
        incAltitude = new SimUAVIncreaseAltitudeButton();
        incAltitude.addActionListener(this);
        commands.add(incAltitude, c);
        
        c.gridx = 3;
        decAltitude = new SimUAVDecreaseAltitudeButton();
        decAltitude.addActionListener(this);
        commands.add(decAltitude, c);
        
        c.gridx = 4;
        incSpeed = new SimUAVIncreaseSpeedButton();
        incSpeed.addActionListener(this);
        commands.add(incSpeed, c);
        
        c.gridx = 5;
        decSpeed = new SimUAVDecreaseSpeedButton();
        decSpeed.addActionListener(this);
        commands.add(decSpeed, c);
        
        c.gridx = 6;
        incAngSpeed = new SimUAVIncreaseAngularSpeedButton();
        incAngSpeed.addActionListener(this);
        commands.add(incAngSpeed, c);
        
        c.gridx = 7;
        decAngSpeed = new SimUAVDecreaseAngularSpeedButton();
        decAngSpeed.addActionListener(this);
        commands.add(decAngSpeed, c);
        
        c.gridx = 8;
        repairButton = new SimUAVRepairPlaneButton();
        repairButton.addActionListener(this);
        commands.add(repairButton, c);
        
        c.gridx = 2;
        c.ipadx = 2;
        c.insets = new Insets(0,0,0,5);
        c.fill = GridBagConstraints.NONE;
        add(commands, c);
    }
    
    private void leftAlignButtons() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_END;
        c.weightx = 100.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        add(Box.createGlue(), c);
    }

    public void toggleAnimationButton() {
        animateButton.toggle();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Grid" :
                map.toggleGrid();
                break;
            case "Color":
                map.toggleColor();
                break;
            case "Fill" :
                map.toggleFill();
                break;
            case "Scale":
                map.toggleScale();
                break;
            case "Animate":
                map.simToggleAnimation();
                menu.toggleAnimation();
                break;
            case "Clockwise":
                map.modifySelectedPlane("Turn", -MAX_TURN_ANGLE);
                break;
            case "Widdershins":
                map.modifySelectedPlane("Turn", MAX_TURN_ANGLE);
                break;
            case "Increase Altitude":
                map.modifySelectedPlane("Height", MAX_ALT_CHANGE);
                break;
            case "Decrease Altitude":
                map.modifySelectedPlane("Height", -MAX_ALT_CHANGE);
                break;
            case "Increase Speed":
                map.modifySelectedPlane("Speed", MAX_SPEED_CHANGE);
                break;
            case "Decrease Speed":
                map.modifySelectedPlane("Speed", -MAX_SPEED_CHANGE);
                break;
            case "Increase Angular Speed":
                map.modifySelectedPlane("AngSpeed", -MAX_ANG_SPEED_CHANGE);
                break;
            case "Decrease Angular Speed":
                map.modifySelectedPlane("AngSpeed", MAX_ANG_SPEED_CHANGE);
                break;
            case "Repair Plane":
                map.modifySelectedPlane("Repair", 0);
                break;
        }
    }
}
