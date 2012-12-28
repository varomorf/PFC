package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.activities.exercise.ExerciseListActivity;
import com.gyp.pfc.data.domain.Exercise;

/**
 * {@link ArrayAdapter} for the {@link ExerciseListActivity}
 * 
 * @author Alvaro
 * 
 */
public class ExerciseListViewAdapter extends ArrayAdapter<Exercise> {

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
	public ExerciseListViewAdapter(Context context, int textViewResourceId,
			List<Exercise> exercises) {
		super(context, textViewResourceId, exercises);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(R.layout.exercise_list_item, null);
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
		Exercise exercise = getItem(position);

		holder.title.setText(exercise.getName());

		return view;
	}

	/**
	 * Sets the collection on the adapter to the contents of the passed list
	 */
	public void setFoods(List<Exercise> exercises) {
		clear();
		for (Exercise exercise : exercises) {
			add(exercise);
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
