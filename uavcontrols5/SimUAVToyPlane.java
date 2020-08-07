/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVToyPlane
 * Extends: SimUAVPlane
 * Purpose: The subclass of SimUAVPlane distinguished by its 
 *          triangular appearance.
 */

package uavcontrols5;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import static java.lang.Math.abs;
import static java.lang.Math.atan;
import static java.lang.Math.hypot;


public class SimUAVToyPlane extends SimUAVPlane {
    private static final int numsides = 3;
    private static final SimUAVPlaneTypes t = SimUAVPlaneTypes.TOY;
    private static double maxAng = (Math.PI / 6.0);
    
    private final double hyp;
    private final double sideAngle;
    
    /*
     * SimUAVToyPlane
     * param: canvas, map, ID, color, position, dimensions, altitude,
     *        direction, speed, angular speed
     * returns: SimUAVToyPlane
     * function: constructor
     */
    public SimUAVToyPlane(UAVCanvas c, UAVMap m, String i, UAVColors col, 
                          Point2D p, Dimension d, int alt, double dir, 
                          double s, double angSpeed)
    {   
        super(c, m, i, t, col, p, d, alt, (Math.toRadians(dir % 360)), s,
              Math.toRadians(angSpeed), maxAng, numsides);
        
        
        hyp = hypot((width/2), (height/2));
        sideAngle = PI - atan(((double) width)/((double) height));
        
        initCoordinates();
        calculateDisplayCoordinates();
    }
    
    /*
     * SimUAVToyPlane
     * param: SimUAVToyPlane
     * returns: SimUAVToyPlane
     * function: constructor
     */
    public SimUAVToyPlane(SimUAVPlane plane) {
        super(plane.getCanvas(), plane.getMap(), plane.id, plane.stype,
              plane.getColor(), plane.center, plane.getDimension(), 
              plane.getAltitude(), plane.getDirection(), plane.getSpeed(), 
              plane.getAngularSpeed(), plane.maxAngle, plane.numEdges);
        
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
     * turn
     * param: the change in direction in degrees
     * returns: N/A
     * function: turns the plane by the given quantity combined 
     *           with the angular speed
     */
    @Override
    public void turn(int change) {
        double changeRad = Math.toRadians(change);
        direction+= ((angularSpeed + changeRad)/fps);
        direction = direction % twoPI;
        if (direction < 0) {direction += twoPI;}
        
        getAngles();
        getCoordinates();
    }
    
    /*
     * turn
     * param: N/A
     * returns: N/A
     * function: turns the plane by the angular speed
     */
    @Override
    public void turn() {
        direction+= (angularSpeed/fps);
        direction = direction % twoPI;
        if (direction < 0) {direction += twoPI;}
        
        getAngles();
        getCoordinates();
    }
    
    /*
     * turn
     * param: N/A
     * returns: N/A
     * function: turns the plane towards the target direction
     */
    @Override
    protected void turnTowardsTarget() {
        if ( abs(targetDirection - direction) <= angularSpeed){    
            direction = targetDirection;
            direction = direction % twoPI;
            if (direction < 0) {direction+=twoPI;}
            changingDirection = false;
        } else {
            //calculate the best direction to turn
            double oppositeDirection = (direction + Math.PI) % (Math.PI * 2);
            if (oppositeDirection > targetDirection) {
                direction += maxAng/fps;
            } else {
                direction -= maxAng/fps;
            }
        }
        getAngles();
        getCoordinates();
    }
}

