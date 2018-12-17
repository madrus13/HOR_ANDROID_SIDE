package com.korotaev.r.ms.hor;

import android.content.Intent;
import android.os.Bundle;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.korotaev.r.ms.testormlite.data.DatabaseHelper;
import com.korotaev.r.ms.testormlite.data.Entity.TLog;

import java.sql.SQLException;
import java.util.Date;

public  class InitActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    static Dao<TLog, Integer> dao;
    public void addLog(String type, String text)
    {
        try {
            if (dao==null) {

                dao = getHelper().getTLogDao();
            }
            TLog tlog = new TLog();
            tlog.setDate(new Date());
            tlog.setName(text);
            tlog.setType(type);
            dao.create(tlog);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clearLog()
    {
        try {
            if (dao==null) {
                dao = getHelper().getTLogDao();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        addLog("INFO", "START");
        Intent intent = new Intent(InitActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
