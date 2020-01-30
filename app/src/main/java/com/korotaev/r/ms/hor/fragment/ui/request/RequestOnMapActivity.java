package com.korotaev.r.ms.hor.fragment.ui.request;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import com.korotaev.r.ms.hor.R;

public class RequestOnMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_on_map_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RequestOnMapFragment.newInstance())
                    .addToBackStack("myStack")
                    .commitNow();
        }
    }
}
