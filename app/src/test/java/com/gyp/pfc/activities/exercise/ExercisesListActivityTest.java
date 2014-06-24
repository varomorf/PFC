package com.gyp.pfc.activities.exercise;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.activities.constants.ExerciseConstants;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.xtremelabs.robolectric.tester.android.view.TestContextMenu;

/**
 * Tests for the ExerciseListActivity
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class ExercisesListActivityTest extends BaseExerciseTest implements ExerciseConstants {

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
	public void shouldListAllExercises() {
		// GIVEN
		insertExercise("foo", "foo desc", 0);
		insertExercise("bar", "bar desc", 0);
		// WHEN
		createActivity();
		// THEN
		assertItemTitle(getItemFromListView(0), "foo");
		assertItemTitle(getItemFromListView(1), "bar");
	}

	@Test
	public void shouldShowExerciseDetailsAfterClicking() {
		// GIVEN
		listWithExercises();
		// WHEN
		clickOnListItem(0);
		// THEN
		Intent next = assertAndReturnNextActivity(ExerciseDetailsActivity.class);
		Exercise exercise = (Exercise) next.getSerializableExtra(ExerciseListActivity.SELECTED_EXERCISE);
		assertThat(exercise.getName(), is("foo"));
	}

	@Test
	public void shouldShowInteractionMenuAfterLongClic() {
		// GIVEN
		listWithExercises();
		// WHEN
		// long click on an item
		longClickOnListItem(0);
		// THEN
		// contextual menu is shown
		TestContextMenu contextMenu = TestContextMenu.getLastContextMenu();
		assertCRUDMenu(contextMenu);
	}

	@Test
	public void shouldDeleteExerciseViaContextMenu() {
		// GIVEN
		listWithExercises();
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context menu delete (first)
		TestContextMenu.getLastContextMenu().clickOn(0);
		// THEN
		// selected exercise is deleted
		assertNull(dao.queryForId(0));
		// toast with deletion message is shown
		assertToastText(R.string.exerciseDeleted);
		// assert item is no longer on the list
		assertItemTitle(getItemFromListView(0), "bar");
		assertNull(getItemFromListView(1));
	}

	@Test
	public void shouldRefreshListViewOnResume() {
		// GIVEN
		listWithExercises();
		// WHEN
		// one exercise is deleted
		dao.deleteById(1);
		// activity is resumed
		activity.callOnResume();
		// THEN
		// list must only show one Exercise
		assertItemTitle(getItemFromListView(0), "bar");
		assertNull(getItemFromListView(1));
	}

	@Test
	public void shouldEditExerciseViaContextMenu() {
		// GIVEN
		listWithExercises();
		// WHEN
		// long click on first item
		longClickOnListItem(0);
		// click on context edit delete (second)
		TestContextMenu.getLastContextMenu().clickOn(1);
		// THEN
		// next activity is EditExerciseActivity
		Intent nextIntent = activity.getNextStartedActivity();
		assertThat(nextIntent.getComponent().getClassName(), is(EditExerciseActivity.class.getName()));
		Exercise exercise = (Exercise) nextIntent.getSerializableExtra(ExerciseListActivity.SELECTED_EXERCISE);
		assertThat(exercise.getName(), is(dao.queryForId(1).getName()));
	}

	@Test
	public void shouldSelectExerciseForReturn() {
		// GIVEN
		String name = "foo";
		insertExercise(name, "foo desc", 0);
		// pass intent with boolean value
		Intent intent = new Intent();
		intent.putExtra(RETURN_EXERCISE, true);
		activity.setIntent(intent);
		// activity started
		createActivity();
		// WHEN
		// first food is selected
		clickOnListItem(0);
		// THEN
		// food is returned as result
		Intent result = activity.getResultIntent();
		Exercise exercise = (Exercise) result.getExtras().get(SELECTED_EXERCISE);
		assertEquals("Should have returned the selected exercise as result", name, exercise.getName());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ExerciseListActivity();
	}

	// Private -------------------------------------------------------

	private void listWithExercises() {
		insertExercise("foo", "foo desc", 0);
		insertExercise("bar", "bar desc", 0);
		createActivity();
	}

	// Inner classes -------------------------------------------------

}
