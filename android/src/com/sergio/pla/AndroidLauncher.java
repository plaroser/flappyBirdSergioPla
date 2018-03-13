package com.sergio.pla;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.sergio.pla.dataBase.FeedReaderDbHelper;
import com.sergio.pla.game.SergioPlaGame;
import com.sergio.pla.utils.GameCallBack;

import static com.sergio.pla.MainActivity.PREF_SOUND;
import static com.sergio.pla.dataBase.FeedReaderDbHelper.COLUMN_NAME_SCORE;
import static com.sergio.pla.dataBase.FeedReaderDbHelper.TABLE_NAME;

public class AndroidLauncher extends AndroidApplication implements GameCallBack {
    private Intent intentVolverAAndroid;
    private boolean isSound;
    private FeedReaderDbHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mDbHelper = new FeedReaderDbHelper(getApplicationContext());
        isSound = prefs.getBoolean(PREF_SOUND, false);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new SergioPlaGame(this), config);
        Gdx.app.log("Pref", "Sound is " + isSound);
        intentVolverAAndroid = new Intent(this, MainActivity.class);
    }

    @Override
    public void callBack() {
        startActivity(intentVolverAAndroid);
    }

    @Override
    public boolean isSound() {
        return isSound;
    }

    @Override
    public void setHighScore(int score) {
        db = mDbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);

        db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_SCORE, score);

        db.insert(TABLE_NAME, null, values);

    }

    @Override
    public int getHighScore() {
        db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.getCount() > 0) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }


}
