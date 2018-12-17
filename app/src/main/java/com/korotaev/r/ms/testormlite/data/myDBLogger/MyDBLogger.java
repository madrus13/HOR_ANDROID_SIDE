package com.korotaev.r.ms.testormlite.data.myDBLogger;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;
import com.korotaev.r.ms.testormlite.data.DatabaseHelper;
import com.korotaev.r.ms.testormlite.data.Entity.TLog;

import java.sql.SQLException;
import java.util.Date;

public  class MyDBLogger extends OrmLiteBaseActivity<DatabaseHelper> {
    static Dao<TLog, Integer> dao;

    public void addLog(String type, String text)
    {
        try {
            if (dao==null) {

                dao = getHelper().getTLogDao();
            }
            TLog tlog = new TLog();
            tlog.setDate((java.sql.Date) new Date());
            tlog.setName(text);
            tlog.setType(type);
            dao.createIfNotExists(tlog);
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
}
