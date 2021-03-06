package com.korotaev.r.ms.hor;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.korotaev.r.ms.hor.AppHelpers.ListViewLoader;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.IntentService.CmdService;
import com.korotaev.r.ms.hor.IntentService.SrvCmd;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;
import com.korotaev.r.ms.hor.fragment.ui.about.AboutFragment;
import com.korotaev.r.ms.hor.fragment.ui.achievment.AchievmentFragment;
import com.korotaev.r.ms.hor.fragment.ui.active_request_list.ActiveRequestListFragment;
import com.korotaev.r.ms.hor.fragment.ui.chat.ChatFragment;
import com.korotaev.r.ms.hor.fragment.ui.help.HelpFragment;
import com.korotaev.r.ms.hor.fragment.ui.request.RequestOnMapFragment;
import com.korotaev.r.ms.hor.fragment.ui.settings.SettingsFragment;
import com.korotaev.r.ms.testormlite.data.ActivityActions;
import com.korotaev.r.ms.testormlite.data.Entity.TLog;
import com.korotaev.r.ms.testormlite.data.Entity.User;
import com.yandex.runtime.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ServiceActivity, View.OnClickListener {

    Messenger mService = null;
    boolean mIsBound;
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private View mProgressView;
    private View mMainView;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private DrawerLayout drawer;

    private String currentToken = "";
    private MyDBHelper myDBHelper = new MyDBHelper(this);

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

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            msg.getData();

            switch (msg.what) {
                case SrvCmd.CMD_RegisterIntentServiceClientResp:

                    if (msg.replyTo == mMessenger)
                    {
                        ViewHelper.showProgress(MainActivity.this,mMainView, mProgressView,true );
                        ViewHelper.sendComandToIntentService(
                                MainActivity.this,
                                mMessenger,
                                mService,
                                mMainView,
                                mProgressView,
                                SrvCmd.CMD_EntitySyncReq, null);
                    }


                    break;
                case SrvCmd.CMD_EntitySyncResp:
                    ViewHelper.showProgress(MainActivity.this,mMainView, mProgressView,false );
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }

    public void initFcmToken()
    {
        User currentUser = Preferences.loadCurrentUserInfo(this);

        FirebaseInstanceId.getInstance().getInstanceId().
                addOnSuccessListener(
                MainActivity.this,
                        instanceIdResult -> {
                            String newToken = instanceIdResult.getToken();
                            myDBHelper.getHelper().addLog(SrvCmd.CODE_INFO,"initFcmToken -newToken" + newToken );

                        });

        if (currentUser!=null && currentUser.getRegion() > 0) {
            //Topic name equal on server side insertMessage code
            FirebaseMessaging.getInstance().subscribeToTopic("region_" + currentUser.getRegion().toString())
                    .addOnCompleteListener(task -> {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        myDBHelper.getHelper().addLog(SrvCmd.CODE_INFO,"initFcmToken - chat_message_region" + msg );
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    });
        }

    }


    public  void initViews(View v) {
        mProgressView = findViewById(R.id.main_activity_progress);
        mMainView = findViewById(R.id.main_layout);
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
    }


    public void OnClickListenerInit()
    {
        if (mFab!=null) {
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initViews(null);
        OnClickListenerInit();
        initFcmToken();

        ViewHelper.showProgress(MainActivity.this,mMainView, mProgressView,true );

        Intent i = new Intent(this, CmdService.class);
        bindService(i,  MainActivity.this, Context.BIND_AUTO_CREATE);

        currentToken = Preferences.loadObjInPrefs(this,Preferences.SAVED_Session);
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Snackbar snackbar = Snackbar
                .make(mMainView, "Welcome to HelpOnRoad", Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(MainActivity.this);
    }

    @Override
    protected void onPause() {
        Log.e("APP", "onPause");
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.e("APP", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("APP", "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        // Создадим новый фрагмент
        Fragment fragment = null;
        Class fragmentClass = null;

        int id = item.getItemId();

        //Hide keyboard on change navi item
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        if (id == R.id.nav_your_help) {
            fragmentClass = HelpFragment.class;
        } else if (id == R.id.nav_chat) {
            fragmentClass = ChatFragment.class;
        } else if (id == R.id.nav_settings) {
           // startActivity(new Intent(MainActivity.this, SettingActivity.class));
            fragmentClass = SettingsFragment.class;
        } else if (id == R.id.nav_requests_on_map) {
            fragmentClass = RequestOnMapFragment.class;
        } else if (id == R.id.nav_active_requests_list) {
            fragmentClass = ActiveRequestListFragment.class;
        }
        else if (id == R.id.nav_achievments) {
            fragmentClass = AchievmentFragment.class;
        }  else if (id == R.id.nav_about_program) {
            fragmentClass = AboutFragment.class;
        }
        else if (id == R.id.nav_signoff) {
            Preferences.saveObjInPrefs(MainActivity.this, Preferences.SAVED_AutoSignInState, "0");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

            ViewHelper.showProgress(MainActivity.this,mMainView, mProgressView,false );
        }
        else if (id == R.id.nav_view_app_logs) {

          start_activity_view_app_logs();
        }

        else if (id == R.id.nav_clear_log) {
            myDBHelper.getHelper().clearLog();
            myDBHelper.getHelper().clearMessage();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment!=null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commitNow();
            item.setChecked(true);
            setTitle(item.getTitle());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void start_activity_view_app_logs()
    {
        ArrayList<String> data = new ArrayList<String>();

        List<TLog> tlogs = myDBHelper.getHelper().getAllTLog();
        SimpleDateFormat dt = new SimpleDateFormat("hh:mm:ss");

        for (TLog el: tlogs
                ) {
            data.add(dt.format(el.getDate()) +": " + el.getName() + " : " + el.getType() );
        }

        Intent intent = new Intent(MainActivity.this, ListViewLoader.class);
        intent.putExtra(ActivityActions.EXTRA_TITLE_LIST, R.string.SelectRegion);
        intent.putStringArrayListExtra(ActivityActions.EXTRA_DATA_LIST, data);
        intent.putExtra(ActivityActions.EXTRA_SELECT_MODE_CHOICE_TYPE, ListView.CHOICE_MODE_SINGLE);
        startActivityForResult(intent, ActivityActions.Pick_One_Item);
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        if (service!=null && mService == null) {

            mService = new Messenger(service);
            ViewHelper.sendComandToIntentService(
                    MainActivity.this,
                    mMessenger,
                    mService,
                    mMainView,
                    mProgressView,
                    SrvCmd.CMD_RegisterIntentServiceClientReq, null);
        }
    }



    @Override
    public void onServiceDisconnected(ComponentName componentName) {
    }


    @Override
    public void onClick(View view) {

    }
}
