package com.otbxsolutions.mobileapps.mooveyourbody.model;

/**
 * Created By: Neelan Joachimpillai
 * Date: 12/1/2016
 * Note: This is the Game class for the instructions activity.
 */
public class Game {
    private int points;
    private boolean state;

    // Constructor
    public Game(){
        state = true;
        points = 0;
    }

    public int getScore() {
        return points;
    }

    public boolean getState(){
        return state;
    }

    public void incrementScore(int point) {
        points = point + points;
        if (points < 0) points = 0;
    }
    public void incrementScore() {
        points++;
    }

    public void decrementScore(){
        if(points != 0) {
            points--;
        }
    }

    public void reset() {
        points = 0;
        state = true;
    }

    public void endGame() {
        state = false;
    }

    public String getCreatorName(){
        return "Neelan Joachimpillai";
    }
}
