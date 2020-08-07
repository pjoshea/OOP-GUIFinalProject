/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVLaser
 * Purpose: The laser moves in a straight line from the point where it was 
 *          fired, in the direction that it was fired, until it collides with 
 *          either a plane or exits the bounds of the game.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import static java.lang.Math.*;

public class GameUAVLaser {
    private UAVMap map;
    private UAVCanvas canvas;
    private Point2D leader, tail;
    private Point2D displayLeader, displayTail;
    private double direction;
    
    private final Rectangle bounds;
    
    private boolean active = true;
    private boolean visible = true;
    private final boolean hostile;
    
    private int deltaX, deltaY;
    
    private final int DISPLAY_LENGTH = 20;
    private final int LASER_SPEED = 250;
    private double fps;
    
    /*
     * GameUAVLaser
     * param: canvas, map, initial location, direction to travel, whether or 
     *        not the plane will affect the player
     * returns: GameUAVLaser
     * function: constructor
     */
    GameUAVLaser(UAVCanvas canvas, UAVMap map, Point2D start, 
                 double direction, boolean hostile) {
        this.map = map;
        this.canvas = canvas;
        this.tail = start;
        this.direction = direction;
        this.hostile = hostile;
        fps = map.getRedrawsPerSecond();
        bounds = map.getBounds();
        calculateVector();
    }
    
    /*
     * tick
     * param: redraws per second
     * returns: N/A
     * function: advances the laser by one frame, moving, testing if 
     *           it needs to be drawn and if it's still active
     */
    public void tick(double fps) {
        if (active) {
            deltaX = (int)(LASER_SPEED/fps * cos(direction));
            deltaY = (int) (LASER_SPEED/fps * sin(direction));
            move();
            checkVisible();
            checkOOB();
        }
    }
    
    /*
     * move
     * param: N/A
     * returns: N/A
     * function: moves the laser by the predetermined amount
     */
    private void move() {
        tail.setLocation(tail.getX() + deltaX, tail.getY() + deltaY);
        displayTail = canvas.translateActualtoMovingDisplay(tail);
        leader.setLocation(leader.getX() + deltaX, leader.getY() + deltaY);
        displayLeader = canvas.translateActualtoMovingDisplay(leader);
    }

    /*
     * calculateVector
     * param: N/A
     * returns: N/A
     * function: creates the shape of the laser
     */
    private void calculateVector() {
        deltaX = (int) (DISPLAY_LENGTH * cos(direction));
        deltaY = (int) (DISPLAY_LENGTH * sin(direction));
        leader = new Point2D.Double((tail.getX() + deltaX), 
                                    (tail.getY() + deltaY));
    }
    
    /*
     * draw
     * param: Graphics2D object on which to draw the laser
     * returns: N/A
     * function: draws the laser if it's still active and on screen
     */
    public void draw(Graphics2D g) { 
        if (active && visible) {
            if (!hostile) { g.setColor(Color.BLUE);}
            else { g.setColor(Color.RED); }
            g.setStroke(new BasicStroke(2));
            g.drawLine((int) displayLeader.getX(), (int) displayLeader.getY(), 
                       (int) displayTail.getX(), (int) displayTail.getY());
        }
    }
    
    /*
     * copy
     * param: N/A
     * returns: laser
     * function: creates a copy with the same exact position, direction, 
     *           and hostility
     */
    public GameUAVLaser copy() {
        return new GameUAVLaser(canvas, map, leader, direction, hostile);
    }

    /*
     * isActive
     * param: N/A
     * returns: whether or not the laser is both active and visible
     * function: get method
     */
    public boolean isActive() {
        return (active && visible);
    }
    
    /*
     * checkOOB
     * param: N/A
     * returns: N/A
     * function: checks to see if the laser is beyond the boundaries 
     *           of the play area
     */
    private void checkOOB() {
        if (tail.getX() > bounds.x + bounds.width || tail.getX() < bounds.x || 
            tail.getY() > bounds.y || tail.getY() < bounds.y - bounds.height) {
            active = false;
        }
    }

    /*
     * checkVisible
     * param: N/A
     * returns: N/A
     * function: checks to see if the laser is on screen
     */
    private void checkVisible() {
        if (displayTail.getX() < 0 || displayTail.getY() < 0 || 
            displayTail.getX() > canvas.getWidth() || 
            displayTail.getY() > canvas.getHeight()) {
            visible = false;
        }
    }
    
    /*
     * collide
     * param: N/A
     * returns: N/A
     * function: sets laser to inactive
     */
    public void collide() {
        active = false;
    }
    
    
/*******************************************************************************
 ****************************** Set & Get Methods ******************************
 ******************************************************************************/
    
    Point2D getLeader() {
        return leader;
    }
    
    Point2D getDisplayLeader() {
        return displayLeader;
    }


    public boolean isHostile() {
        return hostile;
    }

    public Line2D getDisplayLine() {
        return new Line2D.Double(displayLeader, displayTail);
    }
}
