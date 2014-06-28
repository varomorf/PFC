package com.gyp.pfc.activities.sharing.file;

import static com.gyp.pfc.activities.constants.FileSharingName.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yaml.snakeyaml.Yaml;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.food.Food;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.xtremelabs.robolectric.shadows.ShadowActivity;

/**
 * @author alfergon
 * 
 */
@RunWith(CustomTestRunner.class)
public class FileSharingActivityTest extends BaseActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Before
	public void before() {
		super.before();
		FoodManager.getInstance().setFoodDao(new DatabaseHelper(realActivity).getFoodDao());
	}

	@Test
	public void shouldExportFoodEntities() throws FileNotFoundException {
		// GIVEN
		// two foods on DB
		FoodManager.getInstance().createFood("Arroz", 150d, 50d, 100d, 5d);
		FoodManager.getInstance().createFood("Pan", 250d, 10d, 150d, 10d);
		// WHEN
		// activity is created
		createActivity();
		// export of foods is requested
		((FileSharingActivity) realActivity).exportFoods();
		// THEN
		File file = new File(new File(ShadowActivity.EXTERNAL_FILES_DIR, DATA_DIR_NAME), FOOD.getFileName());
		// the file must exist
		assertTrue("The file for the export does not exist", file.isFile());
		// file should have expected data
		List<Food> foods = new ArrayList<Food>();
		for (Object o : new Yaml().loadAll(new FileInputStream(file))) {
			foods.add((Food) o);
		}
		assertEquals("There should have been data for two foods", 2, foods.size());
		// assert first food
		assertEquals("Arroz", foods.get(0).getName());
		assertEquals(150d, foods.get(0).getCalories(), 0);
		assertEquals(100d, foods.get(0).getCarbs(), 0);
		assertEquals(50d, foods.get(0).getProtein(), 0);
		assertEquals(5d, foods.get(0).getFats(), 0);
		// assert second food
		assertEquals("Pan", foods.get(1).getName());
		assertEquals(250d, foods.get(1).getCalories(), 0);
		assertEquals(150d, foods.get(1).getCarbs(), 0);
		assertEquals(10d, foods.get(1).getProtein(), 0);
		assertEquals(10d, foods.get(1).getFats(), 0);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new FileSharingActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
