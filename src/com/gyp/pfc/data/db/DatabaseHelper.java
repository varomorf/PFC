package com.gyp.pfc.data.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Food;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * <p>
 * ORMLite database helper used to manage the creation and upgrading of the
 * application's database.
 * </p>
 * <p>
 * This class also usually provides the DAOs used by the other classes
 * </p>
 * 
 * @author afernandezgo
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// TODO fucking comment this
	// Constants -----------------------------------------------------
	private static final String DB_NAME = "pfcDatabase";
	private static final int DB_VERSION = 1;

	// Attributes ----------------------------------------------------
	private RuntimeExceptionDao<Food, String> foodDao;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION, R.raw.ormlite_config);
	}

	// Public --------------------------------------------------------

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "Creating database");
			// create table for food entity
			TableUtils.createTable(connectionSource, Food.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "Upgrading database");
			// drop the databases
			TableUtils.dropTable(connectionSource, Food.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}

	}

	public RuntimeExceptionDao<Food, String> getFoodDao() {
		if (null == foodDao) {
			foodDao = getRuntimeExceptionDao(Food.class);
		}
		return foodDao;
	}

	/**
	 * FIXME destroyme
	 */
	public Cursor executeReadableQuery(String query) {
		SQLiteDatabase db = getReadableDatabase();
		return db.rawQuery(query, null);
	}

	/**
	 * FIXME destroyme
	 */
	public void executeWritableQuery(String query) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			db.execSQL(query);
			db.close();
		}
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		foodDao = null;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
