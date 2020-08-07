/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVMenu
 * Extends: JMenuBar
 * Purpose: Creates the menu bar and menu items. Creates the tools.
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SimUAVMenu extends JMenuBar implements ActionListener, ChangeListener
{
    private JMenu file, view, action, tool;
    private JMenuItem addUAV, grid, fill, color, scale, animate, getInfo, 
                      modifySpeed, modifyAltitude, modifyID, 
                      modifyDirection, modifyAngularSpeed, modifyColor;
    
    private JSpinner zoom;
    private JLabel zoomLabel;
    private JPanel zoomPanel;
    private int zoomlvl = 100;
    private int zoomMin = 25;
    private int zoomMax = 400;
    private SpinnerNumberModel zoomSpinnerModel = 
                          new SpinnerNumberModel(zoomlvl, zoomMin, zoomMax, 10);
    
    private UAVMap map;
    private SimUAVToolBar toolBar;
    private boolean moveToolsEnabled = true;

    /*
     * SimUAVMenu
     * param: N/A
     * returns: SimUAVMenu
     * function: constructor
     */
    public SimUAVMenu() {
        createFileSubMenu();
        add(file);
        createViewSubMenu();
        add(view);
        createActionSubMenu();
        add(action);
        createToolSubMenu();
        add(tool);
    }
    
    /*
     * addConnections
     * param: toolbar, map
     * returns: N/A
     * function: adds connections between the toolbar, map and menu.
     */
    public void addConnections(SimUAVToolBar tBar, UAVMap uavMap) {
        toolBar = tBar;
        map = uavMap;
    }
    
    /*
     * createFileSubMenu
     * param: N/A
     * returns: N/A
     * function: creates the submenu under the File heading, holding the 
     *           Add UAV and Plane Information items
     */
    private void createFileSubMenu() {
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_A);
        file.getAccessibleContext().setAccessibleDescription("Main Menu");
        
        addUAV = new JMenuItem("Add UAV");
        getInfo = new JMenuItem("Plane Information");
        file.add(addUAV);
        file.add(getInfo);
        addUAV.addActionListener(this);
        getInfo.addActionListener(this);
        addUAV.setActionCommand("addUAV");
        getInfo.setActionCommand("getInfo");
    }
    
    /*
     * createViewSubMenu
     * param: N/A
     * returns: N/A
     * function: creates the submenu under the View heading, holding the 
     *           zoom, grid, color, fill and scale items
     */
    private void createViewSubMenu() {
        view = new JMenu("View");
        view.setMnemonic(KeyEvent.VK_B);
        file.getAccessibleContext().setAccessibleDescription("View Menu");
        
        //zoom
        zoom = new JSpinner(zoomSpinnerModel);
        zoomLabel = new JLabel("Zoom");
        zoomPanel = new JPanel();
        zoomPanel.setBackground(Color.white);
        zoomPanel.add(zoomLabel);
        zoomPanel.add(zoom);
        view.add(zoomPanel);
        zoom.addChangeListener(this);
        
        // grid, fill, color, scale
        grid = new JMenuItem("Grid");
        color = new JMenuItem("Color");
        fill = new JMenuItem("Fill");
        scale = new JMenuItem("Scale");
        
        view.add(grid);
        view.add(color);
        view.add(fill);
        view.add(scale);
        
        grid.addActionListener(this);
        color.addActionListener(this);
        fill.addActionListener(this);
        scale.addActionListener(this);
        
        grid.setActionCommand("grid");
        color.setActionCommand("color");
        fill.setActionCommand("fill");
        scale.setActionCommand("scale");
        
    }
    
    /*
     * createActionSubMenu
     * param: N/A
     * returns: N/A
     * function: creates the submenu under the Actions heading, holding the 
     *           animate item
     */
    private void createActionSubMenu() {
        action = new JMenu("Actions");
        action.setMnemonic(KeyEvent.VK_B);
        file.getAccessibleContext().setAccessibleDescription("Action Menu");
        
        animate = new JMenuItem("Animate");
        action.add(animate);
        animate.addActionListener(this);
        animate.setActionCommand("animate");
    }
    
    /*
     * createToolSubMenu
     * param: N/A
     * returns: N/A
     * function: creates the submenu under the Tools heading, holding the 
     *           items linked to the tools which operate on plane data
     */
    private void createToolSubMenu() {
        tool = new JMenu("Tools");
        tool.setMnemonic(KeyEvent.VK_C);
        file.getAccessibleContext().setAccessibleDescription("Tool Menu");
        
        modifyID = new JMenuItem("Rename Planes");
        modifySpeed = new JMenuItem("Modify Speed");
        modifyAltitude = new JMenuItem("Modify Altitude");
        modifyDirection = new JMenuItem("Modify Direction");
        modifyAngularSpeed = new JMenuItem("Modify Angular Speed");
        modifyColor = new JMenuItem("Modify Color");
        tool.add(modifyID);
        tool.add(modifySpeed);
        tool.add(modifyAltitude);
        tool.add(modifyDirection);
        tool.add(modifyAngularSpeed);
        tool.add(modifyColor);
        modifyID.addActionListener(this);
        modifySpeed.addActionListener(this);
        modifyAltitude.addActionListener(this);
        modifyDirection.addActionListener(this);
        modifyAngularSpeed.addActionListener(this);
        modifyColor.addActionListener(this);
        modifyID.setActionCommand("renamePlane");
        modifySpeed.setActionCommand("modifySpeed");
        modifyAltitude.setActionCommand("modifyAltitude");
        modifyDirection.setActionCommand("modifyDirection");
        modifyAngularSpeed.setActionCommand("modifyAngSpeed");
        modifyColor.setActionCommand("modifyColor");
    }
    
    /*
     * toggleAnimation
     * param: N/A
     * returns: N/A
     * function: when the animation is paused or unpaused, various tools become 
     *           available or unavailable accordingly
     */
    public void toggleAnimation() {
        moveToolsEnabled = !moveToolsEnabled;
        modifySpeed.setEnabled(moveToolsEnabled);
        modifyAltitude.setEnabled(moveToolsEnabled);
        modifyDirection.setEnabled(moveToolsEnabled);
        modifyAngularSpeed.setEnabled(moveToolsEnabled);
    }

    /*
     * actionPerformed
     * param: actionEvent
     * returns: N/A
     * function: tells the program what to do when one of the menu items
     *           is selected
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "addUAV":
                launchPlaneCreationWindow();
                break;
            case "grid":
                map.toggleGrid();
                break;
            case "color":
                map.toggleColor();
                break;
            case "fill":
                map.toggleFill();
                break;
            case "scale":
                map.toggleScale();
                break;
            case "animate":
                map.simToggleAnimation();
                toggleAnimation();
                break;
            case "getInfo":
                launchInfoPane();
                break;
            case "modifySpeed":
                launchModSpeedTool();
                break;
            case "modifyAltitude":
                launchModAltTool();
                break;
            case "renamePlane":
                launchModIDTool();
                break;
            case "modifyDirection":
                launchModDirTool();
                break;
            case "modifyAngSpeed":
                launchModAngSpeedTool();
                break;
            case "modifyColor":
                launchModColorTool();
                break;
            default:
                System.out.println("Action Unsupported");
        }
    }
    
    /*
     * stateChanged
     * param: ChangeEvent
     * returns: N/A
     * function: tells the program what to do when the zoom level is changed
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        zoomlvl = (int) zoom.getValue();
        map.zoom(zoomlvl);
    }
    
/*******************************************************************************
 ****************** Methods which create and launch tools **********************
 ******************************************************************************/
    
    private void launchPlaneCreationWindow() {
        new SimUAVPlaneCreatorWindow(map);
    }

    private void launchInfoPane() {
        new SimUAVInfoPane(map);
    }

    private void launchModSpeedTool() {
        new SimUAVModSpeedTool(map);
    }

    private void launchModAltTool() {
        new SimUAVModAltTool(map);
    }

    private void launchModIDTool() {
        new SimUAVModIDTool(map);
    }

    private void launchModDirTool() {
        new SimUAVModDirTool(map);
    }

    private void launchModAngSpeedTool() {
        new SimUAVModAngSpeedTool(map);
    }
    
    private void launchModColorTool() {
        new SimUAVModColorTool(map);
    }
}
