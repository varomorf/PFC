package com.gyp.pfc.activities.sharing.file;

import static com.gyp.pfc.sharing.FileSharingName.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.food.Food;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.gyp.pfc.sharing.FileSharingName;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for sharing entities via file export.
 * 
 * The entities that can be exported are the ones on the {@link FileSharingName} enum.
 * 
 * All files will be saved to external folder, under the directory {@link FileSharingName#DATA_DIR_NAME}
 * 
 * @author alfergon
 * 
 */
public class FileSharingActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------

	private static final String LOG_TAG = FileSharingActivity.class.getName();

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Exports all the {@link Food} entries on DB to its corresponding file
	 * 
	 * @param view
	 *            the button that calls
	 */
	public void exportFoods(View view) {
		List<Food> foods = FoodManager.it().getAllFoods();
		new ExportingTask<Food>(this, FOOD).execute(foods);
	}

	/**
	 * Imports all the {@link Food} entries from its corresponding file if it exists.
	 * 
	 * Once loaded, they will be loaded on DB overriding exiting ones if name is equal.
	 * 
	 * @param view
	 *            the button that calls
	 */
	public void importFoods(View view) {
		new ImportingTask(this, FOOD).execute(new Closure() {

			@Override
			public Object call(Object it) {
				FoodManager.it().importFood((Food) it);
				return null;
			}
		});
	}

	/**
	 * Exports all the {@link Exercises} entries on DB to its corresponding file
	 * 
	 * @param view
	 *            the button that calls
	 */
	public void exportExercises(View view) {
		List<Exercise> exercises = ExerciseManager.it().getAllExercises();
		new ExportingTask<Exercise>(this, EXERCISE).execute(exercises);
	}

	/**
	 * Imports all the {@link Exercise} entries from its corresponding file if it exists.
	 * 
	 * Once loaded, they will be loaded on DB overriding exiting ones if name is equal.
	 * 
	 * @param view
	 *            the button that calls
	 */
	public void importExercises(View view) {
		new ImportingTask(this, EXERCISE).execute(new Closure() {

			@Override
			public Object call(Object it) {
				ExerciseManager.it().importExercise((Exercise) it);
				return null;
			}
		});
	}

	/**
	 * Exports all the {@link Training} entries on DB to its corresponding file
	 * 
	 * @param view
	 *            the button that calls
	 */
	public void exportTrainings(View view) {
		// only executable trainings
		List<Training> trainings = getHelper().getTrainingDao().queryForEq("executable", true);
		new ExportingTask<Training>(this, TRAINING).execute(trainings);
	}

	/**
	 * Imports all the {@link Training} entries from its corresponding file if it exists.
	 * 
	 * Once loaded, they will be loaded on DB overriding exiting ones if name is equal. The {@link Exercise}
	 * entities used on the trainings will not be overwritten, but new ones will also be added to DB.
	 * 
	 * @param view
	 *            the button that calls
	 */
	public void importTrainings(View view) {
		// create a map of existing Exercises with their name as key for preformance reasons
		final Map<String, Exercise> exercises = new HashMap<String, Exercise>();
		for (Exercise exercise : ExerciseManager.it().getAllExercises()) {
			exercises.put(exercise.getName(), exercise);
		}
		ExerciseManager.it().getAllExercises();
		new ImportingTask(this, TRAINING).execute(new Closure() {

			@Override
			public Object call(Object it) {
				TrainingManager.it().importTraining((Training) it, exercises);
				return null;
			}
		});
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_sharing);

		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			Log.i(LOG_TAG, "Can write");
		}
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
