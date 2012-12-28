package com.gyp.pfc.activities.food;

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
public class FoodListActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foods_list);
		setListAdapter(new FoodListViewAdapter(this, R.layout.food_list_item,
				getFoods()));
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
		inflater.inflate(R.menu.crud_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.delete:
			deleteFood(info.position);
			return true;
		case R.id.edit:
			editFood(info.position);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case EditFoodActivity.EDIT_FOOD:
			if (resultCode == Activity.RESULT_OK) {
				// refresh UI
				refreshAdapter();
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
		// get selected food from adapter
		Food selectedFood = (Food) l.getAdapter().getItem(position);
		// prepare intent for showing food details view
		Intent intent = new Intent(getApplicationContext(),
				ShowFoodDetailsActivity.class);
		// put the selected food on the intent
		intent.putExtra(EditFoodActivity.FOOD_TO_EDIT, selectedFood);
		// start the activity with the intent
		startActivity(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// the activity is shown via back button
		refreshAdapter();
	}

	// Private -------------------------------------------------------

	private List<Food> getFoods() {
		return getHelper().getFoodDao().queryForAll();
	}

	private void deleteFood(int position) {
		// get selected food from adapter
		Food selectedFood = (Food) getListAdapter().getItem(position);
		// delete food on DB
		getHelper().getFoodDao().delete(selectedFood);
		// refresh the adapter to update UI
		refreshAdapter();
		// show deletion message
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
		editFoodIntent.putExtra(EditFoodActivity.FOOD_TO_EDIT, selectedFood);
		startActivityForResult(editFoodIntent, EditFoodActivity.EDIT_FOOD);
	}

	private void refreshAdapter() {
		FoodListViewAdapter adapter = (FoodListViewAdapter) getListAdapter();
		adapter.setFoods(getFoods());
		adapter.notifyDataSetChanged();
	}
	// Inner classes -------------------------------------------------

}
