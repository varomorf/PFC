package com.gyp.pfc.activities.exercise;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Exercise;
import com.xtremelabs.robolectric.tester.android.view.TestContextMenu;

/**
 * Tests for the ExerciseListActivity
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class ExercisesListActivityTest extends BaseExerciseTest {

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
		insertExercise("foo", "foo desc");
		insertExercise("bar", "bar desc");
		// WHEN
		createActivity();
		// THEN
		assertItemText(getItemFromListView(0), "foo");
		assertItemText(getItemFromListView(1), "bar");
	}

	@Test
	public void shouldShowExerciseDetailsAfterClicking() {
		// GIVEN
		listWithExercises();
		// WHEN
		ListView listView = (ListView) activity.findViewById(android.R.id.list);
		shadowOf(listView).performItemClick(0);
		// THEN
		Intent next = activity.getNextStartedActivity();
		assertNotNull(next);
		assertThat(next.getComponent().getClassName(),
				is(ExerciseDetailsActivity.class.getName()));
		Exercise exercise = (Exercise) next
				.getSerializableExtra(ExerciseListActivity.SELECTED_EXERCISE);
		assertThat(exercise.getName(), is("foo"));
	}

	@Test
	public void shouldShowInteractionMenuAfterLongClic() {
		// GIVEN
		listWithExercises();
		// WHEN
		// long click on an item
		getItemFromListView(0).performLongClick();
		// THEN
		// contextual menu is shown
		TestContextMenu contextMenu = TestContextMenu.getLastContextMenu();
		assertCRUDMenu(contextMenu);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new ExerciseListActivity();
	}

	// Private -------------------------------------------------------

	private void assertItemText(View item, String text) {
		TextView title = (TextView) item.findViewById(R.id.title);
		assertThat(title.getText().toString(), is(text));
	}

	private View getItemFromListView(int index) {
		ListView listView = (ListView) activity.findViewById(android.R.id.list);
		return listView.getChildAt(index);
	}

	private void listWithExercises() {
		insertExercise("foo", "foo desc");
		insertExercise("bar", "bar desc");
		createActivity();
	}

	// Inner classes -------------------------------------------------

}
