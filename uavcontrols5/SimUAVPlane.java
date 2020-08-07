/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: SimUAVPlane
 * Extends: UAVPlane
 * Purpose: A subclass of the UAVPlane which creates methods for subclasses
 *          that are designed solely for the simulation mode of the program.
 */

package uavcontrols5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public abstract class SimUAVPlane extends UAVPlane {    
    protected double targetDirection;
    protected boolean changingDirection;
    
    protected int altitude;
    protected double angularSpeed;
    protected boolean selected = false;
    
    /*
     * SimUAVPlane
     * param: canvas, map, ID, plane type, color, position, dimensions, altitude,
     *        direction, speed, angular speed, the maximum angle which the plane
     *        can turn in a frame, the number of sides to the plane
     * returns: SimUAVPlane
     * function: constructor
     */
    public SimUAVPlane(UAVCanvas c, UAVMap m, String i, SimUAVPlaneTypes t, 
                       UAVColors col, Point2D p, Dimension d, int alt, 
                       double dir, double s, double angSpd, 
                       double maxAng, int numSides) 
    {
        super(c, m, i, t, col, p, d, dir, s, maxAng, numSides);
        altitude = alt;
        angularSpeed = angSpd;
    }
    
    /*
     * draw
     * param: the graphics object on which to draw, whether to give it 
     *        full color, whether to fill the interior
     * returns: N/A
     * function: draw method
     */
    @Override
    public void draw(Graphics2D g, boolean colorBool, boolean fill) { 
        calculateDisplayCoordinates();
        if (colorBool) { g.setColor(color.getColorValue());}
        else { g.setColor(Color.BLACK);}
        if (crashed) {g.setColor(Color.DARK_GRAY);}
        g.draw(shape);
        if (fill && !selected) { g.fill(shape); }
    }
    
    /*
     * tick
     * param: # of redraws per second
     * returns: N/A
     * function: advances the plane by one frame (moves the plane
     *           and turns it if necessary)
     */
    @Override
    public void tick(double fps) {
        this.fps = fps;
        if (changingDirection){ turnTowardsTarget(); }
        else { turn(); }
        move();
    }
    
    /*
     * repair
     * param: N/A
     * returns: N/A
     * function: uncrashes plane
     */
    public void repair() {
        crashed = false;
        System.out.println(id + " has been repaired.");
    }
 
    /*
     * turnTowardsTarget
     * param: N/A
     * returns: N/A
     * function: placeholder(?)
     */
    protected void turnTowardsTarget() {}
    
    /*
     * calculateDisplayCoordinates
     * param: N/A
     * returns: N/A
     * function: converts the "real" coordinates to Graphics object coordinates
     */
    protected void calculateDisplayCoordinates() {
        dShape = canvas.translateShapeActualtoDisplay(shape);
        dCenter = canvas.translateActualtoDisplay(center);
    }
    
/*******************************************************************************
 **************************** Set & Get Methods ********************************
 ******************************************************************************/
    @Override
    public void setDirection(double d) {
        targetDirection = Math.toRadians(d % 360);
        if (targetDirection < 0) {targetDirection += (Math.PI *2); }
        changingDirection = true;
    }
    
    public boolean isSelected() { return selected; }
    public void setSelected(boolean b) { 
        selected = b;
        if (b) { System.out.println(id + " selected"); }
    }
    
    public double getAngularSpeed() {
        return angularSpeed;
    }
    public void setAngularSpeed(double d) {
        angularSpeed = d;
    }
    public void changeAngSpeed(int change) {
        angularSpeed += Math.toRadians(change);
        if (angularSpeed > maxAngle) {angularSpeed = maxAngle;}
        if (angularSpeed < - maxAngle) {angularSpeed = - maxAngle;}
    }
    
    public int getAltitude() {
        return altitude;
    }
    public void setAltitude(int alt) {
        altitude = alt;
    }
    public void changeHeight(int change) {
        altitude += change; 
    }
}
