/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memory.game;

import java.util.Random;
import memory.game.ModelInterface;
/**
 *
 * @author JUNG
 */
public class GameModel implements ModelInterface {
    private int score;
    private boolean ckStart;
    private double time;
    private String ansStr;
    private int ckPivot;
    private int level;
    ViewInterface view;
    
    public GameModel(ViewInterface view){
        score = 0;
        ckStart = false;
        time = 0;
        ansStr = "";
        ckPivot = 0;
        level = 1;
        this.view = view;
        view.setInitial();
        //set score,string,time to view
    }
    public void start(){
        ckStart = true;
        view.setScore(score);
        genStr();
        view.disableButtons();
        view.setReady();
    }
    public void btPressed(int x){
        if(ckStart==true){
            ckAns(x);
        }
    }
    
    private void end(){
        ckStart = false;
        level = 1;
        ckPivot = 0;
        time = 0;
        view.setGameOver();
    }
    private void genStr(){
        ansStr = "";
        Random random = new Random();
        for(int i=0;i<level;i++){
            ansStr += random.nextInt(9)+1;
        }
        //set view
    }
    private void ckAns(int x){
        if(ansStr.charAt(ckPivot)==(x+'0')){
            //correct!
            ckPivot++;
            if(ckPivot == ansStr.length()){
                nextLevel();
                ckStart = false;
            }
        }
        else{
            //fail...
            end();
        }
    }
    private void nextLevel(){
        level++;
        score++;
        view.setScore(score);
        genStr();
        ckStart = true;
    }
    
    class TimerThread extends Thread{
        public void run(){
            try{
                while(true){
                    Thread.sleep(100);
                    //set view
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
