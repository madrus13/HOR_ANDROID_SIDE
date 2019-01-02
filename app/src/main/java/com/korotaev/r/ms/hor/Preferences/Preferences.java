package com.korotaev.r.ms.hor.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.korotaev.r.ms.testormlite.data.Entity.Auto;
import com.korotaev.r.ms.testormlite.data.Entity.Tool;
import com.korotaev.r.ms.testormlite.data.Entity.User;

import org.codehaus.jackson.map.ObjectMapper;

import java.util.Arrays;
import java.util.List;

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
    public  static String SAVED_CurrentUserInfo     = "CurrentUserInfo";
    public  static String SAVED_CurrentUserAchievs  = "CurrentUserAchievs";
    public  static String SAVED_CurrentUserAutos     = "CurrentUserAutos";
    public  static String SAVED_CurrentUserTools     = "CurrentUserTools";

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

    public static User loadCurrentUserInfo(Context context) {
        SharedPreferences sPref;
        User currentObj = null;
        sPref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String Info = sPref.getString(SAVED_CurrentUserInfo, "");
        ObjectMapper mapper = new ObjectMapper();
        try {
            currentObj = mapper.readValue(Info, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return currentObj;
    }


    public static Auto loadCurrentUserAuto(Context context) {
        SharedPreferences sPref;
        Auto currentObj = null;
        sPref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String Info = sPref.getString(SAVED_CurrentUserAutos, "");
        ObjectMapper mapper = new ObjectMapper();
        try {
            currentObj = mapper.readValue(Info, Auto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentObj;
    }

    public static List<Tool> loadCurrentUserTools(Context context) {
        SharedPreferences sPref;
        List<Tool> currentObj = null;
        sPref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String Info = sPref.getString(SAVED_CurrentUserAutos, "");
        ObjectMapper mapper = new ObjectMapper();
        try {
            currentObj = Arrays.asList(mapper.readValue(Info, Tool[].class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentObj;
    }
}
