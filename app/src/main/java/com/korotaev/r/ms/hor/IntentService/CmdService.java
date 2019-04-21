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
import android.widget.Toast;

import com.korotaev.r.ms.hor.AppHelpers.FileHelper;
import com.korotaev.r.ms.hor.AppHelpers.MyDBHelper;
import com.korotaev.r.ms.hor.AppHelpers.ParserHelper;
import com.korotaev.r.ms.hor.MainActivity;
import com.korotaev.r.ms.hor.Preferences.Preferences;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.hor.WebServices.ServiceObjectHelper;
import com.korotaev.r.ms.hor.WebServices.VectorByte;
import com.korotaev.r.ms.hor.WebServices.serviceResult;
import com.korotaev.r.ms.testormlite.data.Entity.Achievement;
import com.korotaev.r.ms.testormlite.data.Entity.Achievmenttype;
import com.korotaev.r.ms.testormlite.data.Entity.Auto;
import com.korotaev.r.ms.testormlite.data.Entity.Messagetype;
import com.korotaev.r.ms.testormlite.data.Entity.Region;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;
import com.korotaev.r.ms.testormlite.data.Entity.Tool;
import com.korotaev.r.ms.testormlite.data.Entity.Tooltypes;
import com.korotaev.r.ms.testormlite.data.Entity.TransmissionType;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import java.util.ArrayList;
import java.util.List;


public class CmdService extends IntentService {

    private static final int GET_MESSAGE_PAGE_SIZE = 100;
    private static  User user = null;

    private SyncTask mSyncTask = null;
    private SetUserInfoTask mSetUserInfoTask = null;

    private GetMessageByRegionTask mGetMessageByRegionTask = null;
    private InsertMessageTask mInsertMessageTask = null;

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
    private List<Region> regionsList;
    private List<Achievement> achievements;
    private List<Tool> tools;
    private Auto autos;
    private MyDBHelper myDBHelper = new MyDBHelper(CmdService.this);


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
                    mSyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    break;
                case SrvCmd.CMD_EntityGetUserInfoReq:
                    break;
                case SrvCmd.CMD_EntitySetUserInfoReq: {
                    Bundle data = msg.getData();
                    long regionId = (long) data.get("region");
                    String password = (String) data.get("password");
                    String filename = (String) data.get("filename");
                    byte[] file =  data.getByteArray("file");
                    long transmissionType = (long) data.get("transmissionType");
                    String nameAuto = (String) data.get("nameAuto");
                    long haveCable = (long) data.get("haveCable");
                    String toolTypeIds = (String) data.get("toolTypeIds");
                    VectorByte fileImage = null;
                    FileHelper fileHelper = new FileHelper();
                    if (file!=null && file.length > 0) {
                        fileImage = new VectorByte(file);
                        fileHelper.createIntStoragePrivatePicture(CmdService.this,"in_cmdService_file_.jpeg", fileImage.toBytes());
                    }

                    mSetUserInfoTask = new SetUserInfoTask(msg,
                            regionId, true,
                            password,
                            "filename", fileImage , transmissionType, nameAuto, haveCable, toolTypeIds);
                    mSetUserInfoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    break;
                }

                case SrvCmd.CMD_InsertMessageReq: {
                    Bundle data = msg.getData();
                    mInsertMessageTask = new InsertMessageTask(msg,data);
                    mInsertMessageTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


                    sendMsgToServiceClients(msg, SrvCmd.CMD_InsertMessageResp);

                    break;
                }
                case SrvCmd.CMD_GetMessageByUserRegionReq:
                {

                    mGetMessageByRegionTask = new GetMessageByRegionTask(msg);
                    mGetMessageByRegionTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    break;
                }
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
            regionsList = ServiceObjectHelper.getAllRegions(CmdService.this, "");

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
        private final VectorByte fileImage;
        private final long transmissionType;
        private final String nameAuto;
        private final long haveCable;
        private final String toolTypeIds;

        public SetUserInfoTask(Message msg,
                               long region,
                               boolean regionSpecified,
                               String password,
                               String fileName,
                               VectorByte fileImage,
                               long transmissionType,
                               String nameAuto,
                               long haveCable,
                               String toolTypeIds
                               ) {
            this.msg = msg;
            this.region = region;
            this.regionSpecified = regionSpecified;
            this.password = password;
            this.fileName = fileName;
            this.fileImage = fileImage;
            this.transmissionType = transmissionType;
            this.nameAuto = nameAuto;
            this.haveCable = haveCable;
            this.toolTypeIds = toolTypeIds;
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


            List<Tool> tools = ServiceObjectHelper.setCurrentUserTools(CmdService.this, currentToken,toolTypeIds);

            boolean haveCableIsSpecified = true;

            boolean transmissionTypeSpecified = true;
            if (transmissionType <= 0 ) {
                transmissionTypeSpecified = false;
            }

            List<Auto> auto = ServiceObjectHelper.setCurrentUserAuto(CmdService.this,
                    currentToken,
                    this.nameAuto,
                    this.haveCable,
                    haveCableIsSpecified,
                    this.transmissionType,
                    transmissionTypeSpecified);


            sendMsgToServiceClients(msg, SrvCmd.CMD_EntitySetUserInfoResp);
            return null;
        }
    }



    public class GetMessageByRegionTask extends AsyncTask<Void, Void, Boolean> {
        Message msg;
        public GetMessageByRegionTask(Message msg) {
            this.msg = msg;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            ArrayList<com.korotaev.r.ms.testormlite.data.Entity.Message> msgList = new ArrayList<com.korotaev.r.ms.testormlite.data.Entity.Message>();
            msgList.clear();

            currentToken = Preferences.loadObjInPrefs(CmdService.this,Preferences.SAVED_Session);
            User currentUser = ServiceObjectHelper.getCurrentUserInfo(CmdService.this, currentToken);
            boolean isEnd = false;
            String val = Preferences.loadObjInPrefs(CmdService.this, Preferences.SAVED_LAST_MSG_ROW_IN_REGION);
            Long offset =  ParserHelper.TryParse(val);

            int page =  0;
            while (isEnd == false && currentUser!=null && currentUser.getRegion()!=null) {


                    List<com.korotaev.r.ms.testormlite.data.Entity.Message> retVal = ServiceObjectHelper.findMessageByRegionAndCreationDateBetweenOffset(
                            CmdService.this,
                            currentToken, currentUser.getRegion(), offset.intValue(),page, GET_MESSAGE_PAGE_SIZE);

                    if (retVal!=null && retVal.size() > 0)
                    {
                        msgList.addAll(retVal);
                        //Next page if
                        if (retVal.size() == GET_MESSAGE_PAGE_SIZE) {
                            page++;
                        }
                        else if (retVal.size() < GET_MESSAGE_PAGE_SIZE) {
                            isEnd = true;
                            break;
                        }


                    }
                    else {
                        isEnd = true;
                    }
                    myDBHelper.getHelper().addLog(SrvCmd.CODE_INFO, "getAllMessageByRegion row = " + page );
            }

            myDBHelper.getHelper().addMessageList(msgList);
            //Preferences.saveObjInPrefs(CmdService.this,
            //        Preferences.SAVED_LAST_MSG_ROW_IN_REGION,String.valueOf(page));
            sendMsgToServiceClients(msg, SrvCmd.CMD_GetMessageByUserRegionResp);

            return null;
        }
    }


    public class InsertMessageTask extends AsyncTask<Void, Void, Boolean> {
        Message msg;
        Bundle data;
        public InsertMessageTask(Message msg, Bundle data) {
            this.msg = msg;
            this.data = data;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            currentToken = Preferences.loadObjInPrefs(CmdService.this,Preferences.SAVED_Session);

            String text = this.data.getString("text");
            Long requestId = this.data.getLong("requestId");
            Long regionId = this.data.getLong("regionId");
            Long userRx = this.data.getLong("userRx");
            Long typeId = this.data.getLong("typeId");
            Long fileId = this.data.getLong("fileId");
            String fileName = this.data.getString("fileName");
            byte[] fileImage = this.data.getByteArray("fileImage");

            VectorByte file = null;
            if (fileImage!=null && fileImage.length > 0 && !fileImage.toString().isEmpty()) {
                file = new VectorByte(fileImage);
            }

            serviceResult result = ServiceObjectHelper.insertMessage(CmdService.this, currentToken,
                    text,
                    requestId, requestId > 0 ? true : false,
                    regionId , regionId > 0 ? true : false,
                    userRx,    userRx > 0 ? true : false,
                    typeId,    typeId > 0  ? true : false,
                    fileId,
                    fileName,
                    file

            );



            return null;
        }
    }

    public CmdService() {
        super("CmdService");
        // TODO Auto-generated constructor stub
    }


    private void showNotification() {


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

        return mMessenger.getBinder();
    }
    @Override
    public void onCreate(){

        //mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        //showNotification();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        onHandleIntent(intent);

        return START_STICKY;
    }
    @Override
    public void onDestroy() {

        // Tell the user we stopped.
        Toast.makeText(this, "Завершено", Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO Auto-generated method stub

    }





}