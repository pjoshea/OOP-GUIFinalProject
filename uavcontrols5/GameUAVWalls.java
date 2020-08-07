/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVWalls
 * Purpose: Provides visible boundaries on the play area, which kill anything 
 *          that attempts to go beyond them.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class GameUAVWalls {
    UAVMap map;
    UAVCanvas canvas;
    
    private Rectangle wall;
    
    private final int numEdges = 4;
    private Line2D [] edges = new Line2D.Double[numEdges];
    private Line2D [] dWall;
    
    /*
     * GameUAVWalls
     * param: map, canvas
     * returns: GameUAVWalls
     * function: constructor
     */
    GameUAVWalls(UAVMap map, UAVCanvas canvas) {
        this.map = map;
        this.canvas = canvas;
        
        wall = map.getBounds();
        
        edges[0] = new Line2D.Double(wall.x + wall.width, wall.y - wall.height,
                                     wall.x + wall.width, wall.y);
        edges[1] = new Line2D.Double(wall.x + wall.width, wall.y, 
                                     wall.x, wall.y);
        edges[2] = new Line2D.Double(wall.x, wall.y,
                                     wall.x, wall.y - wall.height);
        edges[3] = new Line2D.Double(wall.x, wall.y - wall.height,
                                     wall.x + wall.width, wall.y - wall.height);
    }
    
    /*
     * draw
     * param: Graphics2D on which to draw this instance
     * returns: N/A
     * function: draw method
     */
    public void draw(Graphics2D g) {
        dWall = canvas.translateEdgesActualtoMovingDisplay(edges);
        g.setColor(Color.CYAN);
        g.setStroke(new BasicStroke(4));
        for (int i = 0; i < numEdges; i++) {
            g.drawLine((int) dWall[i].getX1(), (int) dWall[i].getY1(),
                       (int) dWall[i].getX2(), (int) dWall[i].getY2());
        }
    }
    
    /**********************************************
     ************** Set & Get Methods *************
     **********************************************/
    public Point getLocation() {
        return (Point) wall.getLocation().clone();
    }

    public int getWidth() {
        return wall.width;
    }

    public int getHeight() {
        return wall.height;
    }
    
    /*
     * checkPlaneCollision
     * param: plane
     * returns: whether or not the plane collided with the wall
     * function: checks to see if the plane collided with the wall
     */
    protected boolean checkPlaneCollision(GameUAVPlane p) {
        Line2D [] planeEdges = p.getDisplayLineSegments();
        for (int i = 0; i< p.numEdges; i++) {
            for (int j = 0; j < numEdges; j++) {
                if (planeEdges[i].intersectsLine(dWall[j])) {
                    p.crashPlane();
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * nearby
     * param: location, distance
     * returns: whether or not the given location is within the distance 
     *          given from the walls
     * function: tests to see if the given location is nearby 
     *           (defined as within the given distance from the wall)
     */
    protected boolean nearby(Point2D point, int dist) {
        for (int i = 0; i< numEdges; i++) {
            if (edges[i].ptSegDist(point) < dist) {
                return true;
            }
        }
        return false;
    }

    /*
     * directionTo
     * param: plane's center, distance
     * returns: an integer which represents the closest wall
     * function: Tests the given point to see which wall it's closest to, 
     *           if the point is within the given distance from any 
     *           of the walls.
     * values: East Wall: 0;  NorthEast Corner: 1; North Wall: 2; 
     *         NorthWest Corner: 3; West Wall: 4; SouthWestCorner: 5; 
     *         South Wall: 6; SouthEast Corner: 7
     */
    protected int directionTo(Point2D center, int distance) {
        int d1 = -1;
        int d2 = -1;
        
        for (int i = 0; i< numEdges; i++) {
            if (edges[i].ptSegDist(center) < distance) {
                if (d1 < 0) {
                    d1 = 2 * i;
                } else {
                    d2 = 2 * i;
                }
            }
        }
        if (d2 != -1) {
            d1 = ((d1 == 0) && (d2 == 3)) ? 7: d1 + 1;
        }
        return d1;
    }
}
