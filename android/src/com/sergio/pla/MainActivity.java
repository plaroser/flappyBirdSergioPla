package com.sergio.pla;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
    public static final String PREF_SONIDO = "sonido";

    private Button button1;
    private ToggleButton toggleButtonSonido;
    private SharedPreferences.Editor editor;
    private boolean haySonido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        toggleButtonSonido = findViewById(R.id.toggleButtonSonido);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.contains(PREF_SONIDO)) {
         haySonido = prefs.getBoolean(PREF_SONIDO, true);
        }else{
            editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
            editor.putBoolean(PREF_SONIDO, toggleButtonSonido.isChecked());
            editor.apply();
        }

        final Intent i = new Intent(this, AndroidLauncher.class);

        toggleButtonSonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putBoolean(PREF_SONIDO, toggleButtonSonido.isChecked());
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

}
