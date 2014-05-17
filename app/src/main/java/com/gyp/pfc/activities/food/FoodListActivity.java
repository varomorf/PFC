package com.gyp.pfc.activities.food;

import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
		setContentView(R.layout.entity_list);
		setListAdapter(new FoodListViewAdapter(this, R.layout.food_list_item, getFoods()));
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
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.crud_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
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
		if (requestCode == EditFoodActivity.EDIT_FOOD && resultCode == Activity.RESULT_OK) {
			onFoodEdited();
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// get selected food from adapter
		Food selectedFood = (Food) l.getAdapter().getItem(position);
		// prepare intent for showing food details view
		Intent intent = new Intent(getApplicationContext(), ShowFoodDetailsActivity.class);
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

	/**
	 * Deletes the selected food via a confirmation dialog
	 * 
	 * @param position
	 *            the position in the list of the selected food
	 */
	private void deleteFood(int position) {
		// get selected food from adapter and call helper with positive action
		Food selectedFood = (Food) getListAdapter().getItem(position);
		FoodActivityHelper.callFor(this).deleteWithDialog(deletionAction(selectedFood));
	}

	private void editFood(int position) {
		Food selectedFood = (Food) getListAdapter().getItem(position);
		Intent editFoodIntent = new Intent(getApplicationContext(), EditFoodActivity.class);
		editFoodIntent.putExtra(EditFoodActivity.FOOD_TO_EDIT, selectedFood);
		startActivityForResult(editFoodIntent, EditFoodActivity.EDIT_FOOD);
	}

	private void refreshAdapter() {
		FoodListViewAdapter adapter = (FoodListViewAdapter) getListAdapter();
		adapter.setFoods(getFoods());
		adapter.notifyDataSetChanged();
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
	 * Creates an {@link OnClickListener} that on click will delete the passed
	 * food from DB, refresh the adapter and show a toast to the user
	 * 
	 * @param food
	 *            the food to be deleted
	 * @return the {@link OnClickListener}
	 */
	private OnClickListener deletionAction(final Food food) {
		return new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// delete food on DB
				getHelper().getFoodDao().delete(food);
				// refresh the adapter to update UI
				refreshAdapter();
				// show deletion message
				Toast.makeText(getApplicationContext(),
						food.getName() + " " + getString(R.string.deleteFoodMessage), Toast.LENGTH_SHORT).show();
			}
		};
	}
	// Inner classes -------------------------------------------------

}
