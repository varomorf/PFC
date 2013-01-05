package com.gyp.pfc.dialogs;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Spinner;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.R.id;
import com.gyp.pfc.R.layout;
import com.gyp.pfc.adapters.ExerciseListViewAdapter;
import com.gyp.pfc.data.domain.Exercise;

/**
 * Dialog for adding exercises to a training
 * 
 * @author Alvaro
 * 
 */
public class AddExerciseDialog extends Dialog implements
		android.view.View.OnClickListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private AddExerciseDialogListener listener;
	private List<Exercise> exercises;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the context passed to the super constructor
	 * @param listener
	 *            A listener for the dialog's commit button
	 * @param exercises
	 *            A list of exercises to populate the dialog's spinner
	 */
	public AddExerciseDialog(Context context,
			AddExerciseDialogListener listener, List<Exercise> exercises) {
		super(context);
		this.listener = listener;
		this.exercises = exercises;
	}

	// Public --------------------------------------------------------

	/**
	 * Callback method for the save button
	 * 
	 * @param view
	 */
	public void onClick(View v) {
		// call the listener and dismiss
		listener.onDialogClosing(this);
		dismiss();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise_dialog);

		// set layout parameters so the dialog fills its width
		LayoutParams params = getWindow().getAttributes();
		params.width = LayoutParams.MATCH_PARENT;
		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);
		populateSpinner();
		fillValues();
		// on click listener for the button will be this
		findViewById(R.id.commitButton).setOnClickListener(this);
	}

	// Private -------------------------------------------------------

	private void populateSpinner() {
		// get spinner from view
		Spinner spinner = (Spinner) findViewById(R.id.exerciseSpinner);
		// prepare adapter for exercises list
		ExerciseListViewAdapter adapter = new ExerciseListViewAdapter(
				getContext(), R.layout.exercise_list_item, exercises);
		// set adapter to spinner
		spinner.setAdapter(adapter);
	}

	private void fillValues() {
		UIUtils.setTextToUI(findViewById(R.id.minutes), "0");
		UIUtils.setTextToUI(findViewById(R.id.seconds), "0");
		UIUtils.setTextToUI(findViewById(R.id.repetitions), "1");
	}

	// Inner classes -------------------------------------------------
	/**
	 * Defines a listener for the {@link AddExerciseDialog}
	 * 
	 * @author Alvaro
	 * 
	 */
	public interface AddExerciseDialogListener {
		/**
		 * <p>
		 * Callback method for when the dialog's commit button is clicked
		 * </p>
		 * 
		 * @param dialog
		 *            The dialog passes itself to the listener callback method
		 */
		void onDialogClosing(AddExerciseDialog dialog);
	}
}
