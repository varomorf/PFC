package com.gyp.pfc.activities.training;

import static com.xtremelabs.robolectric.Robolectric.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Training;
import com.j256.ormlite.dao.RuntimeExceptionDao;

@RunWith(CustomTestRunner.class)
public class AddTrainingActivityTest extends BaseActivityTest {

	// Constants -----------------------------------------------------
	private static final String TRAINING_NAME = "TRAINING_NAME";
	// Attributes ----------------------------------------------------
	private RuntimeExceptionDao<Training, Integer> dao;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		super.before();
		dao = new DatabaseHelper(realActivity).getTrainingDao();
	}

	@Test
	public void shouldAddTrainingWithOnlyName() {
		// GIVEN
		// activity is shown
		createActivity();
		// WHEN
		// name is set
		UIUtils.setTextToUI(activity.findViewById(R.id.trainningName),
				TRAINING_NAME);
		// save button is clicked
		clickOn(activity.findViewById(R.id.commitButton));
		// THEN
		// toast with message is shown
		assertToastText(R.string.trainingCreated);
		// training is saved
		List<Training> trainings = dao.queryForAll();
		assertThat(trainings.size(), is(1));
		Training training = trainings.get(0);
		assertThat(training.getName(), is(TRAINING_NAME));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new AddTrainingActivity();
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
