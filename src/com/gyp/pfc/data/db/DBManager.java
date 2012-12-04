/**
 * 
 */
package com.gyp.pfc.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author afernandezgo
 * 
 */
public class DBManager extends SQLiteOpenHelper {
	// TODO fucking comment this
	// Constants -----------------------------------------------------
	private static final String DB_NAME = "pfcDatabase";

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------
	// TODO why so much statics???
	private static int version = 1;
	private static CursorFactory cursorFactory = null;
	private static DBManager instance;

	private static FoodManager foodManager;

	public static void createInstance(Context context) {
		if (instance == null) {
			instance = new DBManager(context);
		}
	}

	public static FoodManager getFoodManager() {
		return foodManager;
	}

	// Constructors --------------------------------------------------
	// TODO why package modifier?
	/**
	 * @param context
	 * @param DB_NAME
	 * @param factory
	 * @param version
	 */
	DBManager(Context context) {
		super(context, DB_NAME, cursorFactory, version);
		foodManager = new FoodManager(this);
	}

	// Public --------------------------------------------------------

	@Override
	public void onCreate(SQLiteDatabase db) {
		foodManager.create(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Change when needed
	}

	/**
	 * 
	 * @param query
	 * @return
	 */
	public Cursor executeReadableQuery(String query) {
		SQLiteDatabase db = getReadableDatabase();
		return db.rawQuery(query, null);
	}

	/**
	 * @param query
	 */
	public void executeWritableQuery(String query) {
		SQLiteDatabase db = getWritableDatabase();
		if (db != null) {
			db.execSQL(query);
			db.close();
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
