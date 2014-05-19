package com.gyp.pfc.activities.food;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.view.View;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.xtremelabs.robolectric.tester.android.view.TestContextMenu;

/**
 * Test for the {@link FoodListActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class FoodListActivityTest extends BaseFoodTest {

	// Constants -----------------------------------------------------

	public static final byte DELETE_MENU_POS = 0;

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Prepares the DB for having always the same foods for each test
	 */
	@Before
	public void resetFoodDB() {
		super.before();
		List<Food> foods = dao.queryForAll();
		dao.delete(foods);
		FoodManager.getInstance().setFoodDao(dao);
		FoodManager.getInstance().createFood("Arroz", "", 364, 6.67, 81.60, 0, 1.4, 0.9, 0.19, 0.0039);
		FoodManager.getInstance().createFood("Huevo", "", 162, 12.68, 0.68, 0, 0, 12.10, 3.3, 0.144);
		FoodManager.getInstance()
				.createFood("Pan blanco", "Mercadona", 261, 8.47, 51.50, 0, 3.5, 1.6, 0.39, 0.540);
	}

	@Test
	public void shouldListAllFoodsWithTheirNameAndBrand() {
		// GIVEN
		// resetFoodDB
		// WHEN
		// activity is created
		createActivity();
		// THEN
		// assert name and brand name for each food listed
		assertFoodName(getItemFromListView(0), "Arroz");
		assertFoodBrandName(getItemFromListView(0), "");
		assertFoodName(getItemFromListView(1), "Huevo");
		assertFoodBrandName(getItemFromListView(1), "");
		assertFoodName(getItemFromListView(2), "Pan blanco");
		assertFoodBrandName(getItemFromListView(2), "Mercadona");
	}

	@Test
	public void shouldAskConfirmationForDeletion() {
		// GIVEN
		// resetFoodDB
		// expected food name on pos 0
		String foodName = "Arroz";
		// expected food name on pos 1
		String nextFoodName = "Huevo";
		// activity is created
		createActivity();
		assertFoodName(getItemFromListView(0), foodName);
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context menu delete (first)
		TestContextMenu.getLastContextMenu().clickOn(DELETE_MENU_POS);
		// THEN
		// question is asked for deletion
		assertAlertDialogText(R.string.assureFoodDeletion);
		// click on yes
		clickYesOnDialog();
		// selected food is deleted
		assertEquals("There should be no foods", 0, dao.queryForEq("name", foodName).size());
		// toast with deletion message is shown
		assertToastText(foodName + " " + getText(R.string.deleteFoodMessage));
		// assert item is no longer on the list)
		assertFoodName(getItemFromListView(0), nextFoodName);
		assertNull(getItemFromListView(2));
	}

	@Test
	public void shouldSearchFoodsWithANameOfAtLeast3Characters() {
		// GIVEN
		String expectedName = "Huevo";
		String searchText = "hue";
		// activity is created
		createActivity();
		// check previous number of items
		assertListSize(3);
		// WHEN
		// search name with 3 characters
		setSearchQuery(searchText);
		// THEN
		// only one item left
		assertListSize(1);
		// is the expected item
		assertFoodName(getItemFromListView(0), expectedName);
	}
	
	@Test
	public void shouldNotSearchFoodsIfNameIsNotOfAtLeast3Characters() {
		// GIVEN
		String searchText = "h";
		// activity is created
		createActivity();
		// check previous number of items
		assertListSize(3);
		// WHEN
		// search name with 1 character
		setSearchQuery(searchText);
		// THEN
		// still 3 items present
		assertListSize(3);
	}
	
	@Test
	public void shouldListAllFoodsIfNameIsBlank() {
		// GIVEN
		String searchText = "hue";
		// activity is created
		createActivity();
		// search name with 3 character
		setSearchQuery(searchText);
		// check previous number of items
		assertListSize(1);
		// WHEN
		// search empty name
		setSearchQuery("");
		// THEN
		// still 3 items present
		assertListSize(3);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new FoodListActivity();
	}

	// Private -------------------------------------------------------

	private void assertFoodName(View item, String text) {
		assertItemText(item, R.id.foodName, text);
	}

	private void assertFoodBrandName(View item, String text) {
		assertItemText(item, R.id.foodBrandName, text);
	}

	private void setSearchQuery(String text) {
		((FoodListActivity) realActivity).onQueryTextChange(text);
	}

	// Inner classes -------------------------------------------------

}
