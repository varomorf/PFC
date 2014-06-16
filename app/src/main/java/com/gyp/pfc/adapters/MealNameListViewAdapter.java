/**
 * 
 */
package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.MealName;

/**
 * {@link ArrayAdapter} for the {@link MealName} entities
 * 
 * @author Alvaro
 * 
 */
public class MealNameListViewAdapter extends ArrayAdapter<MealName> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private LayoutInflater inflater = null;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link MealNameListViewAdapter} specifying its context, and
	 * the list of {@link MealName} entities to use
	 * 
	 * @param context
	 *            The current context.
	 * @param textViewResourceId
	 *            The resource ID for a layout file containing a TextView to use
	 *            when instantiating views.
	 * @param objects
	 *            The objects to represent in the ListView.
	 */
	public MealNameListViewAdapter(Context context, List<MealName> names) {
		super(context, R.layout.meal_name_list_item, R.id.name, names);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(R.layout.meal_name_list_item, null);
		}

		// populate the item with the data
		MealName name = getItem(position);
		UIUtils.setTextToUI(view.findViewById(R.id.name), name.getName());

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
