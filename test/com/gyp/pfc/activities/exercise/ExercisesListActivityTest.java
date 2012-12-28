package com.gyp.pfc.activities.exercise;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.xtremelabs.robolectric.shadows.ShadowListActivity;
import com.xtremelabs.robolectric.shadows.ShadowListView;
import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 * Tests for the ExerciseListActivity
 * 
 * @author Alvaro
 * 
 */
@RunWith(CustomTestRunner.class)
public class ExercisesListActivityTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	private ShadowListActivity activity;
	private RuntimeExceptionDao<Exercise, Integer> dao;
	protected ConnectionSource connectionSource;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		ExerciseListActivity realActivity = new ExerciseListActivity();
		activity = shadowOf(realActivity);
		OpenHelperManager.getHelper(realActivity, DatabaseHelper.class);
		dao = new DatabaseHelper(realActivity).getExerciseDao();
	}

	@Test
	public void shouldListAllExercises() {
		// GIVEN
		insertExercise("foo", "foo desc");
		insertExercise("bar", "bar desc");
		// WHEN
		activity.callOnCreate(null);
		// THEN
		assertItemText(getItemFromListView(0), "foo");
		assertItemText(getItemFromListView(1), "bar");
	}

	@Test
	public void shouldShowExerciseDetailsAfterClicking() {
		// GIVEN
		// list with exercises
		// WHEN
		// simple click on an item
		// THEN
		// FoodDetailActivity is shown
		fail();
	}

	@Test
	public void shouldShowInteractionMenuAfterLongClic() {
		// GIVEN
		insertExercise("foo", "foo desc");
		insertExercise("bar", "bar desc");
		activity.callOnCreate(null);
		// WHEN
		// long click on an item
		ListView listView = (ListView) activity.findViewById(android.R.id.list);
		listView.performLongClick();
		ShadowListView shadow = shadowOf(listView);
		shadow.performLongClick();
		View item = getItemFromListView(0);
		item.performLongClick();
		ShadowView shadowItem = shadowOf(item);
		shadowItem.performLongClick();
		// THEN
		// contextual menu is shown
		View contextMenu = activity.findViewById(R.menu.crud_context_menu);
		assertNotNull(contextMenu);
		// TODO FINISH LATER
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------
	private void insertExercise(String name, String description) {
		Exercise exercise = new Exercise();
		exercise.setName(name);
		exercise.setDescription(description);
		dao.create(exercise);
	}

	private void assertItemText(View item, String text) {
		TextView title = (TextView) item.findViewById(R.id.title);
		assertThat(title.getText().toString(), is(text));
	}

	private View getItemFromListView(int index) {
		ListView listView = (ListView) activity.findViewById(android.R.id.list);
		return listView.getChildAt(index);
	}
	// Inner classes -------------------------------------------------

}
