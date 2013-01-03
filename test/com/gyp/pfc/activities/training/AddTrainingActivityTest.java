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
		List<Training> trainings = dao.queryForAll();
		dao.delete(trainings);
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
		saveButtonIsClicked();
		// THEN
		// toast with message is shown
		assertToastText(R.string.trainingCreated);
		// training is saved
		List<Training> trainings = dao.queryForAll();
		assertThat(trainings.size(), is(1));
		Training training = trainings.get(0);
		assertThat(training.getName(), is(TRAINING_NAME));
	}

	@Test
	public void shouldFailWithEmptyName() {
		// GIVEN
		// activity is shown
		createActivity();
		// WHEN
		UIUtils.setTextToUI(activity.findViewById(R.id.trainningName), "");
		// save button is clicked
		saveButtonIsClicked();
		// THEN
		// toast with fail message is shown
		assertToastText(R.string.trainingNameBlank);
		// no training is saved
		List<Training> trainings = dao.queryForAll();
		assertThat(trainings.size(), is(0));
	}

	@Test
	public void shouldFailWithDuplicatedName() {
		// GIVEN
		// activity is shown
		createActivity();
		// training with duplicated name is on DB
		Training tmp = new Training();
		tmp.setName(TRAINING_NAME);
		dao.create(tmp);
		// WHEN
		UIUtils.setTextToUI(activity.findViewById(R.id.trainningName),
				TRAINING_NAME);
		// save button is clicked
		saveButtonIsClicked();
		// THEN
		// toast with fail message is shown
		assertToastText(R.string.trainingNameDuplicated);
		// no training is saved
		List<Training> trainings = dao.queryForAll();
		assertThat(trainings.size(), is(1));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected Activity newActivity() {
		return new AddTrainingActivity();
	}

	// Private -------------------------------------------------------
	private void saveButtonIsClicked() {
		clickOn(activity.findViewById(R.id.commitButton));
	}
	// Inner classes -------------------------------------------------
}
