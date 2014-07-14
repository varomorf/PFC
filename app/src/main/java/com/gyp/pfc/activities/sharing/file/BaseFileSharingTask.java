/**
 * 
 */
package com.gyp.pfc.activities.sharing.file;

import static com.gyp.pfc.sharing.FileSharingName.*;

import java.io.File;

import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.monitoring.TimeMonitor;
import com.gyp.pfc.sharing.FileSharingName;

/**
 * @author Alvaro
 * 
 */
public abstract class BaseFileSharingTask extends AsyncTask<Object, Integer, Void> {

	// Constants -----------------------------------------------------

	protected static final String LOG_TAG = "FileSharingTask";

	// Attributes ----------------------------------------------------

	/** The context used for showing Toast notifications */
	protected FileSharingActivity context;

	/** The name of the file used */
	protected FileSharingName fileSharingName;

	/** The progress bar to update */
	protected ProgressBar progressBar;

	/** The number of entities that will be handled */
	protected Double entities;

	/** Message to show when starting the task */
	protected String startMessage;

	/** Message to show when ending the task */
	protected String endMessage;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link BaseFileSharingTask} specifying its context
	 * 
	 * @param context
	 *            the context for the new {@link BaseFileSharingTask}
	 * @param fileSharingName
	 *            the name of the file to use
	 * @param startMessageId
	 *            the id of the message to show when starting the task
	 * @param endMessageId
	 *            the id of the message to show when ending the task
	 */
	public BaseFileSharingTask(FileSharingActivity context, FileSharingName fileSharingName, int startMessageId,
			int endMessageId) {
		super();
		this.context = context;
		this.fileSharingName = fileSharingName;
		progressBar = (ProgressBar) context.findViewById(R.id.fileSharingProgressBar);
		progressBar.setProgress(0);
		startMessage = context.getString(startMessageId);
		endMessage = context.getString(endMessageId);
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onPreExecute() {
		Toast.makeText(context, startMessage, Toast.LENGTH_SHORT).show();
		TimeMonitor.start();
	}

	@Override
	protected void onPostExecute(Void result) {
		TimeMonitor.stop();
		progressBar.setProgress(100);
		Toast.makeText(context, endMessage, Toast.LENGTH_SHORT).show();
		Toast.makeText(context, TimeMonitor.getDataFor(entities.longValue()), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		Double percentaje = values[0] / entities;
		int progress = new Double(percentaje * 100).intValue();
		progressBar.setProgress(progress);
	}

	/**
	 * Returns the directory in which the data files must be present
	 * 
	 * @return the directory in which the data files must be present
	 */
	protected File getDataStorageDir() {
		// Get the directory for the user's public pictures directory.
		File dir = Environment.getExternalStoragePublicDirectory(DATA_DIR_NAME);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
