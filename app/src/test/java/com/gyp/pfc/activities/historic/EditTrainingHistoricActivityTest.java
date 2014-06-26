/**
 * 
 */
package com.gyp.pfc.activities.historic;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.activities.constants.ExerciseConstants;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;

/**
 * Tests for the {@link EditTrainingHistoricActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class EditTrainingHistoricActivityTest extends BaseTrainingHistoricTest implements ExerciseConstants {

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
	public void shouldFillUIWithDataFromPassedHistoric() throws ParseException, EntityNameException {
		// GIVEN
		prepareTestData();
		// one historic
		TrainingHistoric historic = trainingHistoricDao.queryForAll().get(0);
		// pass historic via intent
		Intent intent = new Intent();
		intent.putExtra(SELECTED_HISTORIC, historic);
		activity.setIntent(intent);
		// WHEN
		// activity starts
		createActivity();
		// THEN
		// data is loaded
		assertViewText(R.id.addTrainingHistoricExerciseName, historic.getTraining().getName());
		assertViewText(R.id.addTrainingHistoricStartingDate, TimeUtils.formatDateTime(historic.getStart()));
		assertViewText(R.id.addTrainingHistoricEndingDate, TimeUtils.formatDateTime(historic.getEnd()));
	}

	@Test
	public void shouldEditPassedHistoric() throws ParseException, EntityNameException {
		// GIVEN
		prepareTestData();
		// one historic
		TrainingHistoric historic = trainingHistoricDao.queryForAll().get(0);
		// pass historic via intent
		Intent intent = new Intent();
		intent.putExtra(SELECTED_HISTORIC, historic);
		activity.setIntent(intent);
		// WHEN
		// activity starts
		createActivity();
		// data changed
		((EditTrainingHistoricActivity) realActivity).onDateSet(null, 2014, 11, 12);
		((EditTrainingHistoricActivity) realActivity).onTimeSet(null, 20, 20);
		clickOn(activity.findViewById(R.id.okButton));
		// THEN
		// data is changed on DB
		trainingHistoricDao.refresh(historic);
		assertEquals("Ending date should have changed", "12/12/2014 20:20",
				TimeUtils.formatDateTime(historic.getEnd()));
		// result is OK
		assertEquals("Result was not OK", Activity.RESULT_OK, activity.getResultCode());
		// activity is finished
		assertTrue("Activity should be finishing", activity.isFinishing());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new EditTrainingHistoricActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
