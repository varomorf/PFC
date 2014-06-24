/**
 * 
 */
package com.gyp.pfc.activities.historic;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.activities.constants.ExerciseConstants;
import com.gyp.pfc.activities.exercise.ExerciseListActivity;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;
import com.xtremelabs.robolectric.shadows.ShadowActivity.IntentForResult;
import com.xtremelabs.robolectric.shadows.ShadowDialog;

/**
 * Tests for the {@link AddTrainingHistoricActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class AddTrainingHistoricActivityTest extends BaseTrainingHistoricTest implements ExerciseConstants {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldAddExerciseWithStartingDateAndEndingDate() throws EntityNameException {
		// GIVEN
		// one exercise that burns 100 Kcal per hour
		Exercise e = exerciseManager.createExercise("foo", "foo desc", 100);
		// no training historics on DB
		assertTrue("There should be no training historic", trainingHistoricDao.queryForAll().isEmpty());
		// WHEN
		// activity starts
		createActivity();
		// exercise is selected
		selectExercise(e);
		// starting date is entered
		Date startingDate = new Date();
		enterStartingDate(startingDate);
		// ending date is entered (one hour later)
		Date endingDate = DateUtils.addHours(startingDate, 1);
		enterEndingDate(endingDate);
		// ok button is pressed
		clickOn(activity.findViewById(R.id.okButton));
		// THEN
		assertViewText(R.id.addTrainingHistoricExerciseName, e.getName());
		assertViewText(R.id.addTrainingHistoricStartingDate, TimeUtils.formatDateTime(startingDate));
		assertViewText(R.id.addTrainingHistoricEndingDate, TimeUtils.formatDateTime(endingDate));
		// a TrainingHistoric is created with the entered data
		assertEquals("There should be one training historic", 1, trainingHistoricDao.queryForAll().size());
		TrainingHistoric historic = trainingHistoricDao.queryForAll().get(0);
		assertEquals("Created historic should have one exercise", 1, historic.getTraining().getExercises().size());
		TrainingExercise the = (TrainingExercise) historic.getTraining().getExercises().toArray()[0];
		assertEquals("Created historic should have correct exercise", e.getName(), the.getExercise().getName());
		assertFalse("Created historic should have non executable training", historic.getTraining().isExecutable());
		assertEquals("Created historic should have correct starting date", TimeUtils.formatDateTime(startingDate),
				TimeUtils.formatDateTime(historic.getStart()));
		assertEquals("Created historic should have correct ending date", TimeUtils.formatDateTime(endingDate),
				TimeUtils.formatDateTime(historic.getEnd()));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new AddTrainingHistoricActivity();
	}

	// Private -------------------------------------------------------

	private void selectExercise(Exercise exercise) {
		clickOn(activity.findViewById(R.id.addTrainingHistoricExerciseButton));
		IntentForResult intent = assertAndReturnNextActivityForResult(ExerciseListActivity.class);
		assertTrue("Intent should be for returning exercise", intent.intent.getExtras()
				.getBoolean(RETURN_EXERCISE));
		intent.intent.putExtra(SELECTED_EXERCISE, exercise);
		activity.receiveResult(SELECT_EXERCISE, 0, intent.intent);
	}

	private void enterStartingDate(Date startingDate) {
		enterDate(R.id.addTrainingHistoricStartingDateButton, startingDate);
	}

	private void enterEndingDate(Date endingDate) {
		enterDate(R.id.addTrainingHistoricEndingDateButton, endingDate);
	}

	private void enterDate(int buttonId, Date date) {
		clickOn(activity.findViewById(buttonId));
		assertNotNull(ShadowDialog.getLatestDialog());
		ShadowDialog.reset();
		// mock calls to onDateSet
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		((OnDateSetListener) realActivity).onDateSet(null, year, month, day);
		// mock calls to onTimeSet
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		((OnTimeSetListener) realActivity).onTimeSet(null, hour, minutes);
	}

	// Inner classes -------------------------------------------------

}
