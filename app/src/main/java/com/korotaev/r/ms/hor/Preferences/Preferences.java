package com.korotaev.r.ms.hor.Preferences;

import android.app.Activity;
import android.content.SharedPreferences;


public  class Preferences extends Activity {

    private  final String SAVED_REGIONS = "SAVED_REGIONS";

    public  void saveRegions(String jsonRegions) {
        SharedPreferences sPref;
        sPref = getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(SAVED_REGIONS, jsonRegions.toString());
        ed.commit();
    }

    public String loadRegions() {
        SharedPreferences sPref;
        sPref = getSharedPreferences("myPrefs", MODE_PRIVATE);
        return sPref.getString(SAVED_REGIONS, "");
    }
}
