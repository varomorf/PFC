package com.gyp.pfc.adapters;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.food.Food;

/**
 * ArrayAdapter for the FoodListActivity
 * 
 * @author Alvaro
 * 
 */
public class FoodListViewAdapter extends ArrayAdapter<Food> {
	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	
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
	public FoodListViewAdapter(Context context, int textViewResourceId, List<Food> foods) {
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
		}
		// get title from view
		TextView foodName = (TextView) view.findViewById(R.id.foodName);
		TextView foodBrandName = (TextView) view.findViewById(R.id.foodBrandName);

		// populate the item with the data
		Food food = getItem(position);
		foodName.setText(food.getName());
		if(StringUtils.isNotBlank(food.getBrandName())){
			foodBrandName.setText(food.getBrandName());
		}

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

}
