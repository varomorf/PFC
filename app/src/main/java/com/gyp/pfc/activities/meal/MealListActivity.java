package com.gyp.pfc.activities.meal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// get selected date from adapter
		Date selectedDate = (Date) l.getAdapter().getItem(position);
		// prepare intent for showing food details view
		Intent intent = new Intent(getApplicationContext(), EditMealActivity.class);
		// put the selected food on the intent
		intent.putExtra(SELECTED_DATE, selectedDate);
		// start the activity with the intent
		startActivity(intent);
	}

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
