/**
 * 
 */
package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.gyp.pfc.R;
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
	public MealNameListViewAdapter(Context context, int textViewResourceId, List<MealName> names) {
		super(context, textViewResourceId, R.id.name, names);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
