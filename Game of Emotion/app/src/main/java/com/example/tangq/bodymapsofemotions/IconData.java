/*
 * Author:       Qian Tang & Ching Man Lee
 * Application : Game of Emotion
 *
 * */

package com.example.tangq.bodymapsofemotions;

import android.view.View;


public class IconData {
    private String iconName;
    private View view;
    private int x,y;
    private boolean putFront,putBack;


    public IconData(View view) {
        this.view = view;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public boolean isPutFront() {
        return putFront;
    }

    public void setPutFront(boolean putFront) {
        this.putFront = putFront;
    }

    public boolean isPutBack() {
        return putBack;
    }

    public void setPutBack(boolean putBack) {
        this.putBack = putBack;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }


}
