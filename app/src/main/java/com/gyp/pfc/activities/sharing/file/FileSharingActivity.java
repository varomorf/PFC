package com.gyp.pfc.activities.sharing.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import android.os.Environment;
import android.util.Log;

import com.gyp.pfc.activities.constants.FileSharingName;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.food.Food;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for sharing entities via file export.
 * 
 * The entites that can be exported are the ones on the {@link FileSharingName}
 * enum.
 * 
 * All files will be saved to external folder, under the directory
 * {@link FileSharingName#DATA_DIR_NAME}
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
	 */
	public void exportFoods() {
		List<Food> foods = getHelper().getFoodDao().queryForAll();
		File foodsFile = new File(getDataStorageDir(), FileSharingName.FOOD.getFileName());
		try {
			FileWriter writer = new FileWriter(foodsFile);
			new Yaml().dumpAll(foods.iterator(), writer);
		} catch (IOException e) {
			Log.e(LOG_TAG, "An error ocurred while exporting Food entities", e);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Returns the directory in which the data files must be present
	 * 
	 * @return the directory in which the data files must be present
	 */
	private File getDataStorageDir() {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				FileSharingName.DATA_DIR_NAME);
		if (!file.mkdirs()) {
			Log.e(LOG_TAG, "Directory not created");
		}
		return file;
	}

	// Inner classes -------------------------------------------------
}
