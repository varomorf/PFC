package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.FoodsListActivity;
import com.gyp.pfc.data.domain.Food;

/**
 * {@link ArrayAdapter} for the {@link FoodsListActivity}
 * 
 * @author Alvaro
 * 
 */
public class FoodListViewAdapter extends ArrayAdapter<Food> {
	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	private ViewHolder holder;
	private LayoutInflater inflater = null;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * Constructor
	 * 
	 * @param context
	 *            The current context.
	 * @param textViewResourceId
	 *            The resource ID for a layout file containing a TextView to use
	 *            when instantiating views.
	 * @param objects
	 *            The objects to represent in the ListView.
	 */
	public FoodListViewAdapter(Context context, int textViewResourceId,
			List<Food> foods) {
		super(context, textViewResourceId, foods);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(R.layout.food_list_item, null);
			// create a new holder
			holder = new ViewHolder();
			// retrieve the widgets
			holder.title = (TextView) view.findViewById(R.id.title);
			// set the holder as tag
			view.setTag(holder);
		} else {
			// get the holder from the view
			holder = (ViewHolder) view.getTag();
		}

		// populate the holder with the data
		Food food = getItem(position);

		holder.title.setText(food.getName());
		holder.title.setBackgroundColor(food.getColor());

		return view;
	}

	/**
	 * Sets the collection on the adapter to the contents of the passed list
	 */
	public void setFoods(List<Food> foods) {
		clear();
		for (Food food : foods) {
			add(food);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

	private class ViewHolder {
		TextView title;
	}

}
