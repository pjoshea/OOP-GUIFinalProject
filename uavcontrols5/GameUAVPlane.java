/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVPlane
 * Extends: UAVPlane
 * Purpose: The base class which all game planes extend. Provides methods which 
 *          are useful for all game planes which don't overwrite them.
 */

package uavcontrols5;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.Random;


public abstract class GameUAVPlane extends UAVPlane {        
    protected boolean hostile;
    protected int health;
    protected int acceleration;
    protected double laserCooldown = 0;
    protected final int LASER_TIMER;

    protected Random r = new Random();
    protected final int MAX_SPEED = 200;
    protected final int MAX_ACCELERATION = 5;
    protected final int DECELERATION_SPEED = 10;
    protected final int VISIBILITY_RANGE = 500;
    protected final int WALL_PROXIMITY = 50;
    
    /*
     * GameUAVPlane
     * param: canvas, map, ID, plane type, color, position, dimensions, 
     *        direction, speed, maximum angle by which the plane can turn 
     *        in one frame, number of sides, length of laser cooldown, health
     * returns: GameUAVPlane
     * function: constructor
     */
    public GameUAVPlane(UAVCanvas c, UAVMap m, String i, GameUAVPlaneTypes t, 
                        UAVColors col, Point2D pos, Dimension d, double dir,
                        double s, double maxAng, int sides, int ltimer, int h) 
    {
        super(c, m, i, t, col, pos, d, dir, s, maxAng, sides);
        hostile = (t != GameUAVPlaneTypes.PLAYER);
        health = h;
        LASER_TIMER = ltimer;
    }
    
    /*
     * playerInRange
     * param: N/A
     * returns: whether or not the player is close enough to 'see'
     * function: checks to see if the player is visible from this distance
     */
    public boolean playerInRange() {
        return (center.distance(map.getPlayer().center) <= VISIBILITY_RANGE);
    }
    
    /*
     * chasePlayer
     * param: N/A
     * returns: N/A
     * function: chase the player (generic)
     */
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
    protected void patrol() {
        double change = 0;
        if (!map.getWalls().nearby(center, WALL_PROXIMITY)) {
            change = r.nextInt(91);
            change -= 45;
            change = Math.toRadians(change);
        } else {
            //If the plane is close to the wall, it ought to redirect itself so that it doesn't collide with it
            int dirofClosestWall = map.getWalls().directionTo(center, WALL_PROXIMITY);
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
                        change = (direction >= threeHalvesPI) ? maxAngle : -maxAngle;
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
    
    /*
     * draw
     * param: Graphics2D object on which to draw the plane
     * returns: N/A
     * function: draw the plane, or its explosion (if it recently crashed)
     */
    @Override
    public void draw(Graphics2D g) { 
        if (!crashed) {
            calculateDisplayCoordinates();
            g.setColor(color.getColorValue());
            g.draw(dShape);
            g.fill(dShape);
        }
        if (exploding && explosionLength > 0) {
            g.setColor(color.getColorValue());
            for (int i = 0; i < 6; i++) {
                Line2D l = explosionLines[i];
                double dX = (l.getX2() - l.getX1())/2;
                double dY = (l.getY2() - l.getY1())/2;
                explosionLines[i].setLine(l.getX1() + dX, l.getY1() + dY, 
                                          l.getX2() + dX, l.getY2() + dY);
                g.draw(explosionLines[i]);
            }
            explosionLength--;
        }
    }
    
    /*
     * calculateDisplayCoordinates
     * param: N/A
     * returns: N/A
     * function: converts the shape and position from the "real" coordinate
     *           system to that of the Graphics2D object
     */
    protected void calculateDisplayCoordinates() {
        dShape = canvas.translateShapeActualtoMovingDisplay(shape);
        dCenter = canvas.translateActualtoMovingDisplay(center);
    }
    
    /*
     * fireLaser
     * param: N/A
     * returns: N/A
     * function: if possible, creates a laser with the same direction as 
     *           the plane and with the initial location equal to the 
     *           frontmost vertex and the hostility equal to that of the plane's
     */
    public void fireLaser() {
        if (laserCooldown <= 0) {
            map.createLaser(direction, (Point2D.Double) vertices[0].clone(), hostile);
            laserCooldown = LASER_TIMER;
        }
    }

    /*
     * accelerate
     * param: N/A
     * returns: N/A
     * function: increase speed up to the maximum by a constant, 
     *           plane-type-dependent factor 
     */
    void accelerate() {
        if (speed <= MAX_SPEED - acceleration) {
            speed += acceleration;
        } else {
            speed = MAX_SPEED;
        }
    }
    
    /*
     * stop
     * param: N/A
     * returns: N/A
     * function: sets speed to 0 all at once
     */
    void stop() {
        speed = 0;
    }
    
    /*
     * crashPlane
     * param: N/A
     * returns: N/A
     * function: instantly crash the plane
     */
    @Override 
    public void crashPlane() {
        crashed = true;
        speed = 0;
        exploding = true;
        explosionLength = 20;
        explosionLines = new Line2D[EXPLOSION_LINE_NUM];
        for (int i = 0; i < explosionLines.length; i++) {
            Point2D head = new Point2D.Double(
                          5 * cos(i * PI/3 + PI/6) + dCenter.getX(), 
                          dCenter.getY() -  5 * sin(i * PI/3 + PI/6));
            explosionLines[i] = new Line2D.Double(dCenter, head);
        }
    }
    
    /*
     * takeDamage
     * param: N/A
     * returns: N/A
     * function: reduce health by 1. If it falls to 0, then crash the plane
     */
    @Override 
    public void takeDamage() {
        health--;
        if (health == 0) {
            crashed = true;
            speed = 0;
            exploding = true;
            explosionLength = 20;
            explosionLines = new Line2D[EXPLOSION_LINE_NUM];
            for (int i = 0; i < explosionLines.length; i++) {
                Point2D head = new Point2D.Double(
                              5 * cos(i * PI/3 + PI/6) + dCenter.getX(), 
                              dCenter.getY() -  5 * sin(i * PI/3 + PI/6));
                explosionLines[i] = new Line2D.Double(dCenter, head);
            }
        }
    }
}
