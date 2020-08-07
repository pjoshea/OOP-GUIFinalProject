/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVInfoPane
 * Extends: JFrame
 * Purpose: Creates a frame which holds information about all of the planes 
 *          on the map.
 */

package uavcontrols5;

import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.*;

class SimUAVInfoPane extends JFrame {
    private UAVMap map;
    private JTabbedPane tabbedPane;
    private JPanel tab, idPanel, dimLocPanel, movePanel;
    private final Color bgColor = UIManager.getColor( "Panel.background" );
    
    /*
     * SimUAVInfoPane
     * param: map
     * returns: SimUAVInfoPane
     * function: constructor
     */
    public SimUAVInfoPane(UAVMap map) {
        setName("Plane Info");
        setTitle("Plane Info");
        this.map = map;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(1000, 200);
        setSize(265, 400);
        
        tabbedPane = new JTabbedPane();
        
        for (int i = 0; i < map.getNumberPlanes(); i++) {
            int key = translateNumbertoKeyEvent(i);
            JPanel tab = createPlaneInfoTab((SimUAVPlane) map.getPlaneClone(i));
            tabbedPane.addTab(map.getSinglePlaneID(i), tab);
            tabbedPane.setMnemonicAt(i, key);
        }
         
        //Add the tabbed pane to this panel.
        add(tabbedPane);
        tabbedPane.setVisible(true);
        setVisible(true);
    }

    /*
     * createPlaneInfoTab
     * param: plane
     * returns: JPanel
     * function: creates the tab which holds the info on the plane that was 
     *           passed as a parameter
     */
    private JPanel createPlaneInfoTab(SimUAVPlane p) {
        tab = new JPanel();
        tab.setLayout(new GridLayout(3,1));
        createIdentificationPanel(p);
        createDimensionLocationPanel(p);
        createMovementPanel(p);
        
        tab.add(idPanel);
        tab.add(dimLocPanel);
        tab.add(movePanel);
        return tab;
    }

    /*
     * createIdentificationPanel
     * param: plane
     * returns: N/A
     * function: creates the JPanel which holds identification information 
     *           about the plane, i.e. the ID, type, and color.
     */
    private void createIdentificationPanel(SimUAVPlane p) {
        idPanel = new JPanel();
        idPanel.setLayout(new GridLayout(1,1));
        idPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 2, 10, 2), 
                BorderFactory.createTitledBorder("Identification Info")
        ));
        
        JEditorPane text = null;
        try {
            text = new JEditorPane("text/plain",
                                   "ID: " + p.id + 
                                   "\nType: " + p.stype.getName() + 
                                   "\nColor: " + p.getColor());
            text.setBackground(bgColor);
            text.setEditable(false);
        } catch (Exception IOException) {}
        
        idPanel.add(text);
    }

    /*
     * createDimensionLocationPanel
     * param: plane
     * returns: N/A
     * function: creates the JPanel which holds positional and size information 
     *           about the plane (dimensions and location).
     */
    private void createDimensionLocationPanel(SimUAVPlane p) {
        dimLocPanel = new JPanel();
        dimLocPanel.setLayout(new GridLayout(1,1));
        dimLocPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 2, 10, 2), 
                BorderFactory.createTitledBorder("Dimensions & Location Info")
        ));
        
        JEditorPane text = null;
        Dimension d = p.getDimension();
        Point2D l = p.getPosition();
        try {
            text = new JEditorPane("text/plain",
                                   "Height: " + d.height + " meters" + 
                                   "\nWidth: " + d.width + " meters" +
                                   "\nX coordinate: " + l.getX() + 
                                   "\nY coordinate: " + l.getY());
            text.setBackground(bgColor);
            text.setEditable(false);
        } catch (Exception IOException) { }
        
        dimLocPanel.add(text);
    }

    /*
     * createMovementPanel
     * param: plane
     * returns: N/A
     * function: creates the JPanel which holds movement information about the
     *           plane, i.e. direction, altitude, speed, and angular speed
     */
    private void createMovementPanel(SimUAVPlane p) {
        movePanel = new JPanel();
        movePanel.setLayout(new GridLayout(1,1));
        movePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 2, 10, 2), 
                BorderFactory.createTitledBorder("Movement Info")
        ));
        
        JEditorPane text = null;
        try {
            text = new JEditorPane("text/plain",
                                   "Speed: " + p.getSpeed() + 
                                   " kilometers per hour" +
                                   "\nAltitude: " + p.getAltitude() + 
                                   " kilometers" +
                                   "\nDirection: " + p.getDirection() + 
                                   " degrees" + "\nAngular Speed: " + 
                                   p.getAngularSpeed() + " degrees per second");
            text.setBackground(bgColor);
            text.setEditable(false);
        } catch (Exception IOException) {}
        
        movePanel.add(text);
    }
    
    /*
     * translateNumbertoKeyEvent
     * param: Plane Number
     * returns: int
     * function: attempts to give the plane number a corresponding keychar
     *           in hopes of being able to cycle through plane tabs more quickly 
     *           if the simulation holds a large number
     */
    private int translateNumbertoKeyEvent(int i) {
        int keyEventNum = 40;
        keyEventNum+=i;
        if (keyEventNum > 57) { 
            keyEventNum = keyEventNum - 57;
            keyEventNum += 65;
        }
        if (keyEventNum > 90) {keyEventNum = 28;}
        return keyEventNum;
    }
}
