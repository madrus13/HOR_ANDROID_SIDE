package com.korotaev.r.ms.hor;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Build;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>,ServiceConnection, View.OnClickListener {


    /** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;
    /**Target we publish for clients to send messages to IncomingHandler */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private View mProgressView;
    private View mMainView;
    private MyDBHelper myDBHelper = new MyDBHelper(this);

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            myDBHelper.getHelper().addLog ("test", "MA->handleMessage->" + msg.what);

            msg.getData();

            switch (msg.what) {
                case SrvCmd.CMD_RegisterIntentServiceClientResp:
                    showProgress(true);
                    //Toast.makeText(MainActivity.this, "Инициация синхронизации", Toast.LENGTH_SHORT).show();
                    sendComandToIntentService(SrvCmd.CMD_EntitySyncReq);
                    break;
                case SrvCmd.CMD_EntitySyncResp:
                    Bundle data = msg.getData();
                    Toast.makeText(MainActivity.this, "Обновление завершено", Toast.LENGTH_SHORT).show();
                    showProgress(false);
                    break;

                default:
                    super.handleMessage(msg);
            }
        }
    }
    private String currentToken = "";
    private List<Requesttype> requesttypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //myDBLogger.addLog ("INFO", "main->start");
        mProgressView = (View) findViewById(R.id.main_activity_progress);
        mMainView = (View) findViewById(R.id.main_layout);

        Toast.makeText(this, R.string.SuccessSignIn ,Toast.LENGTH_LONG).show();

        Intent i = new Intent(this, CmdService.class);
        bindService(i,  MainActivity.this, Context.BIND_AUTO_CREATE);

        currentToken = Preferences.loadObjInPrefs(this,Preferences.SAVED_Session);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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

            showProgress(false);
        }
        else if (id == R.id.nav_view_app_logs) {
            ArrayList<String> data = new ArrayList<String>();

            List<TLog> tlogs = myDBHelper.getHelper().getAllTLog();

            for (TLog el: tlogs
                 ) {
                data.add(el.getDate() +": " + el.getName() + el.getType() );
            }

            Intent intent = new Intent(MainActivity.this, ListViewLoader.class);
            intent.putExtra(ActivityActions.EXTRA_TITLE_LIST, R.string.SelectRegion);
            intent.putStringArrayListExtra(ActivityActions.EXTRA_DATA_LIST, data);
            intent.putExtra(ActivityActions.EXTRA_SELECT_MODE_CHOICE_TYPE, ListView.CHOICE_MODE_SINGLE);
            startActivityForResult(intent, ActivityActions.Pick_One_Item);
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
            // Вставляем фрагмент, заменяя текущий фрагмент
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
            // Выделяем выбранный пункт меню в шторке
            item.setChecked(true);
            // Выводим выбранный пункт в заголовке
            setTitle(item.getTitle());
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
            mMainView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mMainView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        // TODO Auto-generated method stub
        myDBHelper.getHelper().addLog ("INFO", "main->onServiceConnected" );
        if (service!=null) {
           // myDBLogger.addLog (APP_TAG_CODE, "main->onServiceConnected->" + service.toString()  );
            mService = new Messenger(service);

        }
        sendComandToIntentService(SrvCmd.CMD_RegisterIntentServiceClientReq);


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
            showProgress(false);
        }

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
       // showProgress(false);
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
