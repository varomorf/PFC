package com.gyp.pfc.widgets;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.TextView;

import com.gyp.pfc.TimeUtils;

/**
 * Chronometer extension for counting down seconds.
 * 
 * @author Alvaro
 * 
 */
public class CountdownTimer extends TextView {

	// Constants -----------------------------------------------------

	public static final int MILLIS_IN_ONE_SECOND = 1000;

	// Attributes ----------------------------------------------------

	private long remainingMillis;
	private int ticksFreq = 100;
	private CountDownTimer timer;
	private boolean running;
	private CountdownTimerListener listener;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Constructor that uses super
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CountdownTimer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * Constructor that uses super
	 * 
	 * @param context
	 * @param attrs
	 */
	public CountdownTimer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Constructor that uses super
	 * 
	 * @param context
	 */
	public CountdownTimer(Context context) {
		super(context);
	}

	// Public --------------------------------------------------------

	/**
	 * <p>
	 * Sets the remainingMillis field with the milliseconds value of the passed
	 * parameter and also the text of the chronometer to the total time to be
	 * counted down.
	 * </p>
	 * 
	 * @param seconds
	 *            Total seconds to count down
	 */
	public void setSeconds(int seconds) {
		// set remaining milliseconds from passed arg
		remainingMillis = seconds * MILLIS_IN_ONE_SECOND;
		// set text of chronometer to total time to be counted down
		setTimeText();
	}

	/**
	 * <p>
	 * Makes the count down timer to work, so both starts and resumes it.
	 * </p>
	 */
	public void start() {
		if (timer != null) {
			// cancel previous timer if present
			pause();
		}
		// create new timer
		timer = new CountDownTimer(remainingMillis, ticksFreq) {

			@Override
			public void onTick(long millisUntilFinished) {
				// set remaining milliseconds to allow resuming
				remainingMillis = millisUntilFinished;
				setTimeText();
			}

			@Override
			public void onFinish() {
				// assure that the remaining milliseconds will be 0
				remainingMillis = 0;
				setTimeText();
				// notify listener if any
				if (null != listener) {
					listener.onFinish();
				}
			}
		}.start();
		running = true;
	}

	/**
	 * Pauses the count down timer
	 */
	public void pause() {
		if (timer != null) {
			timer.cancel();
		}
		running = false;
	}

	public boolean isRunning() {
		return running;
	}

	public CountdownTimerListener getListener() {
		return listener;
	}

	public void setListener(CountdownTimerListener listener) {
		this.listener = listener;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	/**
	 * Set text of the chronometer to show the change
	 */
	private void setTimeText() {
		long seconds = (long) Math.ceil(remainingMillis / MILLIS_IN_ONE_SECOND);
		String text = TimeUtils.formatTime((int) seconds);
		setText(text);
	}

	// Inner classes -------------------------------------------------

	/**
	 * Interface for an object that wants to listen for events from a
	 * CountdownTimer
	 * 
	 * @author Alvaro
	 * 
	 */
	public interface CountdownTimerListener {

		/**
		 * Callback for the on finish event of the CountdownTimer
		 */
		void onFinish();
	}
}
