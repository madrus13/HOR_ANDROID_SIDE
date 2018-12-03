package com.korotaev.r.ms.hor.fragment.ui.request;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.korotaev.r.ms.hor.R;

public class RequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, RequestFragment.newInstance())
                    .commitNow();
        }
    }
}
