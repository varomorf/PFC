/**
 * 
 */
package com.gyp.pfc.activities.sharing.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.concurrent.Callable;

import org.yaml.snakeyaml.Yaml;

import android.util.Log;

import com.gyp.pfc.R;
import com.gyp.pfc.sharing.FileSharingName;
import com.gyp.pfc.sharing.TrainingConstructor;

/**
 * Asynchronous task for importing entities from a YAML file into the application's DB
 * 
 * @author Alvaro
 * 
 */
public class ImportingTask extends BaseFileSharingTask {

	// Constants -----------------------------------------------------

	private static final String ERROR_IMPORTING = "An error ocurred while importing entities of type ";

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link ImportingTask} specifying its context
	 * 
	 * @param context
	 *            the context for the new {@link ImportingTask}
	 * @param fileSharingName
	 *            the name of the file to use
	 */
	public ImportingTask(FileSharingActivity context, FileSharingName fileSharingName) {
		super(context, fileSharingName, R.string.fileImportStarted, R.string.fileImportCompleted);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Void doInBackground(Object... params) {
		final Closure closure = (Closure) params[0];
		final File file = new File(getDataStorageDir(), fileSharingName.getFileName());
		computeNumberOfEntities(file);
		context.getHelper().getFoodDao().callBatchTasks(new Callable<Void>() {
			public Void call() throws SQLException {
				// counter for the publishing of the progress
				int i = 0;
				try {
					for (Object o : new Yaml(new TrainingConstructor()).loadAll(new FileInputStream(file))) {
						closure.call(o);
						publishProgress(i++);
					}
				} catch (FileNotFoundException e) {
					Log.e(LOG_TAG, ERROR_IMPORTING + fileSharingName.getClassName(), e);
				}
				return null;
			}
		});
		return null;
	}

	// Private -------------------------------------------------------

	/**
	 * Computes the approximate number of entities to be imported from the file size and the ratio of the
	 * {@link FileSharingName}
	 * 
	 * @param file
	 *            the imported file
	 */
	private void computeNumberOfEntities(File file) {
		entities = new Double((file.length() / 1000) / fileSharingName.getEntityKbSizeRatio());
	}

	// Inner classes -------------------------------------------------

}
