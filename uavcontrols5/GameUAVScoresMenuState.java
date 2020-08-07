/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVScoresMenuState
 * Extends: GameUAVGraphicalState
 * Purpose: Displays all of the high scores from this copy of the program, 
 *          allows the user to return to the main menu when done.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

public class GameUAVScoresMenuState extends GameUAVGraphicalState 
                                    implements MouseWheelListener 
{
    private static final String DEF_TITLE = "High Scores";
    
    private final Font titleFont = new Font(Font.DIALOG, Font.PLAIN, 25);
    private final Font scoreFont = new Font(Font.DIALOG, Font.PLAIN, 20);
    private final double sBoxCompWidth = .875;
    private final double sBoxCompHeight = .8;
    private final Dimension rbtn = new Dimension(100, 40);
    private final int rBtnVoffset = 5;
    private final int rBtnHoffset = -100;
    
    private GameUAVReturnButton returnBtn;
    
    
    private GameUAVScore [] scores;
    private Rectangle scoreBox;
    
    private int pixelsPerLine;
    private int lines;
    private int topVisibleScore = 0;
    
    
    /*
     * GameUAVScoresMenuState
     * param: map, canvas
     * returns: GameUAVScoresMenuState
     * function: constructor
     */
    public GameUAVScoresMenuState(UAVMap m, UAVCanvas c) {
        super(m, c, DEF_TITLE, GameUAVScreens.ScoresMenu);
        scores = map.retrieveHighScores();
        scoreBox = new Rectangle((int) ((1.0 - sBoxCompWidth)/2 * c.getWidth()), 
                                 (int) ((1.0 - sBoxCompHeight)/2 * c.getHeight()), 
                                 (int) (sBoxCompWidth * c.getWidth()), 
                                 (int) (sBoxCompHeight * c.getHeight()));
        returnBtn = new GameUAVReturnButton(map, this,
                 new Point2D.Double(scoreBox.width + scoreBox.x + rBtnHoffset, 
                                    scoreBox.height + scoreBox.y + rBtnVoffset), 
                 rbtn);
        canvas.addMouseWheelListener(this);
    }
    
    /*
     * draw
     * param: Graphics2D
     * returns: N/A
     * function: draw method
     */
    @Override
    public void draw(Graphics2D g) {
        scoreBox = new Rectangle((int) ((1 - sBoxCompWidth)/2 * canvas.getWidth()), 
                                 (int) ((1 - sBoxCompHeight)/2 * canvas.getHeight()), 
                                 (int) (sBoxCompWidth * canvas.getWidth()), 
                                 (int) (sBoxCompHeight * canvas.getHeight()));
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1));        
        g.setFont(titleFont);
        FontMetrics tfm = g.getFontMetrics();
        int hAlign = (canvas.getWidth() - tfm.stringWidth(title))/2;
        g.drawString(title, hAlign, 30);
        
        g.setFont(scoreFont);
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(4));
        FontMetrics fm = g.getFontMetrics();
        pixelsPerLine = fm.getHeight();
        
        lines = (scoreBox.height - 20) / pixelsPerLine;
        int height = (int) scoreBox.getY();
        int h0 = (int) scoreBox.getX() + 50;
        int h1 = (int) scoreBox.getX() + 100 + 
                                      fm.stringWidth(scores[0].getPlace());
        int h2 = (int) scoreBox.getX() + scoreBox.width - 
                                      fm.stringWidth(scores[0].getScore()) - 25;
        
        //here we assume that the topVisibleScore will be positive, and will 
        //stop increasing before the screen displays no scores at all
        for (int place = topVisibleScore; (place - topVisibleScore) <= lines && place < scores.length; place++) {
            height += pixelsPerLine;
            g.drawString(scores[place].getPlace(), h0, height);
            g.drawString(scores[place].getUserName(), h1, height);
            g.drawString(scores[place].getScore(), h2, height);
        }
        
        returnBtn.drawButton(g, 
                new Point2D.Double(scoreBox.width + scoreBox.x + rBtnHoffset, 
                                   scoreBox.height + scoreBox.y + rBtnVoffset));
    }
    
    /*
     * accept
     * param: command
     * returns: N/A
     * function: when it receives the GameUAVReturnButton's command, the program
     *           switches to the main menu
     */
    @Override
    public void accept(String cmd) {
        switch (cmd) {
            case GameUAVReturnButton.cmd :
                canvas.goTo(GameUAVScreens.MainMenu);
                break;
        }
    }
    
    /*
     * deactivate
     * param: N/A
     * returns: N/A
     * function: deactivates the connected buttons and stops listening to 
     *           MouseWheelEvents
     */
    @Override
    public void deactivate(){
        returnBtn.setActive(false);
        canvas.removeMouseWheelListener(this);
    }
    
    /*
     * mouseWheelMoved
     * param: MouseWheelEvent
     * returns: N/A
     * function: when the user scrolls using the mouse wheel, the scores 
     *           move as if they were in a scroll pane
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (topVisibleScore + e.getWheelRotation() >= 0 && 
            ((topVisibleScore + e.getWheelRotation() + lines) < scores.length))
        {
            topVisibleScore += e.getWheelRotation();
            topVisibleScore = (topVisibleScore < 0) ? 0: topVisibleScore;
            canvas.redraw();
        }
    } 
}
