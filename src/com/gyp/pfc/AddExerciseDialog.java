package com.gyp.pfc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager.LayoutParams;

/**
 * Dialog for adding exercises to a training
 * 
 * @author Alvaro
 * 
 */
public class AddExerciseDialog extends Dialog {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private AddExerciseDialogListener listener;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	public AddExerciseDialog(Context context, AddExerciseDialogListener listener) {
		super(context);
		this.listener = listener;
	}

	// Public --------------------------------------------------------

	/**
	 * Callback method for the save button
	 * 
	 * @param view
	 */
	public void commitButton(View view) {
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
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
	public interface AddExerciseDialogListener {
		void onDialogClosing(AddExerciseDialog dialog);
	}
}
