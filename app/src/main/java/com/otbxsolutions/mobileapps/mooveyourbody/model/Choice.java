package com.otbxsolutions.mobileapps.mooveyourbody.model;

/**
 * Created By: Neelan Joachimpillai
 * Date: 12/1/2016
 * Note: This is the Choice class for the instructions activity.
 */
public class Choice {
    private String mText;
    private int mNextPage;

    public Choice(String text, int nextPage) {
        mText=text;
        mNextPage=nextPage;
    }

    public int getNextPage() {
        return mNextPage;
    }

    public void setNextPage(int nextPage) {
        mNextPage = nextPage;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
