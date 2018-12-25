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
 * @author GreimuL
 */
public class GameModel implements ModelInterface {
    private int score;
    private boolean ckStart;
    private boolean gameEnd;
    private double time;
    private double remainTime;
    private double tmptime;
    private final double timelimit = 3000;
    private TimerThread timer;
    private String ansStr;
    private String inputStr;
    private int ckPivot;
    private int level;
    ViewInterface view;
    
    public GameModel(ViewInterface view){
        gameEnd = false;
        score = 0;
        ckStart = false;
        time = 0;
        remainTime = timelimit;
        ansStr = "";
        inputStr = "";
        ckPivot = 0;
        level = 1;
        this.view = view;
        view.setInitial();
        view.setScore(score);
        view.disableButtons();
        view.setTimer(0.0);
        view.setNumber("");
    }
    public void start(){
        if(gameEnd == true){
            score = 0;
        }
        inputStr = "";
        gameEnd = false;
        ckStart = false;
        ckPivot = 0;
        remainTime = timelimit;
        view.setScore(score);
        genStr();
        view.setNumber(ansStr);
        view.disableButtons();
        view.setTimer(remainTime);
        view.setReady();
        timer = new TimerThread();
        timer.start();
    }
    private void go(){
        view.setNumber("");
        ckStart = true;
        remainTime = timelimit;
        view.enableButtons();
        view.setTimer(remainTime);
        view.setGo();
        timer = new TimerThread();
        timer.start();
    }
    public void btPressed(int x){
        //System.out.println("pressed!");
        inputStr += x;
        view.setDisplay(inputStr);
        if(ckStart==true){
            ckAns(x);
        }
    }
    private void end(){
        timer.interrupt();
        ckStart = false;
        gameEnd = true;
        level = 1;
        ckPivot = 0;
        time = 0;
        view.disableButtons();
        view.setTimer(0.0f);
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
                timer.interrupt();
                nextLevel();
                ckStart = false;
            }
            //System.out.println(ckPivot);
        }
        else{
            //fail...
            end();
        }
    }
    private void nextLevel(){
        level++;
        score++;
        ckPivot = 0;
        view.setScore(score);
        genStr();
        start();
    }
    
    class TimerThread extends Thread{
        public void run(){
            try{
                tmptime = System.currentTimeMillis();
                while(!Thread.currentThread().isInterrupted()){
                    Thread.sleep(10);
                    time = System.currentTimeMillis() - tmptime;
                    remainTime = timelimit - time;
                    //set view
                    view.setTimer(remainTime/1000);
                    if(remainTime <=0){
                        break;
                    }
                }
                if(ckStart==false){
                    go();
                }
                else{     
                    end();
                }
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
