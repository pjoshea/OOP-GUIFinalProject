/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVMouseManager
 * Purpose: Every time the timer ticks, the mouse manager looks at the current 
 *          graphical state and then tells the cursor how to look.
 */

package uavcontrols5;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.geom.Point2D;

public class GameUAVMouseManager {
    private static int FAR_DIST = 300;
    private static int MID_DIST = 150;
    
    UAVMap map;
    UAVCanvas canvas;
    
    /*
     * GameUAVMouseManager
     * param: map, canvas
     * returns: GameUAVMouseManager
     * function: constructor
     */
    GameUAVMouseManager(UAVMap m, UAVCanvas c) {
        map = m;
        canvas = c;
        canvas.setCursor(GameUAVCursors.Menu.getCursor());
    }
    
    /*
     * tick
     * param: N/A
     * returns: N/A
     * function: checks to make sure that the current cursor is correct
     */
    public void tick() {
        GameUAVGraphicalState s = canvas.getCurrentState();
        if (s.type.isA("Game")) {
            if (s.type == GameUAVScreens.ActiveGame) {
                if (((GameUAVActiveGameState) s).aimMode) {
                    canvas.setCursor(GameUAVCursors.Aim.getCursor());
                } else {
                    GameUAVPlayer player = map.getPlayer();
                    Point2D playerLoc = player.center;
                    Point mouse = MouseInfo.getPointerInfo().getLocation();
                    Point2D mouseLoc = new Point2D.Double(
                                      mouse.x - canvas.getLocationOnScreen().x, 
                                      mouse.y - canvas.getLocationOnScreen().y);
                    mouseLoc = canvas.translateMovingDisplaytoActual(mouseLoc);
                    
                    double mouseDir = Math.atan2((int) (playerLoc.getY() - mouseLoc.getY()), 
                                                 (int) (mouseLoc.getX() - playerLoc.getX()));
                    mouseDir += (mouseDir < 0) ? 2 * Math.PI: 0;
                    
                    if (playerLoc.distance(mouseLoc) >= FAR_DIST) {
                        canvas.setCursor(GameUAVCursors.Far.turnCursor(mouseDir));
                    } else if (playerLoc.distance(mouseLoc) >= MID_DIST) {
                        canvas.setCursor(GameUAVCursors.Mid.turnCursor(mouseDir));
                    } else {
                        canvas.setCursor(GameUAVCursors.Near.turnCursor(mouseDir));
                    }
                }
            }
        } else {
            canvas.setCursor(GameUAVCursors.Menu.getCursor());
        }
    }
}
