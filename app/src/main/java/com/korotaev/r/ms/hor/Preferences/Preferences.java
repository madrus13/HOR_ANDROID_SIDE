package com.korotaev.r.ms.hor.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import static android.content.Context.MODE_PRIVATE;


public   class Preferences {

    private  static String SAVED_REGIONS = "SAVED_REGIONS";

    public static  void saveRegions(Context context, String jsonRegions) {
        SharedPreferences sPref;
        sPref = context.getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_REGIONS, jsonRegions.toString());
        ed.commit();
    }

    public static String loadRegions(Context context) {
        SharedPreferences sPref;
        sPref = context.getSharedPreferences("myPrefs", MODE_PRIVATE);
        return sPref.getString(SAVED_REGIONS, "");
    }
}
