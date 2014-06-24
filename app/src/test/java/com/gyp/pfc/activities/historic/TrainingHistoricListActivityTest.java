/**
 * 
 */
package com.gyp.pfc.activities.historic;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.builder.TrainingHistoricBuilder;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;
import com.xtremelabs.robolectric.tester.android.view.TestContextMenu;

/**
 * Tests for the {@link TrainingHistoricListActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class TrainingHistoricListActivityTest extends BaseTrainingHistoricTest {

	private static final String TRAINING_1 = "one";

	private static final String TRAINING_2 = "two";
	// Constants -----------------------------------------------------

	public static final byte DELETE_MENU_POS = 0;

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
	}

	@Test
	public void shouldListAllHistorics() throws ParseException, EntityNameException {
		// GIVEN
		prepareTestData();
		// WHEN
		// activity is created
		createActivity();
		// THEN
		// there are two entries on the list
		assertListSize(2);
		// data for the first entry is correctly filled
		assertTextOfListChild(0, R.id.historicDate, "06/01/2014");
		assertTextOfListChild(0, R.id.historicStart, "12:00");
		assertTextOfListChild(0, R.id.historicEnd, "13:00");
		assertTextOfListChild(0, R.id.historicTraining, TRAINING_2);
		assertTextOfListChild(0, R.id.historicCalories, "16 Kcal");
	}

	@Test
	public void shouldAskConfirmationForDeletion() throws EntityNameException, ParseException {
		// GIVEN
		prepareTestData();
		// activity is created
		createActivity();
		assertTextOfListChild(0, R.id.historicTraining, TRAINING_2);
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context menu delete (first)
		TestContextMenu.getLastContextMenu().clickOn(DELETE_MENU_POS);
		// THEN
		// question is asked for deletion
		assertAlertDialogText(R.string.assureHistoricDeletion);
		// click on yes
		clickYesOnDialog();
		// selected food is deleted
		assertEquals("There should be only one historic", 1, trainingHistoricDao.queryForAll().size());
		// toast with deletion message is shown
		assertToastText(TRAINING_2 + " " + getText(R.string.deleteMessage));
		// assert item is no longer on the list)
		assertTextOfListChild(0, R.id.historicTraining, TRAINING_1);
		assertNull(getItemFromListView(1));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new TrainingHistoricListActivity();
	}

	// Private -------------------------------------------------------

	private void prepareTestData() throws EntityNameException, ParseException {
		// one exercise
		Exercise e = exerciseManager.createExercise("one exercise", "one exercise", 100);
		// two trainings
		Training t1 = trainingManager.createTraining(TRAINING_1);
		trainingManager.addExerciseToTraining(t1, e, 3600, 1);
		Training t2 = trainingManager.createTraining(TRAINING_2);
		trainingManager.addExerciseToTraining(t2, e, 60, 10);
		// 1st of January @ 9 and 9:10
		Date first9 = DateUtils.parseDate("01/01/2014 09:00", new String[] { "dd/MM/yyyy HH:mm" });
		Date first10 = DateUtils.parseDate("01/01/2014 09:10", new String[] { "dd/MM/yyyy HH:mm" });
		// 6th of January 2014 @ 12 and 13
		Date sixth12 = DateUtils.parseDate("06/01/2014 12:00", new String[] { "dd/MM/yyyy HH:mm" });
		Date sixth13 = DateUtils.parseDate("06/01/2014 13:00", new String[] { "dd/MM/yyyy HH:mm" });
		// two historic
		TrainingHistoric one = new TrainingHistoricBuilder().id(1).training(t1).start(first9).end(first10)
				.getBuilt();
		trainingHistoricDao.create(one);
		TrainingHistoric two = new TrainingHistoricBuilder().id(2).training(t2).start(sixth12).end(sixth13)
				.getBuilt();
		trainingHistoricDao.create(two);
	}

	// Inner classes -------------------------------------------------

}
