package com.korotaev.r.ms.hor.IntentService;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.korotaev.r.ms.hor.MainActivity;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.WebServices.ServiceObjectHelper;
import com.korotaev.r.ms.testormlite.data.Entity.Achievement;
import com.korotaev.r.ms.testormlite.data.Entity.Achievmenttype;
import com.korotaev.r.ms.testormlite.data.Entity.Auto;
import com.korotaev.r.ms.testormlite.data.Entity.Messagetype;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;
import com.korotaev.r.ms.testormlite.data.Entity.Tool;
import com.korotaev.r.ms.testormlite.data.Entity.Tooltypes;
import com.korotaev.r.ms.testormlite.data.Entity.TransmissionType;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import java.util.ArrayList;
import java.util.List;

import static com.korotaev.r.ms.hor.IntentService.SrvCmd.CODE_INFO;


public class CmdService extends IntentService {

    private static  User user = null;

    private SyncTask mSyncTask = null;
    private SetUserInfoTask mSetUserInfoTask = null;

    /** For showing and hiding our notification. */
    NotificationManager mNM;
    /** Keeps track of all current registered clients. */
    ArrayList<Messenger> mClients = new ArrayList<Messenger>();
    /** Holds last value set by a client. */
    int mValue = 0;
    String  currentToken;
    private List<Requesttype> requesttypeList;
    private List<Messagetype> messagetypeList;
    private List<TransmissionType> transmissionTypeList;
    private List<Tooltypes> tooltypesList;
    private List<Achievmenttype> achievmenttypeList;

    private List<Achievement> achievements;
    private List<Tool> tools;
    private List<Auto> autos;


    public  void  sendMsgToServiceClients(Message msg, int command){

        for (Messenger item : mClients) {
            try {
                Message answerMsg = Message.obtain(null, command);
                answerMsg.replyTo = msg.replyTo;
                item.send(answerMsg);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * Handler of incoming messages from clients.
     */
    class IncomingHandler extends Handler
    {


        @Override
        public void handleMessage(Message msg) {
            Log.e(CODE_INFO, "Service->IncomingHandler->handleMessage : " + msg.what);


            switch (msg.what) {
                case SrvCmd.CMD_RegisterIntentServiceClientReq:
                    mClients.add(msg.replyTo);
                    sendMsgToServiceClients(msg, SrvCmd.CMD_RegisterIntentServiceClientResp);
                    break;
                case SrvCmd.CMD_UnregisterIntentServiceClientReq:
                    mClients.remove(msg.replyTo);
                    break;
                case SrvCmd.CMD_EntitySyncReq:
                    mSyncTask = new SyncTask(msg );
                    mSyncTask.execute((Void) null);
                    break;
                case SrvCmd.CMD_EntityGetUserInfoReq:
                    break;
                case SrvCmd.CMD_EntitySetUserInfoReq:
                    Bundle data = msg.getData();
                    long regionId = (long) data.get("region");
                    String password = (String) data.get("password");
                    String filename = (String) data.get("filename");
                    String file = (String) data.get("file");

                    mSetUserInfoTask = new SetUserInfoTask(msg,
                            regionId,true,
                            password,
                            "filename",file);
                    mSetUserInfoTask.execute((Void) null);
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


    public class SyncTask extends AsyncTask<Void, Void, Boolean> {
        Message msg;
        public SyncTask(Message msg) {
            this.msg = msg;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Bundle data = msg.getData();
            long userId = 0;
            boolean userSpecified = false;

            currentToken = Preferences.loadObjInPrefs(CmdService.this,Preferences.SAVED_Session);

            requesttypeList = ServiceObjectHelper.getRequestTypes(CmdService.this,currentToken);
            messagetypeList = ServiceObjectHelper.getMessageType(CmdService.this,currentToken);
            achievmenttypeList = ServiceObjectHelper.getAchievmenttype(CmdService.this,currentToken);
            tooltypesList = ServiceObjectHelper.getTooltypes(CmdService.this,currentToken);
            transmissionTypeList = ServiceObjectHelper.getTransmissionType(CmdService.this,currentToken);

            User currentUser = ServiceObjectHelper.getCurrentUserInfo(CmdService.this, currentToken);

            if (currentUser != null && currentUser.getId() > 0) {
                userId = currentUser.getId();
                userSpecified = true;
            }
            autos = ServiceObjectHelper.getAllAutoByUser(CmdService.this,currentToken, userId, userSpecified);
            tools = ServiceObjectHelper.getAllToolByUser(CmdService.this,currentToken, userId, userSpecified);
            achievements = ServiceObjectHelper.getAllAchievmentByUser(CmdService.this,currentToken,userId, userSpecified);
            user         = ServiceObjectHelper.getCurrentUserInfo(CmdService.this,currentToken);
            sendMsgToServiceClients(msg, SrvCmd.CMD_EntitySyncResp);
            return null;
        }
    }

    public class SetUserInfoTask extends AsyncTask<Void, Void, Boolean> {
        Message msg;
        private final long region;
        private final boolean regionSpecified;
        private final String password;
        private final String fileName;
        private final String fileImage;

        public SetUserInfoTask(Message msg,
                               long region,
                               boolean regionSpecified,
                               String password,
                               String fileName,
                               String fileImage) {
            this.msg = msg;
            this.region = region;
            this.regionSpecified = regionSpecified;
            this.password = password;
            this.fileName = fileName;
            this.fileImage = fileImage;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Bundle data = msg.getData();
            long userId = 0;
            boolean userSpecified = false;

            currentToken = Preferences.loadObjInPrefs(CmdService.this,Preferences.SAVED_Session);



            User currentUser = ServiceObjectHelper.setCurrentUserInfo(CmdService.this,
                    currentToken,
                    this.region,
                    this.regionSpecified,
                    this.password,
                    this.fileName,
                    this.fileImage
                    );

            if (currentUser != null && currentUser.getId() > 0) {
                userId = currentUser.getId();
                userSpecified = true;
            }

            sendMsgToServiceClients(msg, SrvCmd.CMD_EntitySetUserInfoResp);
            return null;
        }
    }




    public CmdService() {
        super("CmdService");
        // TODO Auto-generated constructor stub
    }


    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        Log.e(CODE_INFO, "ElmaService->showNotification" );

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
        Log.e(CODE_INFO, "Service->onBind" );
        return mMessenger.getBinder();
    }
    @Override
    public void onCreate(){
        Log.e(CODE_INFO, "Service->onCreate" );
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.e(CODE_INFO, "Service->onStartCommand" );
        onHandleIntent(intent);

        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        Log.e(CODE_INFO, "Service->onDestroy" );

        // Tell the user we stopped.
        Toast.makeText(this, "Завершено", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub
        Log.e(CODE_INFO, "Service->onHandleIntent" );
    }





}