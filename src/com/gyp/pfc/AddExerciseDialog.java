package com.gyp.pfc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager.LayoutParams;

/**
 * Dialog for adding exercises to a trainning
 * 
 * @author Alvaro
 * 
 */
public class AddExerciseDialog extends Dialog {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	public AddExerciseDialog(Context context) {
		super(context);
	}

	// Public --------------------------------------------------------

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
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
