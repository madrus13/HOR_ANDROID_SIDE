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
import com.korotaev.r.ms.hor.fragment.ui.active_request_list.ActiveRequestListFragment;
import com.korotaev.r.ms.hor.fragment.ui.chat.ChatFragment;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;

import static com.korotaev.r.ms.hor.AppHelpers.FileHelper.MAPKIT_API_KEY;

public class RequestOnMapFragment extends Fragment implements ServiceActivity, UserLocationObjectListener {

    private RequestOnMapViewModel mViewModel;

    private final Point TARGET_LOCATION = new Point(59.945933, 30.320045);
    private MapView mapView;
    private UserLocationLayer userLocationLayer;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new RequestOnMapFragment()).commit();

                    mapView.getMap().move(
                            new CameraPosition(TARGET_LOCATION, 14.0f, 0.0f, 0.0f),
                            new Animation(Animation.Type.SMOOTH, 5),
                            null);
                    MapKitFactory.getInstance().onStart();
                    mapView.onStart();

                    return true;
                case R.id.navigation_active_request:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new ActiveRequestListFragment()).commit();
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

    @Override
    public void onStart() {
        super.onStart();
    }

    public static RequestOnMapFragment newInstance() {
        return new RequestOnMapFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        MapKitFactory.setApiKey(MAPKIT_API_KEY);
        MapKitFactory.initialize(getContext());       
		View v =  inflater.inflate(R.layout.request_on_map_fragment, container, false);
        super.onCreate(savedInstanceState);

        mapView = (MapView) getActivity().findViewById(R.id.mapview);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RequestOnMapViewModel.class);
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

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }
}
