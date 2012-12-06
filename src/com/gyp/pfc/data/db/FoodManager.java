package com.gyp.pfc.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gyp.pfc.data.domain.Food;

/**
 * Manager for the {@link Food} entity
 * 
 * @author afernandezgo
 * 
 */
public class FoodManager {
	// TODO fucking comment everything
	// Constants -----------------------------------------------------
	private static final String TABLE_FOODS_NAME = "foods";

	// Attributes ----------------------------------------------------
	private DatabaseHelper db;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	// TODO why package modifier???
	FoodManager(DatabaseHelper db) {
		this.db = db;
	}

	// Public --------------------------------------------------------
	public void create(SQLiteDatabase sqldb) {
		sqldb.execSQL(createFoodsTableSql());
	}

	public void insertFood(Food food) {
		String query = "INSERT INTO " + TABLE_FOODS_NAME + " VALUES('"
				+ food.getName() + "', " + food.getCalories() + ", "
				+ food.getSugars() + ", " + food.getFats() + " ) ";
		db.executeWritableQuery(query);
	}

	public Cursor getFoods() {
		String query = "SELECT name, calories, sugars, fats FROM "
				+ TABLE_FOODS_NAME;
		return db.executeReadableQuery(query);
	}

	public void deleteFood(String foodName) {
		String query = "DELETE FROM " + TABLE_FOODS_NAME + " WHERE name='"
				+ foodName + "'";
		db.executeWritableQuery(query);
	}

	public void updateFood(String oldName, Food food) {
		String query = "UPDATE " + TABLE_FOODS_NAME + " SET name='"
				+ food.getName() + "', calories='" + food.getCalories()
				+ "', sugars='" + food.getSugars() + "', fats='"
				+ food.getFats() + "' WHERE name='" + oldName + "'";
		db.executeWritableQuery(query);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	private String createFoodsTableSql() {
		String sql = "CREATE TABLE " + TABLE_FOODS_NAME
				+ " (name VARCHAR(100), calories INT, sugars INT, fats INT)";
		return sql;
	}

	// Inner classes -------------------------------------------------

}
