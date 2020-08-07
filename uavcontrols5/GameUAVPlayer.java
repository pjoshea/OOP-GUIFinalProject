/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVPlayer
 * Extends: GameUAVPlane
 * Purpose: The player class.
 */

package uavcontrols5;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Point2D;
import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.hypot;


public class GameUAVPlayer extends GameUAVPlane {
    private static final GameUAVPlaneTypes t = GameUAVPlaneTypes.PLAYER;
    private static final double maxAng = (Math.PI / 2.0);
    private static final double dir = 90.0;
    private static final double sp = 0;
    private static final int numsides = 3;
    private static final int lt = 10; //the number of redraws between firing lasers
    private static final int FULL_HEALTH = 3;
    
    private double hyp;
    private double sideAngle;
    private boolean autoFire = false;
    
    /*
     * GameUAVPlayer
     * param: canvas, map, ID, color, position, dimensions, direction
     * returns: GameUAVPlayer
     * function: constructor
     */
    public GameUAVPlayer(UAVCanvas c, UAVMap m, String i, UAVColors col, 
                         Point2D pos, Dimension d)
    {
        super(c, m, i, t, col, pos, d, dir, sp, maxAng, numsides, 
              lt, FULL_HEALTH);
        
        hyp = hypot((width/2), (height/2));
        sideAngle = PI - atan(((double) width)/((double) height));
        
        initCoordinates();
        calculateDisplayCoordinates();
    }
    
    /*
     * getAngles
     * param: N/A
     * returns: N/A
     * function: sets the angles and lengths between the center and 
     *           their corresponding vertices
     * notes: placeholder
     */
    @Override
    protected void getAngles() {
        angles[0] = direction;
        lengths[0] = height/2;
        
        angles[1] = direction + sideAngle;
        angles[1] %= twoPI;
        lengths[1] = hyp;
        
        angles[2] = direction - sideAngle;
        angles[2] += (angles[2] < 0) ? twoPI: 0;
        lengths[2] = hyp;
    }
    
    /*
     * tick
     * param: redraws per second
     * returns: N/A
     * function: advances the plane by one frame, chasing the player or 
     *           patrolling its area as appropriate
     */
    @Override
    public void tick(double fps) {
        this.fps = fps;
        if (!crashed) {
            turnTowardsMouse();
            move();
            if (autoFire) { fireLaser(); }
            if (map.getAimMode()) { laserCooldown -= .1;}
            else {laserCooldown--; }
        }
    }
    
    /*
     * turnTowardsMouse
     * param: N/A
     * returns: N/A
     * function: turns the player so that it faces in the direction of the mouse
     *           and accelerates or slows according to the distance 
     *           between the mouse and the player character
     */
    private void turnTowardsMouse() {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        
        Point2D mouseDouble = new Point2D.Double(mouse.x - canvas.getLocationOnScreen().x, 
                          mouse.y - canvas.getLocationOnScreen().y);
        mouseDouble = canvas.translateMovingDisplaytoActual(mouseDouble);
        
        if (isClose(mouseDouble, 10)) { 
            stop();
        } else {
            double mouseDirection = Math.atan2(mouseDouble.getY() - center.getY(), 
                                               mouseDouble.getX() - center.getX());
            if (mouseDirection < 0) { mouseDirection += twoPI;}

            double difference = mouseDirection - direction;
            if (difference != 0) {
                if (abs(difference) <= maxAngle || 
                    abs(difference) >= twoPI-maxAngle){    
                    direction = mouseDirection;
                } else {
                    if ((abs(difference) < PI && difference > 0) || 
                        (abs(difference) > PI && difference > 0)) 
                    { 
                        direction += maxAngle;
                        direction%=twoPI;
                    }
                    else { 
                        direction -= maxAngle; 
                        direction += (direction < 0) ? twoPI:0;
                    }
                }

                getAngles();
                getCoordinates();
            }
            if ((abs(difference) < halfPI/2) ||
                (abs(difference) > (threeHalvesPI + halfPI/2))) {
                    accelerate(); 
            }
        }
    }
    
    /*
     * isClose
     * param: point, range
     * returns: whether or not the player is within the given distance
     *          from the given point
     * function: tests to see if the player is within range of the point
     */
    protected boolean isClose(Point2D p, int range) {
        return p.distance(center) <= range;
    }
    
    /*
     * getDeltaX
     * param: N/A
     * returns: deltaX
     * function: get method
     */
    public double getDeltaX() {
        return (crashed) ? 0: deltaX;
    }
    
    /*
     * getDeltaY
     * param: N/A
     * returns: deltaY
     * function: get method
     */
    public double getDeltaY() {
        return (crashed) ? 0: deltaY;
    }
    
    /*
     * automaticFire
     * param: whether or not autoFire should be true
     * returns: N/A
     * function: sets autoFire to the given value
     */
    public void automaticFire(boolean b) {
        autoFire = b;
    }
}
