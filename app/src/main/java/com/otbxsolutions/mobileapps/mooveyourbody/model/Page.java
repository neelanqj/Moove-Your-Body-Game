package com.otbxsolutions.mobileapps.mooveyourbody.model;

/**
 * Created By: Neelan Joachimpillai
 * Date: 12/1/2016
 * Note: This is the Page class for the instructions activity.
 */
public class Page {
    private String mText;
    private Choice mChoice1;
    private Choice mChoice2;

    public boolean isFinal() {
        return mIsFinal;
    }

    public void setIsFinal(boolean isFinal) {
        mIsFinal = isFinal;
    }

    private boolean mIsFinal;

    public Page(String text, Choice choice1, Choice choice2){
        mText=text;
        mChoice1=choice1;
        mChoice2=choice2;
        mIsFinal=false;
    }

    public Page(String text, Choice choice1){
        mChoice1=choice1;
        mText=text;
        mIsFinal=true;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Choice getChoice1() {
        return mChoice1;
    }

    public void setChoice1(Choice choice1) {
        mChoice1 = choice1;
    }

    public Choice getChoice2() {
        return mChoice2;
    }

    public void setChoice2(Choice choice2) {
        mChoice2 = choice2;
    }
}
