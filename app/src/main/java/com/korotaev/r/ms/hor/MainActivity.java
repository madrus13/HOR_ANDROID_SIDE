package com.korotaev.r.ms.hor;


import android.app.LoaderManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.korotaev.r.ms.hor.AppHelpers.ListViewLoader;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.IntentService.CmdService;
import com.korotaev.r.ms.hor.IntentService.SrvCmd;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.fragment.ui.about.AboutFragment;
import com.korotaev.r.ms.hor.fragment.ui.achievment.AchievmentFragment;
import com.korotaev.r.ms.hor.fragment.ui.chat.ChatFragment;
import com.korotaev.r.ms.hor.fragment.ui.help.HelpFragment;
import com.korotaev.r.ms.hor.fragment.ui.request.RequestFragment;
import com.korotaev.r.ms.hor.fragment.ui.settings.SettingsFragment;
import com.korotaev.r.ms.testormlite.data.ActivityActions;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;
import com.korotaev.r.ms.testormlite.data.Entity.TLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>,ServiceConnection, View.OnClickListener {

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
                        sendComandToIntentService(SrvCmd.CMD_EntitySyncReq);
                    }


                    break;
                case SrvCmd.CMD_EntitySyncResp:
                    Bundle data = msg.getData();
                    ViewHelper.showProgress(MainActivity.this,mMainView, mProgressView,false );
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }


    public  void initViews() {
        mProgressView = findViewById(R.id.main_activity_progress);
        mMainView = findViewById(R.id.main_layout);
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);
        drawer = findViewById(R.id.drawer_layout);
    }

    public void oOnClickListenerInit()
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
        initViews();
        oOnClickListenerInit();


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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(MainActivity.this);
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
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

        if (id == R.id.nav_your_help) {
            fragmentClass = HelpFragment.class;
        } else if (id == R.id.nav_chat) {
            fragmentClass = ChatFragment.class;
        } else if (id == R.id.nav_settings) {
           // startActivity(new Intent(MainActivity.this, SettingActivity.class));
            fragmentClass = SettingsFragment.class;
        } else if (id == R.id.nav_requests) {
            fragmentClass = RequestFragment.class;
        } else if (id == R.id.nav_achievments) {
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
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (fragment!=null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
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
            sendComandToIntentService(SrvCmd.CMD_RegisterIntentServiceClientReq);
        }
    }


    public void sendComandToIntentService(int command)
    {
        // We want to monitor the service for as long as we are
        // connected to it.
        try {
            Message msg = Message.obtain(null, command);
            msg.replyTo = mMessenger;
            mService.send(msg);
        } catch (RemoteException e) {
            Toast.makeText(MainActivity.this, R.string.remote_service_crashed,
                    Toast.LENGTH_SHORT).show();
            ViewHelper.showProgress(MainActivity.this,mMainView, mProgressView,false );
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View view) {

    }
}
