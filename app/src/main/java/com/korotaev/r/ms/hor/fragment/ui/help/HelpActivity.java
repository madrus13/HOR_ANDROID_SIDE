package com.korotaev.r.ms.hor.fragment.ui.help;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.korotaev.r.ms.hor.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HelpFragment.newInstance())
                    .addToBackStack("myStack")
                    .commitNow();
        }
    }
}
