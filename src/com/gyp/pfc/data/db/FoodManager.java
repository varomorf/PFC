package com.gyp.pfc.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gyp.pfc.data.Food;

/**
 * Manager for the {@link Food} entity
 * 
 * @author afernandezgo
 * 
 */
public class FoodManager {
	// TODO fucking comment everything
	private DBManager db;

	private static final String tableFoods = "foods";
	private String SQLCreateFoods = "CREATE TABLE " + tableFoods
			+ " (name VARCHAR(100), calories INT, sugars INT, fats INT) ";

	FoodManager(DBManager db) {
		this.db = db;
	}

	public void create(SQLiteDatabase sqldb) {
		sqldb.execSQL(SQLCreateFoods);
	}

	public void insertFood(Food food) {
		String query = "INSERT INTO " + tableFoods + " VALUES('"
				+ food.getName() + "', " + food.getCalories() + ", "
				+ food.getSugars() + ", " + food.getFats() + " ) ";
		db.executeWritableQuery(query);
	}

	public Cursor getFoods() {
		String query = "SELECT name, calories, sugars, fats FROM " + tableFoods;
		return db.executeReadableQuery(query);
	}

	public void deleteFood(String foodName) {
		String query = "DELETE FROM " + tableFoods + " WHERE name='" + foodName
				+ "'";
		db.executeWritableQuery(query);
	}

	public void updateFood(String oldName, Food food) {
		String query = "UPDATE " + tableFoods + " SET name='" + food.getName()
				+ "', calories='" + food.getCalories() + "', sugars='"
				+ food.getSugars() + "', fats='" + food.getFats()
				+ "' WHERE name='" + oldName + "'";
		db.executeWritableQuery(query);
	}

}
