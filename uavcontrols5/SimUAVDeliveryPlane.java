/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVDeliveryPlane
 * Extends: SimUAVPlane
 * Purpose: The subclass of SimUAVPlane which represents the delivery plane, 
 *          which is distinguished by its diamond shape.
 * Notes: A strange things occurs because of the way that the Canvas determines
 *       how the y-axis grows: all of the calculations done in order to 
 *       the relative positions of the four vertices have to flip the "normal"
 *       sign of a ycoordinate. Because the y-axis grows down but  
 *       this program uses the more conventional method of having 90 degrees 
 *       pointing towards the top of the screen, the calculations done for 
 *       determining the difference between the lead point and any other vertex
 *       need to account for the fact that a positive difference means that the 
 *       other vertex will be further down the page than the leader, not further 
 *       up as in conventional practice. As such, in quadrants where the 
 *       y-difference would be added need to instead subtract the y-difference, 
 *       and vice versa. The x-difference remains as it is conventionally.
 */

package uavcontrols5;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public class SimUAVDeliveryPlane extends SimUAVPlane {
    private static SimUAVPlaneTypes t = SimUAVPlaneTypes.DELIVERY;
    private static final int numsides = 4;
    private static final double maxAng = (Math.PI/4);
    
    /*
     * SimUAVDeliveryPlane
     * param: canvas, map, ID, color, initial location, dimensions, altitude
     *        direction, speed, angular speed
     * returns: SimUAVDeliveryPlane
     * function: constructor
     */
    public SimUAVDeliveryPlane(UAVCanvas c, UAVMap m, String i, UAVColors col, 
                               Point2D p, Dimension d, int alt, double dir, 
                               double s, double angSpeed)
    {   
        super(c, m, i, t, col, p, d, alt, (Math.toRadians(dir % 360)), s,
              Math.toRadians(angSpeed), maxAng, numsides);
        
        initCoordinates();
        calculateDisplayCoordinates();
    }
    
    public SimUAVDeliveryPlane(SimUAVPlane plane) {
        super(plane.getCanvas(), plane.getMap(), plane.id, plane.stype,
              plane.getColor(), plane.center, plane.getDimension(), 
              plane.getAltitude(), plane.getDirection(), plane.getSpeed(), 
              plane.getAngularSpeed(), plane.maxAngle, plane.numEdges);
        
        initCoordinates();
        calculateDisplayCoordinates();
    }
    
    /*
     * getAngles
     * param: N/A
     * returns: N/A
     * function: calculates the angles and distances between the plane's center 
     *           (1/2 of the width, 1/2 of the height) and the vertices
     *           corresponding to those angles.
     */
    @Override
    protected void getAngles() {
        angles[0] = direction;
        lengths[0] = height/2;
        
        angles[1] = direction + threeHalvesPI;
        angles[1] %= twoPI;
        lengths[1] = width/2;
        
        angles[2] = direction + PI;
        angles[2] %= twoPI;
        lengths[2] = height/2;
        
        angles[3] = direction + halfPI;
        angles[3] %= twoPI;
        lengths[3] = width/2;
    }
    
    /*
     * turn
     * param: N/A
     * returns: N/A
     * function: reangles the plane according to the angular speed and then 
     *           recalculates the positions of the vertices
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
     * param: change in direction
     * returns: N/A
     * function: reangles the plane according to the angular speed and the 
     *           directional change given combined and then recalculates 
     *           the positions of the vertices.
     */
    @Override
    public void turn(int change) {
        direction+= ((angularSpeed + change)/fps);
        direction = direction % twoPI;
        if (direction < 0) {direction += twoPI;}
        
        getAngles();
        getCoordinates();
    }
}
