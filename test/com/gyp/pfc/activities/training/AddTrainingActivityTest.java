package com.gyp.pfc.activities.training;

import static com.gyp.pfc.UIUtils.*;
import static com.xtremelabs.robolectric.Robolectric.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;

import com.gyp.pfc.CustomTestRunner;
import com.gyp.pfc.R;
import com.gyp.pfc.activities.BaseActivityTest;
import com.gyp.pfc.activities.exercise.BaseExerciseTest;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Training;
import com.gyp.pfc.data.domain.TrainingExercise;
import com.gyp.pfc.dialogs.AddExerciseDialog;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.xtremelabs.robolectric.shadows.ShadowDialog;

@RunWith(CustomTestRunner.class)
public class AddTrainingActivityTest extends BaseActivityTest {

	// Constants -----------------------------------------------------
	private static final String TRAINING_NAME = "TRAINING_NAME";
	private static final String NEW_EXERCISE_NAME = "NEW_EXERCISE_NAME";
	private static final String NEW_EXERCISE_DESC = "NEW_EXERCISE_DESC";
	// Attributes ----------------------------------------------------
	private RuntimeExceptionDao<Training, Integer> trainingDao;
	private RuntimeExceptionDao<Exercise, Integer> exerciseDao;
	private RuntimeExceptionDao<TrainingExercise, Integer> trainingExerciseDao;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	@Before
	public void before() {
		super.before();
		// init DAOs
		trainingDao = new DatabaseHelper(realActivity).getTrainingDao();
		exerciseDao = new DatabaseHelper(realActivity).getExerciseDao();
		trainingExerciseDao = new DatabaseHelper(realActivity)
				.getTrainingExerciseDao();
		List<Training> trainings = trainingDao.queryForAll();
		trainingDao.delete(trainings);
		List<Exercise> exercises = exerciseDao.queryForAll();
		exerciseDao.delete(exercises);
		// reset shadows for testing
		ShadowDialog.reset();
	}

	@Test
	public void shouldAddTrainingWithOnlyName() {
		// GIVEN
		// activity is shown
		createActivity();
		// WHEN
		// name is set
		setTextToUI(activity.findViewById(R.id.trainningName), TRAINING_NAME);
		// save button is clicked
		saveButtonIsClicked();
		// THEN
		// toast with message is shown
		assertToastText(R.string.trainingCreated);
		// training is saved
		List<Training> trainings = trainingDao.queryForAll();
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
		setTextToUI(activity.findViewById(R.id.trainningName), "");
		// save button is clicked
		saveButtonIsClicked();
		// THEN
		// toast with fail message is shown
		assertToastText(R.string.trainingNameBlank);
		// no training is saved
		List<Training> trainings = trainingDao.queryForAll();
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
		trainingDao.create(tmp);
		// WHEN
		setTextToUI(activity.findViewById(R.id.trainningName), TRAINING_NAME);
		// save button is clicked
		saveButtonIsClicked();
		// THEN
		// toast with fail message is shown
		assertToastText(R.string.trainingNameDuplicated);
		// no training is saved
		List<Training> trainings = trainingDao.queryForAll();
		assertThat(trainings.size(), is(1));
	}

	@Test
	public void shouldStartDialog() {
		// GIVEN
		prepareWithExerciseAndName();
		// WHEN
		// add button is clicked
		clickOn(activity.findViewById(R.id.addExerciseButton));
		// THEN
		// add exercise to training dialog is shown
		Dialog dialog = ShadowDialog.getLatestDialog();
		assertThat(dialog, is(AddExerciseDialog.class));
		// dialog has list of exercises
		Spinner spinner = (Spinner) dialog.findViewById(R.id.exerciseSpinner);
		assertThat(spinner.getAdapter().getCount(), is(1));
		Exercise exercise = (Exercise) spinner.getAdapter().getItem(0);
		assertThat(exercise.getName(), is(BaseExerciseTest.EXERCISE_NAME));
		assertThat(exercise.getDescription(),
				is(BaseExerciseTest.EXERCISE_DESC));
	}

	@Test
	public void shouldNotStartDialogAndShowToast() {
		// GIVEN
		// no exercises on DB
		assertThat(exerciseDao.countOf(), is(0l));
		// activity is shown
		createActivity();
		// name is set
		setTextToUI(activity.findViewById(R.id.trainningName), TRAINING_NAME);
		// WHEN
		// add button is clicked
		clickOn(activity.findViewById(R.id.addExerciseButton));
		// THEN
		// toast is shown with error message
		assertToastText(R.string.emptyExercisesList);
		// no dialog is shown
		assertNull(ShadowDialog.getLatestDialog());
	}

	@Test
	public void shouldAddExerciseToTraining() {
		// GIVEN
		prepareWithExerciseAndName();
		// WHEN
		addExerciseToTraining();
		// THEN
		// exercise has been added to training with correct order
		TrainingExercise te = trainingExerciseDao.queryForId(1);
		assertNotNull(te);
		assertThat(te.getExercise().getName(), is(exerciseDao.queryForId(1)
				.getName()));
		// 90 seconds = 1 min 30 secs
		assertThat(te.getSeconds(), is(90));
		assertThat(te.getReps(), is(5));
		assertThat(te.getPos(), is(0));
		assertNotNull(te.getTraining());
		// UI shows new exercise
		assertChildrenNumber(activity.findViewById(R.id.exercisesLayout), 1);
	}

	@Test
	public void shouldRemoveExerciseFromList() {
		// GIVEN
		// activity ready with exercise on DB and name entered
		prepareWithExerciseAndName();
		// 2 exercises added to the training
		addExerciseToTraining();
		addExerciseToTraining();
		assertChildrenNumber(activity.findViewById(R.id.exercisesLayout), 2);
		// WHEN
		// delete button clicked on item 1
		View item = getChildFromView(
				activity.findViewById(R.id.exercisesLayout), 0);
		clickOn(item.findViewById(R.id.deleteButton));
		// THEN
		// training no longer has exercise
		assertThat(trainingDao.queryForId(1).getExercises().size(), is(1));
		// UI doesn't show exercise on list
		assertChildrenNumber(activity.findViewById(R.id.exercisesLayout), 1);
		// remaining exercise has correct pos
		assertThat(trainingExerciseDao.queryForId(2).getPos(), is(0));
		// training exercise was deleted
		assertThat(trainingExerciseDao.countOf(), is(1l));
	}

	@Test
	public void shouldEditExerciseFromList() {
		// GIVEN
		// activity ready with exercise on DB and name entered
		prepareWithExerciseAndName();
		// new exercise
		BaseExerciseTest.insertExercise(exerciseDao, NEW_EXERCISE_NAME,
				NEW_EXERCISE_DESC);
		// 2 exercises added to the training
		addExerciseToTraining();
		addExerciseToTraining();
		assertChildrenNumber(activity.findViewById(R.id.exercisesLayout), 2);
		// WHEN
		// edit button clicked on item 1
		View item = getChildFromView(
				activity.findViewById(R.id.exercisesLayout), 0);
		clickOn(item.findViewById(R.id.editButton));
		// new data is entered
		// second exercise is selected on dialog
		AddExerciseDialog dialog = (AddExerciseDialog) ShadowDialog
				.getLatestDialog();
		Spinner spinner = (Spinner) dialog.findViewById(R.id.exerciseSpinner);
		spinner.setSelection(1);
		// enter 5 minutes
		setTextToUI(dialog.findViewById(R.id.minutes), "5");
		// enter 0 seconds
		setTextToUI(dialog.findViewById(R.id.seconds), "0");
		// enter 10 repetitions
		setTextToUI(dialog.findViewById(R.id.repetitions), "10");
		// click on dialog's commit button
		clickOn(dialog.findViewById(R.id.commitButton));
		// THEN
		// trainingExercise updated
		TrainingExercise te = trainingExerciseDao.queryForId(1);
		assertThat(te.getExercise().getName(), is(NEW_EXERCISE_NAME));
		assertThat(te.getSeconds(), is(300));
		assertThat(te.getReps(), is(10));
		// UI shows changes
		item = getChildFromView(activity.findViewById(R.id.exercisesLayout), 0);
		View itemTitle = getChildFromView(getChildFromView(item, 0), 1);
		assertThat(getTextFromUI(itemTitle), is(NEW_EXERCISE_NAME));
	}

	@Test
	public void shouldMoveExercises() {
		// GIVEN
		// activity ready with exercise on DB and name entered
		prepareWithExerciseAndName();
		// new exercise
		BaseExerciseTest.insertExercise(exerciseDao, NEW_EXERCISE_NAME,
				NEW_EXERCISE_DESC);
		// 2 exercises added to the training
		addExerciseToTraining();
		addExerciseToTraining();
		addExerciseToTraining();
		addExerciseToTraining();
		assertChildrenNumber(activity.findViewById(R.id.exercisesLayout), 4);
		// WHEN
		// move third to first
		((AddTrainingActivity)realActivity).moveExercise(2, 0);
		// move first to last
		((AddTrainingActivity)realActivity).moveExercise(0, 3);
		// THEN
		// positions are correct
		assertThat(trainingExerciseDao.queryForId(1).getPos(), is(0));
		assertThat(trainingExerciseDao.queryForId(2).getPos(), is(1));
		assertThat(trainingExerciseDao.queryForId(3).getPos(), is(3));
		assertThat(trainingExerciseDao.queryForId(4).getPos(), is(2));
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

	private void addExerciseToTraining() {
		// add button is clicked
		clickOn(activity.findViewById(R.id.addExerciseButton));
		// exercise is selected on dialog
		AddExerciseDialog dialog = (AddExerciseDialog) ShadowDialog
				.getLatestDialog();
		Spinner spinner = (Spinner) dialog.findViewById(R.id.exerciseSpinner);
		spinner.setSelection(0);
		// enter 1 minute
		setTextToUI(dialog.findViewById(R.id.minutes), "1");
		// enter 30 seconds
		setTextToUI(dialog.findViewById(R.id.seconds), "30");
		// enter 5 repetitions
		setTextToUI(dialog.findViewById(R.id.repetitions), "5");
		// click on dialog's commit button
		clickOn(dialog.findViewById(R.id.commitButton));
	}

	private void assertChildrenNumber(View view, int number) {
		ListView viewGroup = (ListView) view;
		assertThat(viewGroup.getChildCount(), is(number));
	}

	private void prepareWithExerciseAndName() {
		// 1 exercise on DB
		BaseExerciseTest.insertExercise(exerciseDao,
				BaseExerciseTest.EXERCISE_NAME, BaseExerciseTest.EXERCISE_DESC);
		// activity is shown
		createActivity();
		// name is set
		setTextToUI(activity.findViewById(R.id.trainningName), TRAINING_NAME);
	}

	// Inner classes -------------------------------------------------
}
