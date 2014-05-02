package com.gyp.pfc.activities.food;

import static com.xtremelabs.robolectric.Robolectric.clickOn;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.widget.Button;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.manager.FoodManager;

/**
 * Test for the {@link AddFoodActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class AddFoodActivityTest extends BaseFoodTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
		List<Food> foods = dao.queryForAll();
		dao.delete(foods);
		FoodManager.getInstance().setFoodDao(dao);
	}

	@Test
	public void shouldSaveFood() {
		// GIVEN
		createActivity();
		// WHEN
		enterFoodName(FOOD_NAME);
		enterFoodBrand(FOOD_BRAND);
		enterFoodCalories(FOOD_CALORIES);
		enterFoodCarbs(FOOD_CARBS);
		enterFoodSugar(FOOD_SUGAR);
		enterFoodFiber(FOOD_FIBER);
		enterFoodFats(FOOD_FATS);
		enterFoodSaturatedFats(FOOD_SATURATED_FATS);
		enterFoodProteins(FOOD_PROTEINS);
		enterFoodSodium(FOOD_SODIUM);
		saveFood();
		// THEN
		// there is one food
		List<Food> foods = dao.queryForAll();
		assertThat(foods.size(), is(1));
		// saved food has entered data
		Food food = foods.get(0);
		assertThat(food.getName(), is(FOOD_NAME));
		assertThat(food.getBrandName(), is(FOOD_BRAND));
		assertThat(food.getCalories(), is(FOOD_CALORIES));
		assertThat(food.getProtein(), is(FOOD_PROTEINS));
		assertThat(food.getCarbs(), is(FOOD_CARBS));
		assertThat(food.getSugar(), is(FOOD_SUGAR));
		assertThat(food.getFiber(), is(FOOD_FIBER));
		assertThat(food.getFats(), is(FOOD_FATS));
		assertThat(food.getSaturatedFats(), is(FOOD_SATURATED_FATS));
		assertThat(food.getSodium(), is(FOOD_SODIUM));
		// toast is shown
		assertToastText(R.string.newFoodInserted);
		// activity finishes
		assertTrue(activity.isFinishing());
	}

	@Test
	public void shouldAssureObligatoryFields() {
		// GIVEN
		createActivity();
		// WHEN
		saveFood();
		// THEN
		List<Food> foods = dao.queryForAll();
		assertThat(foods.size(), is(0));
		// AND
		enterFoodName(FOOD_NAME);
		saveFood();
		// THEN
		foods = dao.queryForAll();
		assertThat(foods.size(), is(0));
		// AND
		enterFoodCalories(FOOD_CALORIES);
		saveFood();
		// THEN
		foods = dao.queryForAll();
		assertThat(foods.size(), is(0));
		// AND
		enterFoodCarbs(FOOD_CARBS);
		saveFood();
		// THEN
		foods = dao.queryForAll();
		assertThat(foods.size(), is(0));
		// AND
		enterFoodFats(FOOD_FATS);
		saveFood();
		// THEN
		foods = dao.queryForAll();
		assertThat(foods.size(), is(0));
		// AND
		enterFoodProteins(FOOD_PROTEINS);
		saveFood();
		// THEN
		foods = dao.queryForAll();
		// all required fields are filled -> food is created
		assertThat(foods.size(), is(1));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new AddFoodActivity();
	}

	// Private -------------------------------------------------------

	private void enterFoodCalories(double foodCalories) {
		enterText(R.id.caloriesText, foodCalories);
	}

	private void enterFoodName(String foodName) {
		enterText(R.id.foodNameText, foodName);
	}

	private void enterFoodBrand(String foodBrand) {
		enterText(R.id.foodBrandText, foodBrand);
	}

	private void enterFoodProteins(double foodProteins) {
		enterText(R.id.proteinsText, foodProteins);
	}

	private void enterFoodCarbs(double foodCarbs) {
		enterText(R.id.carbsText, foodCarbs);
	}

	private void enterFoodSugar(double foodSugar) {
		enterText(R.id.sugarText, foodSugar);
	}

	private void enterFoodFiber(double foodFiber) {
		enterText(R.id.fiberText, foodFiber);
	}

	private void enterFoodFats(double foodFats) {
		enterText(R.id.fatsText, foodFats);
	}

	private void enterFoodSaturatedFats(double footSaturatedFats) {
		enterText(R.id.saturatedFatsText, footSaturatedFats);
	}

	private void enterFoodSodium(double foodSodium) {
		enterText(R.id.sodiumText, foodSodium);
	}

	private void saveFood() {
		Button button = (Button) activity.findViewById(R.id.commitButton);
		clickOn(button);
	}

	// Inner classes -------------------------------------------------
}
