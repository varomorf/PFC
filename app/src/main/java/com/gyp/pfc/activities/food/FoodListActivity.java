package com.gyp.pfc.activities.food;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.base.BaseListActivity;
import com.gyp.pfc.activities.constants.FoodConstants;
import com.gyp.pfc.activities.helpers.FoodActivityHelper;
import com.gyp.pfc.adapters.FoodListViewAdapter;
import com.gyp.pfc.data.domain.food.Food;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

/**
 * Activity for listing all food on DB
 * 
 * @author Alvaro
 */
public class FoodListActivity extends BaseListActivity implements OnQueryTextListener, FoodConstants {

	// Constants -----------------------------------------------------

	private static final int MIN_QUERY_LENGTH = 3;

	private static final String TAG = FoodListActivity.class.getName();

	// Attributes ----------------------------------------------------

	/** Helper to be used */
	private FoodActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new FoodActivityHelper(this);

		setContentView(R.layout.entity_list);
		SearchView searchView = (SearchView) findViewById(R.id.search);
		searchView.setOnQueryTextListener(this);
		setListAdapter(new FoodListViewAdapter(this, R.layout.food_list_item, FoodManager.it().getAllFoods()));
		registerForContextMenu(getListView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.foods_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		if (item.getItemId() == R.id.foodsListMenuMain) {
			// go back
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			onFoodEdited();
		}
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return onQueryTextChange(query);
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		if (StringUtils.isBlank(newText)) {
			refreshAdapter(FoodManager.it().getAllFoods());
		}
		if (newText.length() >= MIN_QUERY_LENGTH) {
			List<Food> foods;
			try {
				QueryBuilder<Food, Integer> qb = getHelper().getFoodDao().queryBuilder();
				qb.where().like("name", "%" + newText + "%");
				PreparedQuery<Food> pq = qb.prepare();
				foods = getHelper().getFoodDao().query(pq);
			} catch (SQLException e) {
				Log.e(TAG, "Error while finding foods with name like " + newText, e);
				foods = FoodManager.it().getAllFoods();
			}
			refreshAdapter(foods);
		}
		return true;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// get selected food from adapter
		Food selectedFood = (Food) l.getAdapter().getItem(position);
		if (getIntent() != null && getIntent().getBooleanExtra(RETURN_FOOD, false)) {
			returnResult(selectedFood);
		} else {
			showFoodDetails(selectedFood);
		}
	}

	/**
	 * Starts the {@link ShowFoodDetailsActivity} for the passed food
	 * 
	 * @param selectedFood
	 *            the food to be passed to {@link ShowFoodDetailsActivity}
	 */
	protected void showFoodDetails(Food selectedFood) {
		// prepare intent for showing food details view
		Intent intent = new Intent(getApplicationContext(), ShowFoodDetailsActivity.class);
		// put the selected food on the intent
		intent.putExtra(SELECTED_FOOD, selectedFood);
		// start the activity with the intent
		startActivity(intent);
	}

	/**
	 * Returns the passed food as result and finished this activity
	 * 
	 * @param selectedFood
	 *            the food to be returned as result
	 */
	protected void returnResult(Food selectedFood) {
		// prepare intent for returning selected food
		Intent intent = new Intent();
		// put the selected food on the intent
		intent.putExtra(SELECTED_FOOD, selectedFood);
		// return result and finish
		setResult(SELECT_FOOD, intent);
		finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// the activity is shown via back button
		refreshAdapter();
	}

	@Override
	protected void doDelete(int position) {
		// get selected food from adapter and call helper with positive action
		Food selectedFood = (Food) getListAdapter().getItem(position);
		h.deleteWithDialog(R.string.assureFoodDeletion, deletionAction(selectedFood));
	}

	@Override
	protected void doEdit(int position) {
		Food selectedFood = (Food) getListAdapter().getItem(position);
		Intent editFoodIntent = new Intent(getApplicationContext(), EditFoodActivity.class);
		editFoodIntent.putExtra(SELECTED_FOOD, selectedFood);
		startActivityForResult(editFoodIntent, EDIT_FOOD);
	}

	// Private -------------------------------------------------------

	private void refreshAdapter(List<Food> foods) {
		FoodListViewAdapter adapter = (FoodListViewAdapter) getListAdapter();
		adapter.setFoods(foods);
		adapter.notifyDataSetChanged();
	}

	private void refreshAdapter() {
		refreshAdapter(FoodManager.it().getAllFoods());
	}

	/**
	 * Callback for when a food has been correctly edited
	 */
	private void onFoodEdited() {
		// refresh UI
		refreshAdapter();
		Toast.makeText(getApplicationContext(), getString(R.string.editFoodMessage), Toast.LENGTH_SHORT).show();
	}

	/**
	 * Creates an {@link OnClickListener} that on click will delete the passed food from DB, refresh the adapter
	 * and show a toast to the user
	 * 
	 * @param food
	 *            the food to be deleted
	 * @return the {@link OnClickListener}
	 */
	private OnClickListener deletionAction(final Food food) {
		return new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// delete food on DB
				getHelper().getFoodDao().delete(food);
				// refresh the adapter to update UI
				refreshAdapter();
				// show deletion message
				Toast.makeText(getApplicationContext(), food.getName() + " " + getString(R.string.deleteMessage),
						Toast.LENGTH_SHORT).show();
			}
		};
	}
	// Inner classes -------------------------------------------------

}
