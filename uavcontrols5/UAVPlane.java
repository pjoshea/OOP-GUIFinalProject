/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: UAVPlane
 * Purpose: Provides methods and data useful to all of its subclasses. 
 *          The supertype of all of the plane objects in the game/simulation.
 */

package uavcontrols5;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public abstract class UAVPlane {
    protected UAVCanvas canvas;
    protected UAVMap map;

    protected String id;
    protected GameUAVPlaneTypes gtype;
    protected SimUAVPlaneTypes stype;
    protected UAVColors color;
    
    protected int width, height;
    protected Point2D [] vertices;
    protected int numVertices;
    protected Line2D [] edges;
    protected int numEdges;
    protected Polygon shape, dShape;
    
    protected Point2D center, dCenter;
    
    protected double direction;    // direction is in radians
    protected double speed;
    protected double maxAngle; //type-based limit on the number of radians 
                               //that a plane can turn per frame
    
    protected boolean exploding;
    protected int explosionLength;
    protected Line2D [] explosionLines;
    protected final static int EXPLOSION_LINE_NUM = 6;
    
    protected boolean crashed = false;
    protected double fps;
    
    protected double [] angles;
    protected double [] lengths;
    protected double deltaX, deltaY;
    
    protected final double PI = Math.PI;
    protected final double halfPI = PI/((double) 2);
    protected final double twoPI = ((double) 2) * PI;
    protected final double threeHalvesPI = ((double) 3)/((double) 2) * PI;
    
    /*
     * UAVPlane
     * param: canvas, map, ID, game plane type, color, position, dimensions, 
     *        direction, speed, maximum angle by which the plane can turn 
     *        in one frame, number of sides
     * returns: UAVPlane
     * function: constructor
     */
    UAVPlane(UAVCanvas c, UAVMap m, String i, GameUAVPlaneTypes t, 
             UAVColors col, Point2D pos, Dimension d, double dir, 
             double s, double maxAng, int sides) 
    {
        canvas = c;
        map = m;
        id = i;
        gtype = t;
        color = col;
        center = pos;
        width = d.width;
        height = d.height;
        direction = dir;
        speed = s;
        maxAngle = maxAng;
        numEdges = numVertices = sides;
        vertices = new Point2D.Double[numVertices];
        edges = new Line2D.Double[numEdges];
        angles = new double[numVertices];
        lengths = new double[numVertices];
        shape = new Polygon();
        fps = map.getRedrawsPerSecond();
    }
    
    /*
     * UAVPlane
     * param: canvas, map, ID, sim-plane type, color, position, dimensions, 
     *        direction, speed, maximum angle by which the plane can turn 
     *        in one frame, number of sides
     * returns: UAVPlane
     * function: constructor
     */
    UAVPlane(UAVCanvas c, UAVMap m, String i, SimUAVPlaneTypes t, UAVColors col,
             Point2D pos, Dimension d, double dir, double s,  
             double maxAng, int sides) {
        canvas = c;
        map = m;
        id = i;
        stype = t;
        color = col;
        center = pos;
        width = d.width;
        height = d.height;
        direction = dir;
        speed = s;
        maxAngle = maxAng;
        numEdges = numVertices = sides;
        vertices = new Point2D.Double[numVertices];
        edges = new Line2D.Double[numEdges];
        angles = new double[numVertices];
        lengths = new double[numVertices];
        shape = new Polygon();
        fps = map.getRedrawsPerSecond();
    }
    
    /*
     * initCoordinates
     * param: N/A
     * returns: N/A
     * function: initializes the arrays of vertices and edges
     */
    protected void initCoordinates() {
        for (int i = 0; i < numVertices;i++) {
            vertices[i] = new Point2D.Double();
            edges[i] = new Line2D.Double();
        }
        getAngles();
        getCoordinates();
    }
    
    /*
     * getAngles
     * param: N/A
     * returns: N/A
     * function: sets the angles and lengths between the center and 
     *           their corresponding vertices
     * notes: placeholder
     */
    protected void getAngles() {}
    
    /*
     * getCoordinates
     * param: N/A
     * returns: N/A
     * function: calculates the positions of the vertices based on the angles 
     *           and lengths between the center and their corresponding vertices
     */
    protected void getCoordinates() {
        int [] x = new int[numVertices];
        int [] y = new int[numVertices];
        for (int i = 0; i <= numVertices; i++){
            if (i < numVertices){ 
                double xC = (cos(angles[i]) * lengths[i]) + center.getX();
                double yC = (sin(angles[i]) * lengths[i]) + center.getY();
                vertices[i].setLocation(xC, yC); 
                x[i] = (int) xC;
                y[i] = (int) yC;
            }
            if (i != 0) { 
                if (i == numVertices) { 
                    edges[0].setLine(vertices[0], vertices[i-1]);
                } else {
                    edges[i].setLine(vertices[i], vertices[i-1]); 
                }
            }
        }
        shape = new Polygon(x,y,numVertices);
    }
    
    /*
     * calcDelta
     * param: N/A
     * returns: N/A
     * function: calculates the changes in x and y
     */
    protected void calcDelta() {
        deltaX = speed/fps * cos(direction);
        deltaY = speed/fps * sin(direction);
    }
    
    /*
     * move
     * param: N/A
     * returns: N/A
     * function: moves the plane by the predetermined amount
     */
    public void move() {
        calcDelta();
        center = new Point2D.Double((center.getX() + deltaX), 
                                    (center.getY() + deltaY));
        getAngles();
        getCoordinates();
    }
    
    /*************************************************************
     *********************** Placeholders ************************
     *************************************************************/
    public void draw(Graphics2D g, boolean color, boolean fill) {}
    public void draw(Graphics2D g) {}
    public void tick(double fps) {}
    public void turn() {}
    public void turn(int change) {}
    
    /*
     * copyPlane
     * param: N/A
     * returns: UAVPlane
     * function: creates a copy of the plane
     */
    public UAVPlane copyPlane() {
        UAVPlane newPlane = null;
        if (gtype != null) {
            switch(gtype) {
            case PLAYER :
                newPlane = new GameUAVPlayer(canvas, map, id, color, center, 
                                             new Dimension(width, height));
                break;
            case FIGHTER:
                newPlane = new GameUAVHostileFighter(canvas, map, id, color, 
                                          center, new Dimension(width, height),
                                          direction, speed);
                break;
            case STRIKER:
                newPlane = new GameUAVHostileStriker(canvas, map, id, color, 
                                          center, new Dimension(width, height),
                                          direction, speed);
                break;
            }
        } else {
            switch (stype) {
                case TOY :
                    newPlane = new SimUAVToyPlane(canvas, map, id, color, 
                                    center, new Dimension(width, height), 
                                    ((SimUAVToyPlane) this).getAltitude(),
                                    direction, speed, 
                                    ((SimUAVToyPlane) this).getAngularSpeed());
                    break;
                case DELIVERY :
                    newPlane = new SimUAVDeliveryPlane(canvas, map, id, color, 
                                center, new Dimension(width, height), 
                                ((SimUAVDeliveryPlane) this).getAltitude(), 
                                direction, speed, 
                                ((SimUAVDeliveryPlane) this).getAngularSpeed());
                default:
                    System.out.println("Unsupported type");
            }
        }
        
        return newPlane;
    }
    
    /*
     * crashPlane
     * param: N/A
     * returns: N/A
     * function: crashes the plane
     */
    public void crashPlane() {
        crashed = true;
        System.out.println(id + " has crashed.");
    }
    
    /*
     * takeDamage
     * param: N/A
     * returns: N/A
     * function: placeholder for outside classes to access GameUAVPlane's 
     *           takeDamage method
     */
    public void takeDamage() {}
   
    /**************************************************************************
     *          Set & Get Methods for private/protected variables             *
     **************************************************************************/

    public Dimension getDimension() {
        return new Dimension(width, height);
    }
    
     public void setPosition(Point2D p) {
        center.setLocation(p);
        initCoordinates();
    }
    
    public Point2D getPosition() {
        return center;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public double getSpeed() {
        return speed;
    }
    
    public void changeSpeed(int change) {
        speed += change;
    }
    
    public void setDirection(double dir) {}
    
    public double getDirection() {
        return Math.toDegrees(direction);
    }

    public void setName(String newName) {
        id = newName;
    }
    
    public String getName() {
        return id;
    }
    
    public UAVCanvas getCanvas() {
        return canvas;
    }
    
    public UAVMap getMap() {
        return map;
    }
    
    public void setColor(UAVColors color) {
        this.color = color;
    }
    
    public UAVColors getColor() {
        return color;
    }

    public boolean pickDisplayCorrelation(Point2D p) {
        return dShape.contains(p);
    }
    
    public Point2D getDisplayCenter() {
        return dCenter;
    }
    
    public Line2D[] getLineSegments() {
        return edges;
    }
    
    public Line2D[] getDisplayLineSegments() {
        return (gtype != null) ? 
                canvas.translateEdgesActualtoMovingDisplay(edges) :
                canvas.translateEdgesActualtoDisplay(edges);
    }
}
