package com.gyp.pfc.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.FoodListViewAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Food;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Activity for listing all food on DB
 * 
 * @author Alvaro
 */
public class FoodsListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {
	// Constants -----------------------------------------------------

	/**
	 * Key for the selected food
	 */
	public static final String SELECTED_FOOD = "selectedFood";
	/**
	 * Result code for food edition
	 */
	public static final int EDIT_FOOD = 1;

	// Attributes ----------------------------------------------------

	private FoodListViewAdapter listViewAdapter;
	private List<Food> foods;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foods_list);

		loadFoods();
		listViewAdapter = new FoodListViewAdapter(this,
				R.layout.food_list_item, foods);
		setListAdapter(listViewAdapter);
		registerForContextMenu(getListView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.foods_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.foodsListMenuMain:
			// go back
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.foods_list_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.deleteFood:
			deleteFood(info.position);
			return true;
		case R.id.editFood:
			editFood(info.position);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case EDIT_FOOD:
			if (resultCode == Activity.RESULT_OK) {
				loadFoods();
				// refresh UI
				loadFoods();
				listViewAdapter.setFoods(foods);
				listViewAdapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(),
						getString(R.string.editFoodMessage), Toast.LENGTH_SHORT)
						.show();
			}
			break;

		default:
			break;
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Food selectedFood = (Food) l.getAdapter().getItem(position);
		Intent showFoodDetailsIntent = new Intent(getApplicationContext(),
				ShowFoodDetailsActivity.class);
		showFoodDetailsIntent.putExtra(SELECTED_FOOD, selectedFood);
		startActivity(showFoodDetailsIntent);
	}

	// Private -------------------------------------------------------

	private void loadFoods() {
		foods = getHelper().getFoodDao().queryForAll();
	}

	private void deleteFood(int position) {
		Food selectedFood = (Food) getListAdapter().getItem(position);
		getHelper().getFoodDao().delete(selectedFood);
		foods.remove(selectedFood);
		// refresh UI
		listViewAdapter.notifyDataSetChanged();
		Toast.makeText(
				getApplicationContext(),
				selectedFood.getName() + " "
						+ getString(R.string.deleteFoodMessage),
				Toast.LENGTH_SHORT).show();
	}

	private void editFood(int position) {
		Food selectedFood = (Food) getListAdapter().getItem(position);
		Intent editFoodIntent = new Intent(getApplicationContext(),
				EditFoodActivity.class);
		editFoodIntent.putExtra(SELECTED_FOOD, selectedFood);
		startActivityForResult(editFoodIntent, EDIT_FOOD);
	}
	// Inner classes -------------------------------------------------

}
