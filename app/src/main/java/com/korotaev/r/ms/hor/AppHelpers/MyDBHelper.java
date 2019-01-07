package com.korotaev.r.ms.hor.AppHelpers;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.korotaev.r.ms.testormlite.data.DatabaseHelper;

public class MyDBHelper {
    private DatabaseHelper databaseHelper = null;
    private Context context;

    public MyDBHelper(Context context) {
        this.context = context;
    }
    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
