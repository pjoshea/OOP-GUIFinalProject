/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: UAVModeSelector
 * Extends: JFrame
 * Purpose: The window in which the user selects the mode 
 *          that the program ought to run in
 */

package uavcontrols5;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class UAVModeSelector extends JFrame implements ActionListener {
    private JButton game, simulator;
    private final String gameString = "game";
    private final String simString = "simulation";
    private String mode = null;

    /*
     * UAVModeSelector
     * param: N/A
     * returns: UAVModeSelector
     * function: constructor
     */
    public UAVModeSelector() {
        setName("Mode Selection");
        setTitle("Mode Selection");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new Dimension(375, 120));
        setLocation(new Point(500, 370));
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(10, 10, 30, 10);
        JLabel msg = new JLabel("Which mode would you like to launch "
                                + "the program in?");
        add(msg, c);
        
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints b = new GridBagConstraints();
        
        game = new JButton("Game");
        game.addActionListener(this);
        buttonPanel.add(game, b);
        
        b.gridx = 1;
        simulator = new JButton("Simulator");
        simulator.addActionListener(this);
        buttonPanel.add(simulator, b);
        
        c.gridy = 1;
        c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.SOUTHEAST;
        add(buttonPanel, c);
        
        setVisible(true);
    }

    /*
     * actionPerformed
     * param: ActionEvent
     * returns: N/A
     * function: initializes a new program session based on the user's selection
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(game)) {
            new Main(gameString);
        }
        if (e.getSource().equals(simulator)) {
            new Main(simString);
        }
        dispose();
    }
}
