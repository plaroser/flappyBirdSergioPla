package com.sergio.pla;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.sergio.pla.dataBase.FeedReaderDbHelper;

import static com.sergio.pla.dataBase.FeedReaderDbHelper.TABLE_NAME;

public class MainActivity extends Activity {
    public static final String PREF_SOUND = "sonido";

    private Intent i;
    private ImageButton button1;
    private EditText editTextPuntuacionMaxima;
    private ToggleButton toggleButtonSonido;
    private SharedPreferences.Editor editor;
    private boolean haySonido;

    private FeedReaderDbHelper mDbHelper;
    private SQLiteDatabase db;
    private AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new FeedReaderDbHelper(getApplicationContext());
        button1 = findViewById(R.id.button);
        editTextPuntuacionMaxima = findViewById(R.id.editTextPuntuacionMaxima);
        toggleButtonSonido = findViewById(R.id.toggleButtonSonido);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editTextPuntuacionMaxima.setKeyListener(null);
        editTextPuntuacionMaxima.setText(String.valueOf(getHighScore()));

        i = new Intent(this, AndroidLauncher.class);
        if (prefs.contains(PREF_SOUND)) {
            haySonido = prefs.getBoolean(PREF_SOUND, false);
            toggleButtonSonido.setChecked(haySonido);
        }


        toggleButtonSonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putBoolean(PREF_SOUND, toggleButtonSonido.isChecked());
                editor.apply();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(i);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        editTextPuntuacionMaxima.setText(String.valueOf(getHighScore()));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setCancelable(false);
        builder.setView(inflater.inflate(R.layout.dialog_exit, null));
        alert = builder.create();
        alert.show();
        Button buttonSi = alert.findViewById(R.id.buttonSi);
        Button buttonNo = alert.findViewById(R.id.buttonNo);
        buttonSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.cancel();
            }
        });
    }

    /**
     * Devuelve la puntuacion maxima registrada
     *
     * @return puntuacion máxima
     */
    public int getHighScore() {
        db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst() || cursor.getCount() > 0) {
            return cursor.getInt(0);
        } else {
            return 0;
        }
    }

}
