package com.korotaev.r.ms.hor.fragment.ui.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.korotaev.r.ms.hor.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commitNow();
        }
    }
}
