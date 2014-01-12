package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
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
	public ExerciseListViewAdapter(Context context, int textViewResourceId,
			List<Exercise> exercises) {
		super(context, textViewResourceId, R.id.title, exercises);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(R.layout.exercise_list_item, null);
		}
		// populate the item with the data
		Exercise exercise = getItem(position);
		UIUtils.setTextToUI(view.findViewById(R.id.title), exercise.getName());

		return view;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
