package com.korotaev.r.ms.hor.fragment.ui.fragment.ui.new_request;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.korotaev.r.ms.hor.R;


public class NewRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_request_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, NewRequestFragment.newInstance())
                    .addToBackStack("myStack")
                    .commitNow();
        }
    }
}
