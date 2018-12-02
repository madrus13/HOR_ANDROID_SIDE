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
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.korotaev.r.ms.hor.IntentService.CmdService;
import com.korotaev.r.ms.hor.IntentService.SrvCmd;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;

import java.util.List;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.APP_TAG_CODE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>,ServiceConnection, View.OnClickListener {


    /** Messenger for communicating with service. */
    Messenger mService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean mIsBound;
    /**Target we publish for clients to send messages to IncomingHandler */
    final Messenger mMessenger = new Messenger(new IncomingHandler());


    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            Log.e("test", "main->handleMessage->");

            msg.getData();

            switch (msg.what) {
                case SrvCmd.CMD_RegisterIntentServiceClientResp:
                    Toast.makeText(MainActivity.this, "Инициация синхронизации", Toast.LENGTH_SHORT).show();
                    sendComandToIntentService(SrvCmd.CMD_EntitySyncReq);

                case SrvCmd.CMD_EntitySyncResp:
                    Bundle data = msg.getData();
                    //data.setClassLoader(ElmaResponseAuthorise.class.getClassLoader());
                    //ElmaResponseAuthorise info = (ElmaResponseAuthorise)data.getParcelable(String.valueOf(SrvCmd.Auth_Response));
                    Toast.makeText(MainActivity.this, "Обновление завершено", Toast.LENGTH_SHORT).show();
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
        int id = item.getItemId();

        if (id == R.id.nav_your_help) {
            // Handle the camera action
        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, ActivitySettings.class));

        } else if (id == R.id.nav_requests) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        // TODO Auto-generated method stub
        Log.e(APP_TAG_CODE, "main->onServiceConnected" );
        if (service!=null) {
            Log.e(APP_TAG_CODE, "main->onServiceConnected->" + service.toString()  );
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
