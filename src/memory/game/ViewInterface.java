/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memory.game;

/**
 *
 * @author 민경효
 */
public interface ViewInterface {
    public void setReady();
    public void setGo();
    public void setGameOver();
    public void setInitial();
	    
    public void setNumber(String num);
    public void setTimer(double time);
    public void disableButtons();
    public void enableButtons();
    public void setScore(int score);
}
