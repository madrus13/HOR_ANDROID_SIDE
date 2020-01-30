package com.korotaev.r.ms.hor.fragment.ui.settings;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.korotaev.r.ms.hor.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .addToBackStack("myStack")
                    .commitNow();
        }
    }
}
