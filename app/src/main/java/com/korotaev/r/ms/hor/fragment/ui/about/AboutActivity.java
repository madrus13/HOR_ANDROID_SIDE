package com.korotaev.r.ms.hor.fragment.ui.about;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.korotaev.r.ms.hor.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AboutFragment.newInstance())
                    .addToBackStack("myStack")
                    .commitNow();
        }
    }
}
