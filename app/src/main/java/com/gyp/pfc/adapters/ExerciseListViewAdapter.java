package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Exercise;

/**
 * ArrayAdapter for the ExerciseListActivity
 * 
 * @author Alvaro
 * 
 */
public class ExerciseListViewAdapter extends ArrayAdapter<Exercise> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

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
	public ExerciseListViewAdapter(Context context, int textViewResourceId, List<Exercise> exercises) {
		super(context, textViewResourceId, R.id.title, exercises);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
