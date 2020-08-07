/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVHostileStriker
 * Extends: GameUAVPlane
 * Purpose: The hostile striker is intended to be the more difficult enemy 
 *          in the game. Its objective is to meander around until the player
 *          enters its sight, and then pursue with the sole intent
 *          of colliding with the player. It was intended to move in straight 
 *          lines at higher speed than the player.
 */

package uavcontrols5;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import static java.lang.Math.atan;
import static java.lang.Math.hypot;

class GameUAVHostileStriker extends GameUAVPlane {
    private static final GameUAVPlaneTypes t = GameUAVPlaneTypes.STRIKER;
    private static final int numsides = 4;
    private static final int lt = 50; //the number of redraws between firing lasers
    private static final double maxAng = (Math.PI/8.0);
    private static final int FULL_HEALTH = 1;
    
    private final double frontVLength, backVLength;
    private final double frontVAngle, backVAngle;
    
    /*
     * GameUAVHostileStriker
     * param: canvas, map, ID, color, position, dimensions, 
     *        direction, speed
     * returns: GameUAVHostileStriker
     * function: constructor
     */
    public GameUAVHostileStriker(UAVCanvas c, UAVMap m, String i, 
                                 UAVColors col, Point2D p, Dimension d,
                                 double dir, double s)
    {   
        super(c, m, i, t, col, p, d, (Math.toRadians(dir % 360)), s, 
              maxAng, numsides, lt, FULL_HEALTH);
        
        frontVLength = hypot(height/2, width/6);
        backVLength = hypot(width/2, height/2);
        frontVAngle = atan(((double) width)/(double) (3 * height));
        backVAngle = PI - atan(((double) width)/((double) height));
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
        angles[0] = direction - frontVAngle;
        angles[0] += (angles[0] < 0) ? twoPI : 0;
        lengths[0] = frontVLength;
        
        angles[1] = direction - backVAngle;
        angles[1] += (angles[1] < 0) ? twoPI : 0;
        lengths[1] = backVLength;
        
        angles[2] = direction + backVAngle;
        angles[2] %= twoPI;
        lengths[2] = backVLength;
        
        angles[3] = direction + frontVAngle;
        angles[3] %= twoPI;
        lengths[3] = frontVLength;
    }
}