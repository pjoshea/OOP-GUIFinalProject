/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVHostileFighter
 * Extends: GameUAVPlane
 * Purpose: The hostile fighter is the most basic enemy in the game. Its 
 *          objective is to meander around until the player enters its sight, 
 *          and then pursue the player while firing lasers (but avoid colliding 
 *          with the player if it can help it).
 */

package uavcontrols5;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import static java.lang.Math.*;

public class GameUAVHostileFighter extends GameUAVPlane {
    private static final int numsides = 4;
    private static final int lt = 20; //the number of redraws between firing lasers
    private static final GameUAVPlaneTypes t = GameUAVPlaneTypes.FIGHTER;
    private static final double maxAng = (Math.PI/6);
    private static final int FULL_HEALTH = 1;
    
    private final double hyp;
    private final double sideAngle;
    
    /*
     * GameUAVHostileFighter
     * param: canvas, map, ID, color, position, dimensions, direction, speed
     * returns: GameUAVHostileFighter
     * function: constructor
     */
    public GameUAVHostileFighter(UAVCanvas c, UAVMap m, String i, UAVColors col,
                                 Point2D p, Dimension d, double dir, double s) 
    {
        super(c, m, i, t, col, p, d, Math.toRadians(dir % 360),
              s, maxAng, numsides, lt, FULL_HEALTH);
        
        hyp = hypot((width/2), (height/2));
        sideAngle = PI - atan(((double) width) / ((double) height));
        
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
        
        angles[2] = direction - PI;
        angles[2] += (angles[2] < 0) ? twoPI : 0;
        lengths[2] = 0;
        
        angles[3] = direction - sideAngle;
        angles[3] += (angles[3] < 0) ? twoPI: 0;
        lengths[3] = hyp;
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
        if (playerInRange()) { chasePlayer(); }
        else { patrol(); }
    }
    
    /*
     * chasePlayer
     * param: N/A
     * returns: N/A
     * function: moves the plane towards the player character, 
     *           firing lasers when it can
     */
    @Override
    protected void chasePlayer() {
        turnTowardsPlayer();
        move();
        fireLaser();
        laserCooldown--;
    }
    
    /*
     * turnTowardsPlayer
     * param: N/A
     * returns: N/A
     * function: angles the plane so that it's moving towards the player, 
     *           unless the player is close enough, then it will move 
     *           to avoid colliding with the player
     */
    @Override
    protected void turnTowardsPlayer() {
        GameUAVPlayer player = map.getPlayer();
        double playerDir = player.getDirection();
        double change = 0;
        if (player.isClose(center, 100)) {
            change = avoidCollision(player, playerDir);
        } else {
            Point2D target = player.getPosition();
            double targetDir = Math.atan2(target.getY() - center.getY(), 
                                       target.getX() - center.getX());
            if (targetDir < 0) { targetDir += twoPI;}

            double difference = targetDir - direction;
            if (difference != 0) {
                if (abs(difference) <= maxAngle || 
                    abs(difference) >= twoPI-maxAngle){    
                    direction = targetDir;
                } else {
                    if ((abs(difference) < PI && difference > 0) || 
                        (abs(difference) > PI && difference > 0)) 
                    { direction += maxAngle; }
                    else { direction -= maxAngle; }

                    if (direction < 0) { direction += twoPI;}
                    direction %=twoPI;
                }
                change = abs(difference);
            }
        }

        getAngles();
        getCoordinates();
        if (change < halfPI || change > threeHalvesPI) { accelerate();}
    }
    
    /*
     * avoidCollision
     * param: player, the direction the player is moving
     * returns: the direction that the plane needs to turn towards so that 
     *          it doesn't collide with the player character
     * function: avoid collisions
     * notes: not implemented at current time
     */
    @Override
    protected double avoidCollision(GameUAVPlayer player, double playerDir) {
        double change = 0;
        
        return change;
    }
    
    /*
     * patrol
     * param: N/A
     * returns: N/A
     * function: meander around the boundaries of the play area,
     *           avoiding collision with the walls
     */
    @Override
    protected void patrol() {
        double change = 0;
        if (!map.getWalls().nearby(center, WALL_PROXIMITY)) {
            change = r.nextInt(91);
            change -= 45;
            change = Math.toRadians(change);
        } else {
            int dirofClosestWall = map.getWalls().directionTo(center, 
                                                              WALL_PROXIMITY);
            double wallDir = dirofClosestWall * PI/4;
            if ((abs(direction - wallDir) >= 3*PI/4) && 
                (abs(direction - wallDir) <= 5*PI/4)) {
                change = 0;
            } else {
                switch (dirofClosestWall) {
                    case 0:
                        change = (direction >= 0) ? maxAngle : -maxAngle;
                        break;
                    case 1:
                        change = (direction >= PI/4) ? maxAngle : -maxAngle;
                        break;
                    case 2:
                        change = (direction >= halfPI) ? maxAngle : -maxAngle;
                        break;
                    case 3: 
                        change = (direction >= 3*PI/4) ? maxAngle : -maxAngle;
                        break;
                    case 4: 
                        change = (direction >= PI) ? maxAngle : -maxAngle;
                        break;
                    case 5:
                        change = (direction >= 5 * PI/4) ? maxAngle : -maxAngle;
                        break;
                    case 6:
                        change = (direction >= threeHalvesPI) ? maxAngle : 
                                                                -maxAngle;
                        break;
                    case 7:
                        change = ((direction >= 7 * PI/4) || 
                                  (direction < PI/4)) ? maxAngle : -maxAngle;
                        break;
                }
            }
        }
        direction = direction + change;
        if (direction < 0) { direction += twoPI; }
        getAngles();
        getCoordinates();
    }
    
}
