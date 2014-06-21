package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;

/**
 * ArrayAdapter for TrainingExercise entity for the AddTrainingActivity
 * 
 * @author Alvaro
 * 
 */
public class TrainingExerciseAdapter extends ArrayAdapter<TrainingExercise> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The context to be used
	 * @param list
	 *            The list of TrainingExercises to use
	 */
	public TrainingExerciseAdapter(Context context, List<TrainingExercise> list) {
		super(context, R.layout.training_exercise_item, R.id.title, list);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
