package com.korotaev.r.ms.testormlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.korotaev.r.ms.hor.R;
import com.korotaev.r.ms.testormlite.data.Entity.Achievement;
import com.korotaev.r.ms.testormlite.data.Entity.Achievmenttype;
import com.korotaev.r.ms.testormlite.data.Entity.Auto;
import com.korotaev.r.ms.testormlite.data.Entity.Files;
import com.korotaev.r.ms.testormlite.data.Entity.Message;
import com.korotaev.r.ms.testormlite.data.Entity.Messagetype;
import com.korotaev.r.ms.testormlite.data.Entity.Region;
import com.korotaev.r.ms.testormlite.data.Entity.Request;
import com.korotaev.r.ms.testormlite.data.Entity.Requeststatus;
import com.korotaev.r.ms.testormlite.data.Entity.Requesttype;
import com.korotaev.r.ms.testormlite.data.Entity.Session;
import com.korotaev.r.ms.testormlite.data.Entity.TLog;
import com.korotaev.r.ms.testormlite.data.Entity.Tool;
import com.korotaev.r.ms.testormlite.data.Entity.Tooltypes;
import com.korotaev.r.ms.testormlite.data.Entity.TransmissionType;
import com.korotaev.r.ms.testormlite.data.Entity.User;
import com.korotaev.r.ms.testormlite.data.Entity.Userstatus;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Database helper which creates and upgrades the database and provides the DAOs for the app.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 42;
	private static final String DATABASE_NAME = "ormDB_"+ DATABASE_VERSION +".db";

	private static Dao<Tooltypes		 , Integer> TooltypesDao;
	private static Dao<Achievmenttype   , Integer> AchievmenttypeDao;
	private static Dao<Messagetype      , Integer> MessagetypeDao;
	private static Dao<Requeststatus    , Integer> RequeststatusDao;
	private static Dao<Requesttype      , Integer> RequesttypeDao;
	private static Dao<Userstatus       , Integer> UserstatusDao;
	private static Dao<TransmissionType , Integer> TransmissionTypeDao;
	private static Dao<Tool             , Integer> ToolDao;
	private static Dao<Achievement      , Integer> AchievementDao;
	private static Dao<Region           , Integer> RegionDao;
	private static Dao<User             , Integer> UserDao;
	private static Dao<Auto             , Integer> AutoDao;
	private static Dao<Message          , Integer> MessageDao;
	private static Dao<Request          , Integer> RequestDao;
	private static Dao<Session          , Integer> SessionDao;
	private static  Dao<TLog             , Integer> TLogDao;
	private static  Dao<Files             , Integer> FilesDao;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    /************************************************
     * Suggested Copy/Paste Done
     ************************************************/

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Tooltypes.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Achievmenttype.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Messagetype.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Requeststatus.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Requesttype.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Userstatus.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, TransmissionType.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Tool.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Achievement.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Region.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, User.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Auto.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Message.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Request.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}
		try {
			TableUtils.createTable(connectionSource, Session.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}

		try {
			TableUtils.createTable(connectionSource, TLog.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}

		try {
			TableUtils.createTable(connectionSource, Files.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Unable to create database", e);
		}


	}

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Achievement.class, true);
            TableUtils.dropTable(connectionSource, Achievmenttype.class, true);
            TableUtils.dropTable(connectionSource, Auto.class, true);
            TableUtils.dropTable(connectionSource, Message.class, true);
            TableUtils.dropTable(connectionSource, Messagetype.class, true);
            TableUtils.dropTable(connectionSource, Region.class, true);
            TableUtils.dropTable(connectionSource, Request.class, true);
            TableUtils.dropTable(connectionSource, Requeststatus.class, true);
            TableUtils.dropTable(connectionSource, Requesttype.class, true);
            TableUtils.dropTable(connectionSource, Session.class, true);
            TableUtils.dropTable(connectionSource, Tool.class, true);
            TableUtils.dropTable(connectionSource, Tooltypes.class, true);
            TableUtils.dropTable(connectionSource, TransmissionType.class, true);
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Userstatus.class, true);
			TableUtils.dropTable(connectionSource, TLog.class, true);
			TableUtils.dropTable(connectionSource, Files.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Unable to upgrade database from version " + oldVer + " to new "
                    + newVer, e);
        }
    }


    public Dao<Achievement, Integer> getAchievementDao() throws SQLException {
        if (getAchievementDao() == null) {
            AchievementDao = getDao(Achievement.class);
        }
        return AchievementDao;
    }
	public Dao<Tooltypes, Integer> getTooltypesDao() throws SQLException {
		if (TooltypesDao == null) {
			TooltypesDao = getDao(Tooltypes.class);
		}
		return TooltypesDao;
	}

	public Dao<Achievmenttype, Integer> getAchievmenttypeDao() throws SQLException {
		if (AchievmenttypeDao == null) {
			AchievmenttypeDao = getDao(Achievmenttype.class);
		}
		return AchievmenttypeDao;
	}


	public Dao<Messagetype, Integer> getMessagetypeDao() throws SQLException {
		if (MessagetypeDao == null) {
			MessagetypeDao = getDao(Messagetype.class);
		}
		return MessagetypeDao;
	}

	public Dao<Requeststatus, Integer> getRequeststatusDao() throws SQLException {
		if (RequeststatusDao == null) {
			RequeststatusDao = getDao(Requeststatus.class);
		}
		return RequeststatusDao;
	}

	public Dao<Requesttype, Integer> getRequesttypeDao() throws SQLException {
		if (RequesttypeDao == null) {
			RequesttypeDao = getDao(Requesttype.class);
		}
		return RequesttypeDao;
	}

	public Dao<Userstatus, Integer> getUserstatusDao() throws SQLException {
		if (UserstatusDao == null) {
			UserstatusDao = getDao(Userstatus.class);
		}
		return UserstatusDao;
	}

	public Dao<TransmissionType, Integer> getTransmissionTypeDao() throws SQLException {
		if (TransmissionTypeDao == null) {
			TransmissionTypeDao = getDao(TransmissionType.class);
		}
		return TransmissionTypeDao;
	}

	public Dao<Tool, Integer> getToolDao() throws SQLException {
		if (ToolDao == null) {
			ToolDao = getDao(Tool.class);
		}
		return ToolDao;
	}

	public Dao<Region, Integer> getRegionDao() throws SQLException {
		if (RegionDao == null) {
			RegionDao = getDao(Region.class);
		}
		return RegionDao;
	}

	public Dao<User, Integer> getUserDao() throws SQLException {
		if (UserDao == null) {
			UserDao = getDao(User.class);
		}
		return UserDao;
	}

	public Dao<Auto, Integer> getAutoDao() throws SQLException {
		if (AutoDao == null) {
			AutoDao = getDao(Auto.class);
		}
		return AutoDao;
	}

	public Dao<Message, Integer> getMessageDao() throws SQLException {
		if (MessageDao == null) {
			MessageDao = getDao(Message.class);
		}
		return MessageDao;
	}


	public Dao<Request, Integer> getRequestDao() throws SQLException {
		if (RequestDao == null) {
			RequestDao = getDao(Request.class);
		}
		return RequestDao;
	}


	public Dao<Session, Integer> getSessionDao() throws SQLException {
		if (SessionDao == null) {
			SessionDao = getDao(Session.class);
		}
		return SessionDao;
	}

	public Dao<TLog, Integer> getTLogDao() throws SQLException {
		if (TLogDao == null) {
			TLogDao = getDao(TLog.class);
		}
		return TLogDao;
	}

	public Dao<Files, Integer> getFilesDao() throws SQLException {
		if (FilesDao == null) {
			FilesDao = getDao(Files.class);
		}
		return FilesDao;
	}

	//static Dao<TLog, Integer> dao;
	public void addLog(String type, String text)
	{
		try {
			if (TLogDao==null) {
				TLogDao = this.getTLogDao();
			}
			TLog tlog = new TLog();
			tlog.setDate(new Date());
			tlog.setName(text);
			tlog.setType(type);
			TLogDao.create(tlog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void clearLog()
	{
		try {
			if (TLogDao==null) {
				TLogDao = this.getTLogDao();
			}
			TableUtils.clearTable(this.getConnectionSource(), TLog.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<TLog> getAllTLog()
	{
		try {
			if (TLogDao==null) {
				TLogDao = this.getTLogDao();
			}
			return TLogDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public List<Message> getAllMessage()
	{
		try {
			if (MessageDao==null) {
				MessageDao = this.getMessageDao();
			}
			return MessageDao.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public void clearMessage()
	{
		try {
			if (MessageDao==null) {
				MessageDao = this.getMessageDao();
			}
			TableUtils.clearTable(this.getConnectionSource(), Message.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}





	public void addMessageList(List<Message> msgList)
	{
		try {
			if (MessageDao==null) {
				MessageDao = this.getMessageDao();
			}
			for (Message msg: msgList
				 ) {
				Message finded = MessageDao.queryBuilder().where().eq("id", msg.getId()).queryForFirst();
				if (finded == null) {
					MessageDao.create(msg);

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addRequestList(List<Request> reqList)
	{
		try {
			if (RequestDao==null) {
				RequestDao = this.getRequestDao();
			}
			for (Request msg: reqList
			) {
				Request finded = RequestDao.queryBuilder().where().eq("id", msg.getId()).queryForFirst();
				if (finded == null) {
					RequestDao.create(msg);

				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Message getMessageItem(int startRow)
	{
		List<Message> finded = null;

		try {
			if (MessageDao==null) {
				MessageDao = this.getMessageDao();
			}

			finded = MessageDao.queryBuilder().limit(1L)
					.offset(Long.valueOf(startRow))
					.orderBy("id", true)
					.query();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return finded.size() > 0 ? finded.get(0) : null;
	}

	public List<Message> getMessageItemBlock(Long region, int startRow, int  size)
	{
		List<Message> finded = null;
		try {
			if (MessageDao==null) {
				MessageDao = this.getMessageDao();
			}


			finded = MessageDao.queryBuilder()
					.orderBy("id", true)
					.limit(Long.valueOf(size))
					.offset(Long.valueOf(startRow))
					.where().eq("region",region).query();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return finded;
	}


	public int getMessageCount(Long region)
	{
		int result = 0;
		try {
			if (MessageDao==null) {
				MessageDao = this.getMessageDao();
			}
			result = (int) MessageDao.queryBuilder()
					.where().eq("region",region).countOf();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  result;
	}


	public Request getRequestItem(int startRow)
	{
		List<Request> finded = null;

		try {
			if (RequestDao==null) {
				RequestDao = this.getRequestDao();
			}

			finded = RequestDao.queryBuilder().limit(1L)
					.offset(Long.valueOf(startRow))
					.orderBy("id", true)
					.query();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return finded.size() > 0 ? finded.get(0) : null;
	}


	public List<Request> getRequestItemBlock(Long region, int startRow, int  size)
	{
		List<Request> finded = null;
		try {
			if (RequestDao==null) {
				RequestDao = this.getRequestDao();
			}

			finded = RequestDao.queryBuilder()
					.orderBy("id", true)
					.limit(Long.valueOf(size))
					.offset(Long.valueOf(startRow))
					.where().eq("region",region).query();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return finded;
	}


	public int getRequestCount(Long region)
	{
		int result = 0;
		try {
			if (RequestDao==null) {
				RequestDao = this.getRequestDao();
			}
			result = (int) RequestDao.queryBuilder()
					.where().eq("region",region).countOf();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return  result;
	}
}
