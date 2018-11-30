package com.korotaev.r.ms.hor.IntentService;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.korotaev.r.ms.hor.MainActivity;
import com.korotaev.r.ms.hor.R;

import java.util.ArrayList;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.APP_TAG_CODE;


public class CmdService extends IntentService {

    /** For showing and hiding our notification. */
    NotificationManager mNM;
    /** Keeps track of all current registered clients. */
    ArrayList<Messenger> mClients = new ArrayList<Messenger>();
    /** Holds last value set by a client. */
    int mValue = 0;


    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.e(APP_TAG_CODE, "Service->IncomingHandler->handleMessage : " + msg.what);


            switch (msg.what) {
                case SrvCmd.CMD_RegisterIntentServiceClientReq:
                    mClients.add(msg.replyTo);
                    Message answerMsg = Message.obtain(null, SrvCmd.CMD_RegisterIntentServiceClientResp);
                    answerMsg.replyTo = msg.replyTo;

                    for (Messenger item : mClients) {
                        try {
                            item.send(answerMsg);
                        } catch (RemoteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    break;
                case SrvCmd.CMD_UnregisterIntentServiceClientReq:
                    mClients.remove(msg.replyTo);
                    break;
                case SrvCmd.CMD_EntitySyncReq:
                    Log.e(APP_TAG_CODE, "Service->IncomingHandler->handleMessage : Auth_Request");

                    Bundle data = msg.getData();
                    //data.setClassLoader(Auth_Request.class.getClassLoader());
                    //Auth_Request info = (Auth_Request)data.getParcelable(String.valueOf(SrvCmd.CMD_Auth_Request));
                    //Log.e(APP_TAG_CODE, "Service->IncomingHandler->handleMessage : email | pass" + info.Email + " | " + info.Password);

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());


    public CmdService() {
        super("CmdService");
        // TODO Auto-generated constructor stub
    }


    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        Log.e(APP_TAG_CODE, "ElmaService->showNotification" );

        CharSequence text = getText(R.string.remote_service_started);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_send)  // the status icon
                .setTicker(text)  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(getText(R.string.app_name))  // the label of the entry
                .setContentText(text)  // the contents of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

        // Send the notification.
        // We use a string id because it is a unique number.  We use it later to cancel.
        mNM.notify(R.string.remote_service_started, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(APP_TAG_CODE, "Service->onBind" );
        return mMessenger.getBinder();
    }
    @Override
    public void onCreate(){
        Log.e(APP_TAG_CODE, "Service->onCreate" );
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e(APP_TAG_CODE, "Service->onStartCommand" );
        onHandleIntent(intent);

        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        Log.e(APP_TAG_CODE, "Service->onDestroy" );

        // Tell the user we stopped.
        Toast.makeText(this, "Destroyed", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        Log.e(APP_TAG_CODE, "Service->onHandleIntent" );
    }





}