/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVPlaneCreatorWindow
 * Extends: JFrame
 * Purpose: Creates a frame which allows the user to create their own plane, 
 *          specifying type, name, color, dimensions, starting location, and 
 *          various movement information.
 */

package uavcontrols5;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import javax.swing.*;
import javax.swing.event.*;
import static javax.swing.BoxLayout.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;

public class SimUAVPlaneCreatorWindow extends JFrame implements ActionListener, 
                                                             ChangeListener,
                                                             DocumentListener
{
    private UAVMap map;
    
    //provide a frame for the incomplete error message 
    private JFrame incomplete;  
    
    //an array which remembers which of the four non-optional fields have been filled
    private boolean [] fieldsFilled = {false, false, false, false};

    private JLabel idLabel;
    private JTextField id;
    private String idText;
    private boolean invalidID = false;
    
    private JLabel widthLabel, heightLabel, xLabel, yLabel, altitudeLabel,
           directionLabel, speedLabel, angLabel;
    private JSpinner width, height, x, y, altitude, direction, ang, speed;
    private int widthNum, heightNum;
    private int xNum = 0;
    private int yNum = 0;
    private int altNum = 0; 
    private int speedNum = 0;
    //the plane's constructor requires a double for direction and angular speed
    private double dirNum = 0.0;
    private double angNum = 0.0;
    private SpinnerNumberModel[] spinnerModels = new SpinnerNumberModel[8];
    
    private JLabel colorLabel, typeLabel;
    private JComboBox colorBox, typeBox;
    private UAVColors color = UAVColors.NONE;
    private String typeText;
    public static String [] allowedTypes = { "", "Toy", "Delivery"};   
    
    private JPanel contentPanel;
    private JPanel identificationPanel, sizeLocationPanel, movementPanel;
    private SimUAVFinalizeCreatedPlaneButton finalize;
    
    private Border minimalLabelBorder = 
        BorderFactory.createCompoundBorder(
                                    BorderFactory.createEmptyBorder(0,0,0,0),
                                    BorderFactory.createEmptyBorder(0,0,0,0));

    
    /*
     * SimUAVPlaneCreatorWindow
     * param: map
     * returns: SimUAVPlaneCreatorWindow
     * function: constructor
     */
    public SimUAVPlaneCreatorWindow(UAVMap map) {
        setName("Plane Creation");
        setTitle("Plane Creation");
        setLocation(new Point(550, 300));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.map = map;
              
        Container content = getContentPane();
        content.setLayout(new BoxLayout(content, Y_AXIS));
        
        for (int i = 0; i < spinnerModels.length; i++) {
            spinnerModels[i] = new SpinnerNumberModel();
            spinnerModels[i].setMinimum(0);
        }
        
        contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 1, 10, 0));
        
        generateContentPanel();
        finalize = new SimUAVFinalizeCreatedPlaneButton(map);
        finalize.addActionListener(this);

        content.add(contentPanel);
        content.add(finalize);
        
        this.pack();
        this.setVisible(true);
    }
    
    /*
     * checkAllFields
     * param: N/A
     * returns: whether all of the necessary fields have been filled by the user
     * function: checks to see if the user has entered values for all the data 
     *           necessary to create a plane
     */
    private boolean checkAllFields() {
        boolean validPlane = true;
        for (int i = 0; i < fieldsFilled.length; i++) {
            if (!fieldsFilled[i]) { validPlane = false; }
        }
        return validPlane;
    }

    /*
     * sendIncompleteErrorMessage
     * param: N/A
     * returns: N/A
     * function: tells the user either that the ID they entered is already in 
     *           use, or that not all of the requisite fields have been filled
     */
    private void sendIncompleteErrorMessage() {
        String dialog;
        if (invalidID) {
            dialog = "The id you have entered is already in use,"
                    + " please choose another";
        } else {
            dialog = "Please fill in all of the fields before you submit.\n"
                    + "Remember, neither Width nor Height can be zero.\n"
                    + "Color, Type and ID are necessary for plane creation.";
        }
        JOptionPane.showMessageDialog( incomplete, dialog, "Plane Incomplete", 
                                       JOptionPane.OK_OPTION);   
    }
    
    /*
     * actionPerformed
     * param: ActionEvent
     * returns: N/A
     * function: Depending on the source of the ActionEvent, either
     *           a. checks to see if the plane is valide, and if it is,
     *              creates the plane & adds it to the map's list
     *           b. changes the value associated with the combo boxes.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //only activate if the action command comes from the finalize button
        if (finalize.equals(e.getSource())) {
            if (checkAllFields()) {
                switch (typeText) {
                    /*UAVCanvas c, UAVMap m, String i, UAVColors col, 
                          Point2D p, Dimension d, int alt, double dir, 
                          double s, double angSpeed*/
                    case "Toy":
                        map.addPlane(new SimUAVToyPlane(map.getCanvas(), map, 
                                idText, color, new Point2D.Double(xNum, yNum), 
                                new Dimension(widthNum, heightNum), altNum, 
                                dirNum, speedNum, angNum));
                        break;
                    case "Delivery":
                        map.addPlane(new SimUAVDeliveryPlane(
                                            map.getCanvas(), map, idText, color, 
                                            new Point2D.Double(xNum, yNum), 
                                            new Dimension(widthNum, heightNum), 
                                            altNum, dirNum, speedNum, angNum));
                }
                this.setVisible(false);
            } else {
                sendIncompleteErrorMessage();
            }
        }
        if (colorBox.equals(e.getSource())) {
            //the first index in the combo box is blank, 
            //so we can catch invalid colors
            if (colorBox.getSelectedIndex() != 0) {
                color = (UAVColors) colorBox.getSelectedItem();
            }
        }
        if (typeBox.equals(e.getSource())) {
            //the first index in the combo box is blank, 
            //so we can catch invalid types
            if (typeBox.getSelectedIndex() != 0) {
                typeText = (String) typeBox.getSelectedItem();
                fieldsFilled[0] = true;
            }
        }
    }
    
    /*
     * stateChanged
     * param: ChangeEvent
     * returns: N/A
     * function: changes the value associated with the JSpinner source and 
     *           records that the value has been changed.
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(width)) {
            widthNum = (int) width.getValue();
            fieldsFilled[2] = true;
        }
        if (e.getSource().equals(height)) {
            heightNum = (int) height.getValue();
            fieldsFilled[3] = true;
        }
        if (e.getSource().equals(x)) {
            xNum = (int) x.getValue();
        }
        if (e.getSource().equals(y)) {
            yNum = (int) y.getValue();
        }
        if (e.getSource().equals(altitude)) {
            altNum = (int) altitude.getValue();
        }
        if (e.getSource().equals(speed)) {
            speedNum = (int) speed.getValue();
        }
        if (e.getSource().equals(direction)) {
            dirNum = (int) direction.getValue();
        }
        if (e.getSource().equals(ang)) {
            angNum = (int) ang.getValue();
        }
    }

    /******************************************
     *****   Document Listener Interface  *****
     ***** implementations which redirect *****
     *****      to the same function      *****
     *****************************************/
    @Override
    public void insertUpdate(DocumentEvent e) {
        idModified(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        idModified(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        idModified(e);
    }
    
    /* 
     * idModified
     * param: DocumentEvent
     * returns: N/A
     * function: changes the value associated with the ID's textfield, checks 
     *           the new ID for validity and records that the value 
     *           has been changed.
     * notes: this function is called for each change made to the id field. 
     *        If the id field is left blank as a result, or if the id has 
     *        already been used, then the function will mark the id as not yet
     *        filled, and in the latter case, change the error message shown 
     *        when finalize is called.
     */
    private void idModified(DocumentEvent e) {
        if (!(id.getText().equals(""))) {
            if (map.idAvailable(id.getText())) {
                idText = id.getText();
                fieldsFilled[1] = true;
                invalidID = false;
            } else {
                invalidID = true;
                fieldsFilled[1] = false;
            }
        } else {
            fieldsFilled[1] = false;
        }
    }

    /* 
     * generateContentPanel
     * param: N/A
     * returns: N/A
     * function: creates the member panels and arranges them in the frame
     */
    private void generateContentPanel() {
        createIdentificationPanel();
        createSizeLocationPanel();
        createMovementPanel();
        
        contentPanel.add(identificationPanel);
        contentPanel.add(sizeLocationPanel);
        contentPanel.add(movementPanel);
    }

    /* 
     * createIdentificationPanel
     * param: N/A
     * returns: N/A
     * function: creates the panel holding the ID, color, and type fields
     */
    private void createIdentificationPanel() {
        Dimension boxSize = new Dimension(40, 10);
        identificationPanel = new JPanel();
        identificationPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        identificationPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                BorderFactory.createTitledBorder("Identification Data")));        
        
        
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .5;
        c.weighty = .5;
        c.anchor = GridBagConstraints.NORTH;
        typeLabel = new JLabel("Type: ", SwingConstants.RIGHT);
        typeLabel.setLabelFor(typeBox);
        typeLabel.setBorder(minimalLabelBorder);
        identificationPanel.add(typeLabel, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.weightx = .5;
        c.weighty = .5;
        typeBox = new JComboBox(allowedTypes);
        typeBox.addActionListener(this);
        typeBox.setEditable(false);
        typeBox.setPreferredSize(boxSize);
        identificationPanel.add(typeBox, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = .5;
        c.weighty = .5;
        idLabel = new JLabel("ID", SwingConstants.RIGHT);
        idLabel.setLabelFor(id);
        idLabel.setBorder(minimalLabelBorder);
        identificationPanel.add(idLabel, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = .5;
        c.weighty = .5;
        id = new JTextField();
        id.getDocument().addDocumentListener(this);
        id.setPreferredSize(boxSize);
        identificationPanel.add(id, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = .5;
        c.weighty = .5;
        colorLabel = new JLabel("Color", SwingConstants.RIGHT);
        colorLabel.setLabelFor(colorBox);
        colorLabel.setBorder(minimalLabelBorder);
        identificationPanel.add(colorLabel, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 1;
        c.gridy = 2;
        c.weightx = .5;
        c.weighty = .5;
        colorBox = new JComboBox(UAVColors.getComboBoxModel());
        colorBox.addActionListener(this);
        colorBox.setEditable(false);
        colorBox.setPreferredSize(boxSize);
        identificationPanel.add(colorBox, c);
    }

    /* 
     * createSizeLocationPanel
     * param: N/A
     * returns: N/A
     * function: creates the panel holding the height, width, 
     *           x-position, and y-position fields
     */
    private void createSizeLocationPanel() {
        Dimension boxSize = new Dimension(25, 25);
        sizeLocationPanel = new JPanel();
        sizeLocationPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        sizeLocationPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                BorderFactory.createTitledBorder("Coordinates and Dimensions"))); 
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = .5;
        c.weighty = .5;
        widthLabel = new JLabel("Width (meters)", SwingConstants.RIGHT);
        widthLabel.setLabelFor(width);
        widthLabel.setBorder(minimalLabelBorder);
        sizeLocationPanel.add(widthLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.weightx = .5;
        c.weighty = .5;
        width = new JSpinner(spinnerModels[0]);
        width.addChangeListener(this);
        width.setPreferredSize(boxSize);
        sizeLocationPanel.add(width, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = .5;
        c.weighty = .5;
        heightLabel = new JLabel("Height (meters)", SwingConstants.RIGHT);
        heightLabel.setLabelFor(height);
        heightLabel.setBorder(minimalLabelBorder);
        sizeLocationPanel.add(heightLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = .5;
        c.weighty = .5;
        height = new JSpinner(spinnerModels[1]);
        height.setPreferredSize(boxSize);
        height.addChangeListener(this);
        sizeLocationPanel.add(height, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = .5;
        c.weighty = .5;
        xLabel = new JLabel("X", SwingConstants.RIGHT);
        xLabel.setLabelFor(x);
        xLabel.setBorder(minimalLabelBorder);
        sizeLocationPanel.add(xLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 3;
        c.gridy = 0;
        c.weightx = .5;
        c.weighty = .5;
        x = new JSpinner(spinnerModels[2]);
        x.setPreferredSize(boxSize);
        x.addChangeListener(this);
        sizeLocationPanel.add(x, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = .5;
        c.weighty = .5;
        yLabel = new JLabel("Y", SwingConstants.RIGHT);
        yLabel.setLabelFor(y);
        yLabel.setBorder(minimalLabelBorder);
        sizeLocationPanel.add(yLabel, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 3;
        c.gridy = 1;
        c.weightx = .5;
        c.weighty = .5;
        y = new JSpinner(spinnerModels[3]);
        y.setPreferredSize(boxSize);
        y.addChangeListener(this);
        sizeLocationPanel.add(y, c);
    }

    /* 
     * createMovementPanel
     * param: N/A
     * returns: N/A
     * function: creates the panel holding the direction, speed, angular speed, 
     *           and altitude fields
     */
    private void createMovementPanel() {
        Dimension boxSize = new Dimension(10, 10);
        movementPanel = new JPanel();
        movementPanel.setLayout(new GridLayout(4,2));
        movementPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 10, 5, 10),
                BorderFactory.createTitledBorder("Movement Data")));
        
        altitude = new JSpinner(spinnerModels[4]);
        altitude.setSize(boxSize);
        altitude.addChangeListener(this);
        altitudeLabel = new JLabel("Altitude (kilometers)", SwingConstants.RIGHT);
        altitudeLabel.setLabelFor(altitude);
        altitudeLabel.setBorder(minimalLabelBorder);
        speed = new JSpinner(spinnerModels[5]);
        speed.setSize(boxSize);
        speed.addChangeListener(this);
        speedLabel = new JLabel("Speed (kph)", SwingConstants.RIGHT);
        speedLabel.setLabelFor(speed);
        speedLabel.setBorder(minimalLabelBorder);
        direction = new JSpinner(spinnerModels[6]);
        direction.setSize(boxSize);
        direction.addChangeListener(this);
        directionLabel = new JLabel("Direction (degrees)",SwingConstants.RIGHT);
        directionLabel.setLabelFor(direction);
        directionLabel.setBorder(minimalLabelBorder);
        ang = new JSpinner(spinnerModels[7]);
        ang.setSize(boxSize);
        ang.addChangeListener(this);
        angLabel = new JLabel("Angular Speed (degrees/sec)", SwingConstants.RIGHT);
        angLabel.setLabelFor(ang);
        angLabel.setBorder(minimalLabelBorder);
        
        
        movementPanel.add(altitudeLabel);
        movementPanel.add(altitude);
        movementPanel.add(speedLabel);
        movementPanel.add(speed);
        movementPanel.add(directionLabel);
        movementPanel.add(direction);
        movementPanel.add(angLabel);
        movementPanel.add(ang);
    }
}