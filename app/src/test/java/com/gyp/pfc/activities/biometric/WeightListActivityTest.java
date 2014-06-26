package com.gyp.pfc.activities.biometric;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.TestConstants;
import com.gyp.pfc.activities.constants.BiometricConstants;
import com.gyp.pfc.data.domain.biometric.Weight;
import com.gyp.pfc.data.domain.exception.EntityNameException;
import com.xtremelabs.robolectric.shadows.ShadowActivity.IntentForResult;
import com.xtremelabs.robolectric.tester.android.view.TestContextMenu;

/**
 * Tests for the {@link WeightListActivity}
 * 
 * @author alfergon
 * 
 */
@RunWith(CustomTestRunner.class)
public class WeightListActivityTest extends BaseWeightActivityTest implements TestConstants, BiometricConstants {

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
	public void shouldListAllWeightsOrderedByDateFromTheLatest() {
		// GIVEN
		// three dates with different dates on db
		prepareTestData();
		// WHEN
		// activity is started
		createActivity();
		// THEN
		// there are 3 entries
		assertListSize(3);
		// correctly ordered
		assertTextOfListChild(0, R.id.weightListItemWeight, "75.0");
		assertTextOfListChild(1, R.id.weightListItemWeight, "80.0");
		assertTextOfListChild(2, R.id.weightListItemWeight, "85.0");
	}

	@Test
	public void shouldAskConfirmationForDeletion() throws EntityNameException, ParseException {
		// GIVEN
		// three dates with different dates on db
		prepareTestData();
		// activity is created
		createActivity();
		assertTextOfListChild(1, R.id.weightListItemWeight, "80.0");
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context menu delete (first)
		TestContextMenu.getLastContextMenu().clickOn(DELETE_MENU_POS);
		// THEN
		// question is asked for deletion
		assertAlertDialogText(R.string.assureWeightDeletion);
		// click on yes
		clickYesOnDialog();
		// selected food is deleted
		assertEquals("There should be only one historic", 2, weightDao.queryForAll().size());
		// assert item is no longer on the list)
		assertTextOfListChild(1, R.id.weightListItemWeight, "85.0");
		assertNull(getItemFromListView(2));
	}

	@Test
	public void shouldEditHistoricViaContextMenu() throws EntityNameException, ParseException {
		// GIVEN
		// three dates with different dates on db
		prepareTestData();
		// activity is created
		createActivity();
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context edit (second option)
		TestContextMenu.getLastContextMenu().clickOn(EDIT_MENU_POS);
		// THEN
		// next activity is EditFoodActivity
		IntentForResult nextIntent = assertAndReturnNextActivityForResult(EditWeightActivity.class);
		Weight weight = (Weight) nextIntent.intent.getSerializableExtra(SELECTED_WEIGHT);
		assertEquals("Correct weight should have been sent", 75d, weight.getWeight(), 0);
	}

	@Test
	public void shouldRefreshAdadpterAfterEdition() throws EntityNameException, ParseException {
		// GIVEN
		// three dates with different dates on db
		prepareTestData();
		// activity is created
		createActivity();
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context edit delete (second option)
		TestContextMenu.getLastContextMenu().clickOn(EDIT_MENU_POS);
		Intent editionItent = activity.getNextStartedActivity();
		Weight weight = (Weight) editionItent.getSerializableExtra(SELECTED_WEIGHT);
		// edition is performed
		weight.setWeight(90d);
		weightDao.update(weight);
		// return from edition
		activity.receiveResult(editionItent, Activity.RESULT_OK, null);
		// THEN
		// edited historic on pos 0
		assertTextOfListChild(0, R.id.weightListItemWeight, "90.0");
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new WeightListActivity();
	}

	// Private -------------------------------------------------------

	private Weight createWeight(Date date, Double weight) {
		Weight ret = new Weight();
		ret.setDate(date);
		ret.setWeight(weight);
		weightDao.create(ret);
		return ret;
	}

	private void prepareTestData() {
		createWeight(new Date(), 80d);
		createWeight(DateUtils.addDays(new Date(), 1), 75d);
		createWeight(DateUtils.addDays(new Date(), -1), 85d);
	}

	// Inner classes -------------------------------------------------
}
