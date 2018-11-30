package com.korotaev.r.ms.hor.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;


public   class Preferences {

    public  static String SAVED_Session             = "Session";
    public  static String SAVED_Region              = "Region";
    public  static String SAVED_MessageType         = "MessageType";
    public  static String SAVED_Achievmenttype      = "Achievmenttype";
    public  static String SAVED_RequestType         = "RequestType";
    public  static String SAVED_ToolType            = "ToolType";
    public  static String SAVED_TransmissionType    = "TransmissionType";
    public  static String SAVED_Login               = "Login";
    public  static String SAVED_Pass                = "Pass";
    public  static String SAVED_AutoSignInState     = "AutoSignInState";


    public static  void saveObjInPrefs(Context context,String objectName, String str) {
        SharedPreferences sPref;
        sPref = context.getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(objectName, str);
        ed.commit();
    }

    public static String loadObjInPrefs(Context context, String objectName) {
        SharedPreferences sPref;
        sPref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        return sPref.getString(objectName, "");
    }
}
