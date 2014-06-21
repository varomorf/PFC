package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;

/**
 * ArrayAdapter for Training entities
 * 
 * @author Alvaro
 * 
 */
public class TrainingAdapter extends ArrayAdapter<Training> {

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
	 * @param trainings
	 *            The list of trainings to be shown
	 */
	public TrainingAdapter(Context context, List<Training> trainings) {
		super(context, R.layout.entity_list, R.id.title, trainings);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(R.layout.training_list_item, null);
		}
		// populate the item with the data
		Training training = getItem(position);
		UIUtils.setTextToUI(view.findViewById(R.id.title), training.getName());
		UIUtils.setTextToUI(view.findViewById(R.id.time), TimeUtils.formatTime(sumSeconds(training)));

		return view;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	private int sumSeconds(Training training) {
		int seconds = 0;
		for (TrainingExercise te : training.getExercises()) {
			seconds += te.getSeconds() * te.getReps();
		}
		return seconds;
	}

	// Inner classes -------------------------------------------------
}
