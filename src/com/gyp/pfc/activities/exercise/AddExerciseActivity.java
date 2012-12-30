package com.gyp.pfc.activities.exercise;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for adding new exercises
 * 
 * @author Alvaro
 * 
 */
public class AddExerciseActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_exercise);
	}

	/**
	 * Call back for the add button
	 * 
	 * @param view
	 */
	public void addExercise(View view) {
		View name = findViewById(R.id.exerciseName);
		View description = findViewById(R.id.exerciseDescription);
		Exercise exercise = new Exercise();
		exercise.setName(UIUtils.getTextFromUI(name));
		if (assertExercise(exercise)) {
			exercise.setDescription(UIUtils.getTextFromUI(description));
			getHelper().getExerciseDao().create(exercise);
			Toast.makeText(getApplicationContext(), R.string.exerciseCreated,
					Toast.LENGTH_SHORT).show();
			UIUtils.clearView(name);
			UIUtils.clearView(description);
		}
	}

	// Private -------------------------------------------------------
	private boolean assertExercise(Exercise exercise) {
		return assertNotBlankName(exercise)
				&& assertNotDuplicatedName(exercise);
	}

	private boolean assertNotBlankName(Exercise exercise) {
		if (StringUtils.isBlank(exercise.getName())) {
			Toast.makeText(getApplicationContext(), R.string.exerciseNameBlank,
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private boolean assertNotDuplicatedName(Exercise exercise) {
		List<Exercise> tmp = getHelper().getExerciseDao().queryForEq("name",
				exercise.getName());
		if (tmp.size() != 0) {
			Toast.makeText(getApplicationContext(),
					R.string.exerciseNameDuplicated, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	// Inner classes -------------------------------------------------
}
