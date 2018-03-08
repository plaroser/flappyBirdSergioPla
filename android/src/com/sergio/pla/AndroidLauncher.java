package com.sergio.pla;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sergio.pla.game.SergioPlaGame;
import com.sergio.pla.utils.GameCallBack;

import static com.sergio.pla.MainActivity.PREF_SOUND;

public class AndroidLauncher extends AndroidApplication implements GameCallBack {
    private boolean isSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        isSound = prefs.getBoolean(PREF_SOUND, false);
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new SergioPlaGame(this), config);
        Gdx.app.log("Pref","Sound is "+ isSound);
    }

    @Override
    public void callBack() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public boolean isSound() {
        return isSound;
    }
}
