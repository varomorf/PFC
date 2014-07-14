/**
 * 
 */
package com.gyp.pfc.activities.sharing.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import android.util.Log;

import com.gyp.pfc.R;
import com.gyp.pfc.sharing.FileSharingName;
import com.gyp.pfc.sharing.TrainingRepresenter;

/**
 * Asynchronous task for exporting entities from a YAML file into the application's DB
 * 
 * @author Alvaro
 * 
 */
public class ExportingTask<T> extends BaseFileSharingTask {

	// Constants -----------------------------------------------------

	private static final String ERROR_EXPORTING = "An error ocurred while exporting entities of type ";

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link ExportingTask} specifying its context
	 * 
	 * @param context
	 *            the context for the new {@link ExportingTask}
	 * @param fileSharingName
	 *            the name of the file to use
	 */
	public ExportingTask(FileSharingActivity context, FileSharingName fileSharingName) {
		super(context, fileSharingName, R.string.fileExportStarted, R.string.fileExportCompleted);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Void doInBackground(Object... params) {
		@SuppressWarnings("unchecked")
		final List<T> list = (List<T>) params[0];
		final File file = new File(getDataStorageDir(), fileSharingName.getFileName());
		entities = new Double(list.size());
		context.getHelper().getFoodDao().callBatchTasks(new Callable<Void>() {
			@Override
			public Void call() throws SQLException {
				// counter for the publishing of the progress
				int i = 0;
				// make explicit the start of each of the list items
				DumperOptions options = new DumperOptions();
				options.setExplicitStart(true);
				try {
					FileWriter output = new FileWriter(file);
					for (T object : list) {
						new Yaml(new TrainingRepresenter(), options).dump(object, output);
						publishProgress(i++);
					}
				} catch (IOException e) {
					Log.e(LOG_TAG, ERROR_EXPORTING + fileSharingName.getClassName(), e);
				}
				return null;
			}
		});
		return null;
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
