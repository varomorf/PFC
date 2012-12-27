package com.gyp.pfc.activities.food;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.xtremelabs.robolectric.shadows.ShadowActivity;

/**
 * Test for the {@link AddFoodActivity}
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class AddFoodActivityTest {

	private AddFoodActivity activity;
	private Button addFoodButton;
	private Button calculateButton;
	private TextView calories;
	private TextView sugars;
	private TextView fats;

	@Before
	public void setUp() throws Exception {
		activity = new AddFoodActivity();
		activity.onCreate(null);
		addFoodButton = (Button) activity.findViewById(R.id.addFoodButton);
		calculateButton = (Button) activity.findViewById(R.id.calculateButton);
		calories = (TextView) activity.findViewById(R.id.caloriesText);
		sugars = (TextView) activity.findViewById(R.id.sugarsText);
		fats = (TextView) activity.findViewById(R.id.fatsText);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		calories.setText("100");
		sugars.setText("10");
		fats.setText("5");
		clickOn(calculateButton);
		addFoodButton.setVisibility(0);
		clickOn(addFoodButton);

		ShadowActivity shadowActivity = shadowOf(activity);
		Intent next = shadowActivity.getNextStartedActivity();
		assertNotNull(next);
		assertEquals(EnterFoodNameActivity.class.getName(), next.getComponent()
				.getClassName());
	}

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
