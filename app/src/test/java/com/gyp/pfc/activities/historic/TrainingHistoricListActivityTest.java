/**
 * 
 */
package com.gyp.pfc.activities.historic;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.TestConstants;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.activities.constants.ExerciseConstants;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;
import com.xtremelabs.robolectric.shadows.ShadowActivity.IntentForResult;
import com.xtremelabs.robolectric.tester.android.view.TestContextMenu;

/**
 * Tests for the {@link TrainingHistoricListActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class TrainingHistoricListActivityTest extends BaseTrainingHistoricTest implements ExerciseConstants,
		TestConstants {

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

	@Test
	public void shouldEditHistoricViaContextMenu() throws EntityNameException, ParseException {
		// GIVEN
		prepareTestData();
		// activity is created
		createActivity();
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context edit (second option)
		TestContextMenu.getLastContextMenu().clickOn(1);
		// THEN
		// next activity is EditFoodActivity
		IntentForResult nextIntent = assertAndReturnNextActivityForResult(EditTrainingHistoricActivity.class);
		TrainingHistoric historic = (TrainingHistoric) nextIntent.intent.getSerializableExtra(SELECTED_HISTORIC);
		assertThat(historic.getTraining().getName(), is(TRAINING_2));
	}

	@Test
	public void shouldRefreshAdadpterAfterEdition() throws EntityNameException, ParseException {
		// GIVEN
		prepareTestData();
		// activity is created
		createActivity();
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context edit delete (second option)
		TestContextMenu.getLastContextMenu().clickOn(1);
		Intent editionItent = activity.getNextStartedActivity();
		TrainingHistoric historic = (TrainingHistoric) editionItent.getSerializableExtra(SELECTED_HISTORIC);
		// edition is performed
		Date date = new Date();
		historic.setStart(date);
		trainingHistoricDao.update(historic);
		// return from edition
		activity.receiveResult(editionItent, Activity.RESULT_OK, null);
		// THEN
		// edited historic on pos 0
		assertTextOfListChild(0, R.id.historicDate, TimeUtils.formatDate(date));
		assertTextOfListChild(0, R.id.historicStart, TimeUtils.formatTime(date));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new TrainingHistoricListActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
