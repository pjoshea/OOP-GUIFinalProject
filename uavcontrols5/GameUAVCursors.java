/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Enum: GameUAVCursors
 * Purpose: Provides basic cursors, and the method required to reangle the 
 *          directional cursors
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public enum GameUAVCursors {
    Menu ("/images/menu_cursor.png", "Menu"),
    Near ("/images/near_cursor.png", "Near"),
    Mid ("/images/mid_cursor.png", "Mid"),
    Far ("/images/far_cursor.png", "Far"),
    Aim ("/images/aim_cursor.png", "Aim");
    
    BufferedImage icon;
    BufferedImage primitive;
    String name;
    
    /*
     * GameUAVCursors
     * param: a String which holds the rest of the path to an image, 
     *        a String representation of the enum constant
     * returns: GameUAVCursors
     * function: constructor
     * notes: like the UAVButtons, this assumes that the assets haven't been 
     *        moved, and also that the user ran the program from 
     *        within the directory that contains the images folder
     */
    GameUAVCursors(String imageURI, String n) {
        try {
            primitive = ImageIO.read(new File(System.getProperty("user.dir") + imageURI));
        } catch (IOException i) {
            System.err.println("Cursor image file not found");
        }
        if (primitive != null) {
            ColorModel cm = primitive.getColorModel();
            boolean alphaPreMult = cm.isAlphaPremultiplied();
            WritableRaster raster = primitive.copyData(primitive.getRaster().createCompatibleWritableRaster());
            icon = new BufferedImage(cm, raster, alphaPreMult, null);
        }
        name = n;
    }
    
    /*
     * getCursor
     * param: N/A
     * returns: Cursor
     * function: creates a cursor from the icon
     */
    public Cursor getCursor() {
        return Toolkit.getDefaultToolkit().createCustomCursor(icon, new Point(0,0), name);
    }
    
    /*
     * getIcon
     * param: GameUAVCursors
     * returns: BufferedImage
     * function: gets the primitive image of the cursor
     */
    public static BufferedImage getIcon(GameUAVCursors c) {
        return c.primitive;
    } 
    
    /*
     * turnCursor
     * param: the direction that the cursor ought to be angled
     * returns: Cursor
     * function: draws the primitive image onto the icon using a transform
     *           which rotates the bits so that 0 radians on the primitive is 
     *           facing the direction given. Creates a cursor using the newly 
     *           transformed icon also attempts to set the hotspot
     *           to match the icon more closely.
     */
    public Cursor turnCursor(double direction) {
        Graphics2D iconMap = icon.createGraphics();
        iconMap.setBackground(new Color(00,00,00,00));
        iconMap.clearRect(0,0, icon.getWidth(), icon.getHeight());
        AffineTransform at = iconMap.getTransform();
        at.rotate(direction, 12, 12);
        BufferedImageOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        iconMap.drawImage(primitive, op, 0, 0);
        
        Point hotSpot = new Point(12 + (int) (12 * Math.cos(direction)), 
                                  12 + (int)(12 * Math.sin(direction)));
        return Toolkit.getDefaultToolkit().createCustomCursor(icon, hotSpot, name);
    }
}
