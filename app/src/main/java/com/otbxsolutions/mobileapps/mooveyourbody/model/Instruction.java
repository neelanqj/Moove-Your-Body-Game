package com.otbxsolutions.mobileapps.mooveyourbody.model;

/**
 * Created By: Neelan Joachimpillai
 * Date: 12/1/2016
 * Note: This is the Instruction class for the instructions activity.
 */
public class Instruction {
    private Page[] mPages;

    public Instruction() {
        mPages = new Page[3];

        mPages[0] = new Page(
                "Instructions:\n The purpose of this game is to move your android device in a controlled forceful manner in the direction of the arrow in order to obtain as many points as possible in the alloted time. Each game lasts 3 minutes.",
                new Choice("Points Page", 2),
                new Choice("Next", 1));

        mPages[1] = new Page(
                "Arrow colors have a different amount of points. You want to move in the opposite direction of arrows that take away points, and in the same direction of arrows that give you points, accelerating in the wrong direction of an arrow, makes you loose 1 point. Watch out for the pull back after moving in the proper direction, its a major cause of lost points!",
                new Choice("Previous", 0),
                new Choice("Next", 2));

        mPages[2] = new Page(
                "The points are as follows: \nWhite arrows are -2 points.\nOrange, green, blue, red and yellow arrows are +2 points.\nBrown arrows are +4 points.\nBlack arrows are +3 points.\n",
                new Choice("Previous", 1));

    }

    public Page getPage(int pageNumber){
        return mPages[pageNumber];
    }
}
