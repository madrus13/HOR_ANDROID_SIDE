package com.korotaev.r.ms.hor.fragment.ui.request;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;
import com.korotaev.r.ms.hor.fragment.ui.chat.ChatFragment;

public class RequestFragment extends Fragment implements ServiceActivity {

    private RequestViewModel mViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                   // mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_active_request:
                    //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new RequestFragment()).commit();
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_common_chat:
                    //mTextMessage.setText(R.string.title_notifications);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new ChatFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    public static RequestFragment newInstance() {
        return new RequestFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View v =  inflater.inflate(R.layout.request_fragment, container, false);

        BottomNavigationView navigation = (BottomNavigationView) v.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RequestViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void initViews(View v) {

    }

    @Override
    public void OnClickListenerInit() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
