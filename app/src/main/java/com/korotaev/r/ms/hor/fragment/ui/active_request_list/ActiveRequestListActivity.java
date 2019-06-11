package com.korotaev.r.ms.hor.fragment.ui.active_request_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.fragment.ui.active_request_list.ActiveRequestListFragment;

public class ActiveRequestListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_request_list_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ActiveRequestListFragment.newInstance())
                    .addToBackStack("myStack")
                    .commitNow();
        }
    }
}
