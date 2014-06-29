package com.gyp.pfc.activities.sharing.file;

import static com.gyp.pfc.sharing.FileSharingName.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.food.Food;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.gyp.pfc.sharing.FileSharingName;
import com.gyp.pfc.sharing.TrainingRepresenter;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for sharing entities via file export.
 * 
 * The entites that can be exported are the ones on the {@link FileSharingName} enum.
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
		exportEntities(foods, FOOD);
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
		File file = new File(getDataStorageDir(), FOOD.getFileName());
		try {
			for (Object o : new Yaml().loadAll(new FileInputStream(file))) {
				Food food = (Food) o;
				FoodManager.it().importFood(food);
			}
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, "An error ocurred while importing entities of type " + FOOD.getClassName(), e);
		}
	}

	/**
	 * Exports all the {@link Exercises} entries on DB to its corresponding file
	 * 
	 * @param view
	 *            the button that calls
	 */
	public void exportExercises(View view) {
		List<Exercise> exercises = getHelper().getExerciseDao().queryForAll();
		exportEntities(exercises, EXERCISE);
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
		exportEntities(trainings, TRAINING);
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

	/**
	 * Exports all the entities on the passed {@link List} to the file specified on the {@link FileSharingName}
	 * 
	 * @param iterable
	 *            a {@link List} with the entities to export
	 * @param fileSharingName
	 *            the {@link FileSharingName} to use when exporting the entities
	 */
	private <T> void exportEntities(List<T> iterable, FileSharingName fileSharingName) {
		File exportFile = new File(getDataStorageDir(), fileSharingName.getFileName());
		Toast.makeText(this, getString(R.string.fileExportStarted), Toast.LENGTH_SHORT).show();
		try {
			FileWriter writer = new FileWriter(exportFile);
			new Yaml(new TrainingRepresenter()).dumpAll(iterable.iterator(), writer);
		} catch (IOException e) {
			Log.e(LOG_TAG, "An error ocurred while exporting entities of type " + fileSharingName.getClassName(),
					e);
		}
		Toast.makeText(this, getString(R.string.fileExportCompleted), Toast.LENGTH_SHORT).show();
	}

	/**
	 * Returns the directory in which the data files must be present
	 * 
	 * @return the directory in which the data files must be present
	 */
	private File getDataStorageDir() {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
				DATA_DIR_NAME);
		if (!file.mkdirs()) {
			Log.e(LOG_TAG, "Directory not created");
		}
		return file;
	}

	// Inner classes -------------------------------------------------
}
