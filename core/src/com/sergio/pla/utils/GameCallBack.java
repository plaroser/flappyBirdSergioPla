package com.sergio.pla.utils;

/**
 * Created by Sergio Pla on 3/8/2018.
 */

public interface GameCallBack {
    void callBack();

    boolean isSound();

    void setHighScore(int score);

    int getHighScore();
}
