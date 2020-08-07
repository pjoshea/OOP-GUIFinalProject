/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVControlsMenuState
 * Extends: GameUAVGraphicalState
 * Purpose: Provides the controls menu state, which contains information on how 
 *          to control the game.
 */

package uavcontrols5;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

class GameUAVControlsMenuState extends GameUAVGraphicalState {
    private final Font titleFont = new Font(Font.DIALOG, Font.PLAIN, 25);
    private final Font controlFont = new Font(Font.DIALOG, Font.PLAIN, 16);
    private final Font spaceBarFont = new Font(Font.DIALOG, Font.BOLD, 20);
    private static final String DEF_TITLE = "Controls";

    private Rectangle spaceBar;
    private final String spaceBarText = "SPACE";
    private BufferedImage aimCursor = GameUAVCursors.getIcon(GameUAVCursors.Aim);
    private final String [] spaceBarControlsText = {
                    "Hold down the space bar to enter aim mode.",
                    "In aim mode, everything moves more slowly,",
                    "and the plane will continue at the same speed",
                    "in the same direction as when it entered aim mode,",
                    "so the mouse can be used to aim at planes without",
                    "changing the direction that the plane is moving."
    };

    
    private BufferedImage nearCursor = GameUAVCursors.getIcon(GameUAVCursors.Near);
    private BufferedImage midCursor = GameUAVCursors.getIcon(GameUAVCursors.Mid);
    private BufferedImage farCursor = GameUAVCursors.getIcon(GameUAVCursors.Far);
    private final String [] mouseControlsText = {
        "Use the mouse to direct the plane.",
        "The plane will move towards the pointer.",
        "The distance between the plane and the mouse",
        "determines the speed at which the plane moves.",
        "",
        "The color of the cursor determines if the plane",
        "is accelerating, slowing or maintaining speed.",
        "",
        "The cursor is yellow when the plane is slowing;",
        "green when accelerating to or maintaining a ",
        "speed less than the maximum; and blue when ",
        "accelerating to or maintaining the plane's maximum speed."
    };    

    private GameUAVReturnButton returnBtn;

    
    /*
     * GameUAVControlsMenuState
     * param: map, canvas
     * returns: GameUAVControlsMenuState
     * function: constructor
     */
    public GameUAVControlsMenuState(UAVMap m, UAVCanvas c)
    {
        super(m, c, DEF_TITLE, GameUAVScreens.ControlsMenu);
        spaceBar = new Rectangle((int) (.3 * canvas.getWidth()) - 150,
                                 canvas.getHeight()/4 - 25, 300, 50);

        returnBtn = new GameUAVReturnButton(map, this,
                                      new Point2D.Double(3 * c.getWidth() / 4,
                                                         7 * c.getHeight()/ 8),
                                      new Dimension(100, 50));
    }

    /*
     * draw
     * param: Graphics2D on which to draw
     * returns: N/A
     * function: draw method
     */
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(1));
        g.setFont(titleFont);
        FontMetrics tfm = g.getFontMetrics();
        int hAlign = (canvas.getWidth() - tfm.stringWidth(title))/2;
        g.drawString(title, hAlign, 30);

        g.setStroke(new BasicStroke(2));
        g.draw(spaceBar);
        g.setStroke(new BasicStroke(1));
        g.setFont(spaceBarFont);
        FontMetrics fm = g.getFontMetrics();
        int hz = fm.stringWidth(spaceBarText) / 2;
        int vt = fm.getAscent()/2;
        g.drawString(spaceBarText, (int) (.3 * canvas.getWidth()) - hz,
                     canvas.getHeight()/4 + vt);
        vt += 30;
        g.drawImage(aimCursor, (int) (.3 * canvas.getWidth()) - aimCursor.getWidth()/2,
                    canvas.getHeight()/4 + vt, canvas);

        g.setFont(controlFont);
        fm = g.getFontMetrics();
        vt = (int)(.4 * fm.getAscent() * spaceBarControlsText.length);
        for (int i =0; i< spaceBarControlsText.length; i++) {
            g.drawString(spaceBarControlsText[i],
                         (int) (.55 * canvas.getWidth()),
                         (int)(canvas.getHeight()/4 - vt
                               + 1.5 * (i * fm.getAscent())));
        }

        hz = nearCursor.getWidth()/2;
        vt = nearCursor.getHeight() + 30;
        g.drawImage(nearCursor, (int) (.3 * canvas.getWidth()) - hz,
                    (int) (.6 * canvas.getHeight()), canvas);
        g.drawImage(midCursor, (int) (.3 * canvas.getWidth()) - hz,
                    (int) (.6 * canvas.getHeight()) + vt, canvas);
        g.drawImage(farCursor, (int) (.3 * canvas.getWidth()) - hz,
                    (int) (.6 * canvas.getHeight()) + 2 * vt, canvas);

        for (int i = 0; i < mouseControlsText.length; i++) {
            g.drawString(mouseControlsText[i], (int) (.55 * canvas.getWidth()),
                      (int)(canvas.getHeight()/2 + 1.5 * (i * fm.getAscent())));
        }

        returnBtn.drawButton(g);
    }

    /*
     * accept
     * param: command String
     * returns: N/A
     * function: when the return button is pressed, the program returns 
     *           to the main menu
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
     * function: disables the return button
     */
    @Override
    public void deactivate() {
        returnBtn.setActive(false);
    }
}
