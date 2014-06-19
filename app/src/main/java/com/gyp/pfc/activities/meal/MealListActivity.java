package com.gyp.pfc.activities.meal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.os.Bundle;

import com.gyp.pfc.DateIterator;
import com.gyp.pfc.R;
import com.gyp.pfc.adapters.DateListViewAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Meal;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * Activity for listing exercises and interacting with them
 * 
 * @author Alvaro
 * 
 */
public class MealListActivity extends OrmLiteBaseListActivity<DatabaseHelper> implements MealConstants {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.entity_list);
		setListAdapter(new DateListViewAdapter(this, getDates()));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Returns a list of dates from today to date of the first meal
	 * 
	 * @return a list of dates from today to date of the first meal
	 */
	private List<Date> getDates() {
		ArrayList<Date> dates = new ArrayList<Date>();
		QueryBuilder<Meal, Integer> qb = getHelper().getMealDao().queryBuilder();
		qb.selectRaw("MIN(date)");
		Date mealDate = null;
		try {
			GenericRawResults<Object[]> results = getHelper().getMealDao().queryRaw(qb.prepareStatementString(),
					new DataType[] { DataType.DATE_STRING });
			mealDate = (Date) results.getFirstResult()[0];
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Iterator<Date> iterator = new DateIterator(new Date(), mealDate); iterator.hasNext();) {
			dates.add(iterator.next());
		}
		return dates;
	}

	// Inner classes -------------------------------------------------
}
