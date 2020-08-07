/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVGraphicalButton
 * Extends: Rectangle
 * Purpose: Graphical Buttons allow the game to display all of its buttons 
 *          inside the canvas, without needing to overlay a JButton on top.
 * Notes: Only after I had already finished working out the Graphical Button 
 *        system, did I realize that it may have been possible to complete the 
 *        same objective through slight modifications to one of Java's or 
 *        Swing's preexisting classes.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public abstract class GameUAVGraphicalButton extends Rectangle 
                                             implements MouseListener,
                                                        ComponentListener
{
    UAVMap map;
    GameUAVGraphicalState parent;
    Dimension dim;
    Point2D pos;
    double relX, relY;
    Color borderColor, textColor, bgColor;
    
    BasicStroke boxStrokeWidth = new BasicStroke(5);
    
    String text;
    Font font;
    int vAlign, hAlign;
    
    protected boolean active = true;
    protected String command;
    
    /*
     * GameUAVGraphicalButton
     * param: map, parent graphical state, position, dimensions, text, 
     *        border color, text color, background color, font, command String
     * returns: GameUAVGraphicalButton
     * function: constructor
     */
    GameUAVGraphicalButton(UAVMap m, GameUAVGraphicalState par, Point2D p, Dimension d, 
                       String text, Color bCol, Color tCol, Color bgCol, Font f,
                       String cmd) 
    { 
        map = m;
        parent = par;
        command = cmd;
        
        map.getParent().getRootPane().addComponentListener(this);
        pos = p;
        dim = d;
        setRect(pos.getX(), pos.getY(), dim.width, dim.height);
        relX = pos.getX() / map.getCanvas().getWidth();
        relY = pos.getY() / map.getCanvas().getHeight();
        this.text = text;
        borderColor = bCol;
        textColor = tCol;
        bgColor = bgCol;
        font = f;
        map.getCanvas().addMouseListener(this);
    }
    
    /*
     * drawButton
     * param: Graphics2D on which to draw the button
     * returns: N/A
     * function: draw method
     * notes: The transition from user space to device space on the programmer's 
     *        computer seems to remove the top and the leftmost pixels of the 
     *        shape. If the Rendering Hint Key: KEY_STROKE_CONTROL is set to 
     *        Value: RenderingHints.VALUE_STROKE_PURE, the leftmost pixel is 
     *        restored, but the rightmost pixel is removed in its place.
     *        If, simultaneously, the AntiAliasing is turned on, 
     *        the lost pixels are restored
     */
    public void drawButton(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(boxStrokeWidth);
        g.setColor(borderColor);
        g.draw(this);
        g.setColor(bgColor);
        g.fill(this);
        
       
        g.setFont(font);
        g.setColor(textColor);
        FontMetrics fm = g.getFontMetrics();
        vAlign = (dim.height - fm.getHeight())/2 + fm.getAscent();
        hAlign = (dim.width - fm.stringWidth(text))/2;
        g.setStroke(new BasicStroke(2));
        g.drawString(text,(int) pos.getX() + hAlign,(int) pos.getY() + vAlign);
    }
    
    /*
     * drawButton
     * param: Graphics2D on which to draw the button, position to relocate the
     *        button to
     * returns: N/A
     * function: draw method
     */
    public void drawButton(Graphics2D g, Point2D p) {
        setLocation((int) p.getX(), (int) p.getY());
        pos = p;
        
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setStroke(boxStrokeWidth);
        g.setColor(borderColor);
        g.draw(this);
        g.setColor(bgColor);
        g.fill(this);
        
       
        g.setFont(font);
        g.setColor(textColor);
        FontMetrics fm = g.getFontMetrics();
        vAlign = (dim.height - fm.getHeight())/2 + fm.getAscent();
        hAlign = (dim.width - fm.stringWidth(text))/2;
        g.setStroke(new BasicStroke(2));
        g.drawString(text,(int) pos.getX() + hAlign,(int) pos.getY() + vAlign);
    }
    
    /*
     * setActive
     * param: whether or not the button ought to be clickable
     * returns: N/A
     * function: set method. Adds mouse listener to the canvas when the passed 
     *           value is true, removes it when the passed value is false
     */
    public void setActive(boolean b) {
        if (b && !active) {
            map.getCanvas().addMouseListener(this);
        }
        if (!b && active) {
            map.getCanvas().removeMouseListener(this);
        }
        active = b;
    }
    
    /*
     * componentResized
     * param: ComponentEvent
     * returns: N/A
     * function: when the canvas is resized, the button ought to reposition
     *           itself to maintain relative positioning
     */
    @Override
    public void componentResized(ComponentEvent e) {
        Rectangle r = map.getCanvas().getBounds();
        pos = new Point2D.Double(r.getWidth() * relX, r.getHeight() * relY);
        setRect(pos.getX(), pos.getY(), dim.getWidth(), dim.getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    /*
     * mouseClicked
     * param: MouseEvent
     * returns: N/A
     * function: when the mouse clicks inside the bounds of the button while 
     *           it's active, the button tells the parent graphical state
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (active && this.contains(e.getPoint())) {
            parent.accept(command);
        }
    }
}
