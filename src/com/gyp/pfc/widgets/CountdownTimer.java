package com.gyp.pfc.widgets;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.Chronometer;

/**
 * Chronometer extension for counting down seconds.
 * 
 * @author Alvaro
 * 
 */
public class CountdownTimer extends Chronometer {

	// Constants -----------------------------------------------------

	public static final int MILLIS_IN_ONE_SECOND = 1000;

	// Attributes ----------------------------------------------------

	private long remainingMillis;
	private int ticksFreq = 500;
	private CountDownTimer timer;

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
        String text = DateUtils.formatElapsedTime(seconds);
        setText(text);
	}

	/**
	 * <p>
	 * Makes the count down timer to work, so both starts and resumes it.
	 * </p>
	 */
	@Override
	public void start() {
		if(timer != null){
			// cancel previous timer if present
			pause();
		}
		// create new timer
		timer = new CountDownTimer(remainingMillis, ticksFreq) {

			@Override
			public void onTick(long millisUntilFinished) {
				// set text of the chronometer to show the change
				setBase(SystemClock.elapsedRealtime() - millisUntilFinished);
				// set remaining milliseconds to allow resuming
				remainingMillis = millisUntilFinished;
			}

			@Override
			public void onFinish() {
				// set text of the chronometer to zero
				setBase(SystemClock.elapsedRealtime());
				// assure that the remaining milliseconds will be 0
				remainingMillis = 0;
			}
		}.start();
	}

	/**
	 * Pauses the count down timer
	 */
	public void pause() {
		timer.cancel();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}