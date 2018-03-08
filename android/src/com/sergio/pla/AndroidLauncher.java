package com.sergio.pla;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sergio.pla.game.SergioPlaGame;
import com.sergio.pla.utils.GameCallBack;

public class AndroidLauncher extends AndroidApplication implements GameCallBack {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new SergioPlaGame(this), config);
    }

    @Override
    public void callBack() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
