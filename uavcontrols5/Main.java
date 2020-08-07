/*
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls
 * Program Purpose: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles
 * File Name: Main.java
 * File Purpose: Create the "map" and initialize the widgets and buttons and 
 *     then hand control to the event handlers.
 */

package uavcontrols5;

import uavcontrols5.UAVModes;
import uavcontrols5.UAVModeSelector;
import uavcontrols5.UAVMap;
import uavcontrols5.SimUAVMenu;
import uavcontrols5.SimUAVToolBar;

import javax.swing.JFrame;
import java.awt.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


public class Main extends JFrame {
    private UAVMap map;
    
    //game-only objects
    private Window fullScreen;
    private String gameName = "Evasion";
    
    //simulation-only objects
    private SimUAVToolBar toolBar;
    private SimUAVMenu smenu;
    
    /*
     * main
     * param: array of Strings containing the arguments
     * returns: N/A
     * function: creates a mode selector window
     */
    public static void main(String[] args) {
        new UAVModeSelector();
    }
    
    /*
     * Main
     * param: the mode, represented as a String
     * returns: Main
     * function: creates the map and the other program objects
     */
    Main(String mode) {
        if (mode.equals("game")) {
            createGameMode();
        } else {
            createSimulatorMode();
        }
    }

    /*
     * createGameMode
     * param: N/A
     * returns: N/A
     * function: initializes the map and adds the canvas to the frame, 
     *           then starts the timer
     * notes: difficulties with fullscreen mode: key bindings not working right
     *        Unsure if due to operating system or faulty coding, but the window
     *        that becomes fullscreen doesn't seem to be part of the same call 
     *        hierarchy as the Canvas because key bindings are unresponsive
     */
    private void createGameMode() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle fullScreenBounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
        boolean fs = ge.getDefaultScreenDevice().isFullScreenSupported();
        
        setTitle(gameName);
        setName(gameName);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        map = new UAVMap(this, UAVModes.GAME, fs);
        
        if (fs) {
            fullScreen = new Window(this);
            fullScreen.add(map.getCanvas());
            ge.getDefaultScreenDevice().setFullScreenWindow(fullScreen);
        } else {
            this.setSize((int)(.9 * fullScreenBounds.width), (int)(.9 * fullScreenBounds.height));
            this.setLocation(fullScreenBounds.x + (int) (.05 * fullScreenBounds.width), 
                             fullScreenBounds.y + (int) (.05 * fullScreenBounds.height));
            Container content = getContentPane();
            content.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1;
            c.weighty = 1;
            content.add(map.getCanvas(), c);
            setVisible(true);
        }
        map.initializeGame();
        map.startTimer(); 
    }

    /*
     * createSimulatorMode
     * param: N/A
     * returns: N/A
     * function: initializes the map, the toolbar, and the menu, creates
     *           connections between the three, then adds each to the frame
     *           and finally starts the timer
     */
    private void createSimulatorMode() {
        setSize(1000, 750);
        setLocation(new Point(200, 100));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container content = getContentPane();
        content.setLayout(new GridBagLayout());
        
        GridBagConstraints c = new GridBagConstraints();
        
        map = new UAVMap(this, UAVModes.SIMULATION, false);
        smenu = new SimUAVMenu();
        toolBar = new SimUAVToolBar();
        map.addConnections(toolBar, smenu);
        smenu.addConnections(toolBar, map);
        toolBar.addConnections(smenu, map);
        
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;
        content.add(smenu, c);
        
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        content.add(toolBar, c);
        
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        content.add(map.getCanvas(), c);
        
        setVisible(true);
        map.initializeSimulation();
        map.startTimer();
    }
}