package com.otbxsolutions.mobileapps.mooveyourbody.model;

/**
 * Created By: Neelan Joachimpillai
 * Date: 12/1/2016
 * Note: This is the Arrow class for the instructions activity.
 */
public class Arrow {
    private enum direction {
        UP, DOWN, RIGHT, LEFT;
        public static direction getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private enum color {
        RED, BLUE, GREEN, YELLOW, BLACK, ORANGE, WHITE, BROWN;
        public static color getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private direction arrowDirection;
    private color arrowColor;
    private boolean activeArrow;
    private double x,  y;

    public Arrow() {
        generateArrow();
    }

    public Arrow(String dir, String col) {
        arrowDirection = direction.valueOf(dir.toUpperCase());
        arrowColor = color.valueOf(col.toUpperCase());
        activeArrow = true;
    }

    public void generateArrow() {
        arrowDirection = direction.getRandom();
        arrowColor= color.getRandom();
        activeArrow= true;
    }

    public boolean isActiveArrow() {
        return activeArrow;
    }

    public void resetArrow() {
        this.activeArrow = true;
    }

    public void setDirection (String dir) {
        switch(dir.toUpperCase()) {
            case "UP":
                arrowDirection = direction.UP;
                break;
            case "DOWN":
                arrowDirection = direction.DOWN;
                break;
            /*
            case "FORWARD":
                arrowDirection = direction.FORWARD;
                break;
            case "BACKWARD":
                arrowDirection = direction.BACKWARD;
                break;*/
            case "RIGHT":
                arrowDirection = direction.RIGHT;
                break;
            case "LEFT":
                arrowDirection = direction.LEFT;
                break;
            default:
                arrowDirection = arrowDirection;
        }
    }

    public String getDirection() {
        return arrowDirection.toString();
    }

    public int getRotation() {
        switch(arrowDirection.toString()){
            case "UP":
                return 0;
            case "RIGHT":
                return 90;
            case "LEFT":
                return -90;
            case "DOWN":
                return 180;
            default:
                return 0;
        }
    }

    public void setColor(String col){
        arrowColor = color.valueOf(col.toUpperCase());
    }

    public String getColor(){
        return arrowColor.toString();
    }

    // The points the arrow is worth, is equivalent to its color.
    public int getPoints(){

        switch(arrowColor.toString()){
            case "WHITE":
                return -2;
            case "BROWN":
                return 4;
            case "BLACK":
                return 3;
            case "RED":
                return 2;
            case "ORANGE":
                return 2;
            case "GREEN":
                return 2;
            case "BLUE":
                return 2;
            case "YELLOW":
                return 2;
            default:
                return 0;
        }
    }

    public String getImageFileName(){
        return "arrow_"+ arrowColor.toString().toLowerCase();
    }
}
