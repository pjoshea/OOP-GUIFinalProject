/* 
 * Programmer: Patrick O'Shea <poshea01>
 * Date: Thursday, October 8th, 2015
 * Program Name: UAVControls (v. 5)
 * Objective: Simulate a set of controls for a set of unmanned aerial 
 *     vehicles and create a setting which turns it into a game.
 * 
 * Class: GameUAVScore
 * Purpose: Groups information about a particular score, and allows limited 
 *          modification of the score's data.
 */

package uavcontrols5;

class GameUAVScore {
    private String user;
    private int place, score;
    private String placeText, scoreText;
    
    /*
     * GameUAVScore
     * param: rank, username, score
     * returns: GameUAVScore
     * function: constructor
     */
    GameUAVScore(int p, String u, int s) {
        place = p;
        placeText = addZeroesMakeString(p, 3);
        user = u; 
        score = s;
        scoreText = addZeroesMakeString(s, 8);
    }
    
    /*
     * addZeroesMakeString
     * param: number, desired length of final string
     * returns: addZeroesMakeString
     * function: converts a number to a string, inserting 0's in front
     *           to extend the String to the desired length
     */
    private String addZeroesMakeString(double n, int len) {
        String x = "";
        double digits = Math.floor(Math.log10(n));
        digits = (digits < 0) ? 0: digits; 
        for (int i = len - 1; i > digits; i--) {
            x += "0";
        }
        x+= (int) n;
        return x;
    }
    
/****************************************************************************
 **************************** Set & Get Methods *****************************
 ****************************************************************************/
    
    protected void setPlace(int p) {
        place = p;
    }
    
    protected String getPlace() {
        return placeText;
    }
    
    protected void setScore(int s) {
        score = s;
    }
    
    protected String getScore() {
        return scoreText;
    }
    
    protected void setUserName(String n) {
        user = n;
    }
    
    protected String getUserName() {
        return user;
    }
}
