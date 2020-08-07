/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Enum: GameUAVScreens
 * Purpose: Provides a type to the subclasses of the GameUAVGraphicalState
 *          class. Provides a method to create an instance of a
 *          subclass without knowing the specific type of that subclass. 
 *          Also allows for a grouping of specific graphical states, for 
 *          a convenient shorthand in certain conditional statements.
 */

package uavcontrols5;

public enum GameUAVScreens {
    ActiveGame              ("ActiveGame", ScreenType.Game, (UAVMap map, UAVCanvas canvas) -> { return new GameUAVActiveGameState(map, canvas);}),
    PausedGame              ("PausedGame", ScreenType.Game, (UAVMap map, UAVCanvas canvas) -> { return new GameUAVPauseState(map, canvas);}),
    CompletedGame           ("CompletedGame", ScreenType.Game, (UAVMap map, UAVCanvas canvas) -> { return new GameUAVGameOverState(map, canvas);}),
    MainMenu                ("MainMenu", ScreenType.Menu, (UAVMap map, UAVCanvas canvas) -> { return new GameUAVMainMenuState(map, canvas);}),
    ScoresMenu              ("ScoresMenu", ScreenType.Menu, (UAVMap map, UAVCanvas canvas) -> { return new GameUAVScoresMenuState(map, canvas);}),
    ControlsMenu            ("ControlsMenu", ScreenType.Menu, (UAVMap map, UAVCanvas canvas) -> { return new GameUAVControlsMenuState(map, canvas);}),
    QuitGameDialog          ("QuitGameDialog", ScreenType.Dialog, (UAVMap map, UAVCanvas canvas) -> { return new GameUAVQuitDialogState(map, canvas);}),
    ExitApplicationDialog   ("ExitApplicationDialog", ScreenType.Dialog, (UAVMap map, UAVCanvas canvas) -> { return new GameUAVExitDialogState(map, canvas);});
    
    /*
     * ScreenType
     * enumerates: types of graphical states
     * purpose: useful shorthand for certain generic statements
     */
    public enum ScreenType {
        Game       (0),
        Menu       (1), 
        Dialog     (2);
        
        private final int n;
        
        ScreenType(int num) {
            n = num;
        }
    };
    
    /*
     * Constructor
     * purpose: allows the program to store a function call as a member of 
     *          an enum constant, which is useful for polymorphism
     */
    interface Constructor {
        GameUAVGraphicalState construct(UAVMap m, UAVCanvas c);
    }
    
    String name;
    ScreenType type;
    Constructor c;
    
    /*
     * GameUAVScreens
     * param: name of the enum constant, screenType, means of constructing 
     *        an instance of the specific enum constant
     * returns: GameUAVScreens
     * function: constructor
     */
    GameUAVScreens(String name, ScreenType type, Constructor c) {
        this.name = name;
        this.type = type;
        this.c = c;
    }
    
    /*
     * isA
     * param: String representation of the ScreenType 
     * returns: boolean 
     * function: returns whether or not this specific enum constant belongs to 
     *           the same ScreenType as the ScreenType whose String 
     *           representation was passed as an argument
     */
    protected boolean isA(String name) {
        switch (name) {
            case "Game":
                return this.type.equals(ScreenType.Game);
            case "Menu":
                return this.type.equals(ScreenType.Menu);
            case "Dialog":
                return this.type.equals(ScreenType.Dialog);
            default:
                return false;
        }
    }
    
    /*
     * getInstanceOf
     * param: map, canvas
     * returns: GameUAVGraphicalState 
     * function: returns an instance of the graphical state that the
     *           enum constant is associated with
     */
    GameUAVGraphicalState getInstanceOf(UAVMap map, UAVCanvas canvas) {
        return c.construct(map, canvas);
    }
}
