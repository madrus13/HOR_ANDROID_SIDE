package com.korotaev.r.ms.hor.IntentService;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.ViewHelper;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.fragment.ui.ServiceActivity;

import org.json.JSONObject;

import java.util.Map;

public class FcmListenerService extends FirebaseMessagingService implements ServiceActivity {
    private MyDBHelper myDBHelper = new MyDBHelper(this);

    static Messenger mService = null;
    Messenger mMessenger = new Messenger(new FcmListenerService.IncomingHandler());
    public  static boolean isServiceConnected;
    public  FcmListenerService() {


    }

    protected  class IncomingHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {

            msg.getData();
            if (msg.replyTo == mMessenger) {
                switch (msg.what) {
                    case SrvCmd.CMD_RegisterIntentServiceClientResp:
                        ViewHelper.sendComandToIntentService(
                                getBaseContext(),
                                mMessenger,
                                mService,
                                null,
                                null,
                                SrvCmd.CMD_GetMessageByUserRegionReq, null);

                        break;
                    default:
                        super.handleMessage(msg);
                }
            }

        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);

        String NOTIFICATION_CHANNEL_ID = "Channel";

        long pattern[] = {0, 1000, 500, 1000};

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Your Notifications",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.setDescription("");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(pattern);
            notificationChannel.enableVibration(true);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }

        // to diaplay notification in DND Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID);
            channel.canBypassDnd();
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(remoteMessage.getNotification().getBody())
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);


        mNotificationManager.notify(1000, notificationBuilder.build());

        if (isServiceConnected == false && mService == null) {
            Intent i = new Intent(this, CmdService.class);
            bindService(i, this, Context.BIND_AUTO_CREATE);
        }


        ViewHelper.sendComandToIntentService(
                this,
                mMessenger,
                mService,
                null,
                null,
                SrvCmd.CMD_GetMessageByUserRegionReq, null);


    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        myDBHelper.getHelper().addLog(SrvCmd.CODE_INFO,"FcmListenerService -newToken" + s );

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
    public void onServiceConnected(ComponentName componentName, IBinder service) {
        if (service!=null && mService == null) {
            mService = new Messenger(service);
            ViewHelper.sendComandToIntentService(
                    getApplicationContext(),
                    mMessenger,
                    mService,
                    null,
                    null,
                    SrvCmd.CMD_RegisterIntentServiceClientReq, null);

            isServiceConnected = true;
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
