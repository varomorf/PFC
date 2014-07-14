package com.gyp.pfc.activities.training;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.adapters.TrainingExerciseAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.gyp.pfc.dialogs.AddExerciseDialog;
import com.gyp.pfc.dialogs.AddExerciseDialog.AddExerciseDialogListener;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.mobeta.android.dslv.DragSortController;
import com.mobeta.android.dslv.DragSortListView;

/**
 * Activity to add trainings and reference exercises to trainings.
 * 
 * @author Alvaro
 * 
 */
public class AddTrainingActivity extends OrmLiteBaseActivity<DatabaseHelper> implements AddExerciseDialogListener {

	// Constants -----------------------------------------------------

	public static final String TRAINING = "TRAINING";

	// Attributes ----------------------------------------------------

	/** Adapter used for the list of {@link TrainingExercise} entities */
	private TrainingExerciseAdapter adapter;

	/** The created {@link Training} */
	private Training training;

	/** Drop listener for the {@link DragSortListView} */
	private DragSortListView.DropListener onDrop = new DragSortListView.DropListener() {
		@Override
		public void drop(int from, int to) {
			moveExercise(from, to);
		}
	};

	/** Helper to be used */
	private BaseActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	/**
	 * Callback for the commit button
	 * 
	 * @param view
	 */
	public void commitButton(View view) {
		// assure training
		getTraining();
		if (assertTraining()) {
			// persist the training
			getHelper().getTrainingDao().createOrUpdate(training);
			// show toast with OK message
			Toast.makeText(this, R.string.trainingCreated, Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Callback method for the Add button
	 * 
	 * @param view
	 */
	public void addExercise(View view) {
		// assure training
		getTraining();
		if (assertTraining()) {
			prepareAndShowDialog(null);
		}
	}

	/**
	 * Callback for the delete buttons of the added exercises
	 * 
	 * @param view
	 */
	public void deleteButton(View view) {
		int pos = getPositionOfItemForPressedButton(view);
		// remove item from list's adapter
		adapter.remove(getTrainingExercise(pos));
		// refresh the adapter to update UI
		adapter.notifyDataSetChanged();
		// remove trainingExercise
		TrainingExercise te = getTrainingExercise(pos);
		getHelper().getTrainingExerciseDao().delete(te);
		getHelper().getTrainingDao().refresh(training);
		// fix position of the rest of exercises
		fixExercisesPosition(pos, adapter.getCount() + 1);
	}

	/**
	 * Callback for the edit buttons of the added exercises
	 * 
	 * @param view
	 */
	public void editButton(View view) {
		int pos = getPositionOfItemForPressedButton(view);
		// get trainingExercise for that pos
		TrainingExercise selected = getTrainingExercise(pos);
		// prepare and show dialog for edition
		prepareAndShowDialog(selected);
	}

	/**
	 * Extracts the data from the closing dialog and creates a TrainingExercise entity with it
	 */
	public void addNewExercise(AddExerciseDialog dialog) {
		// save training so it can be assigned on the TrainingExercise
		getHelper().getTrainingDao().createOrUpdate(training);
		// create new TrainingExercise entity
		TrainingExercise te = createTrainingExercise(dialog);
		// save TrainingExercise to DB
		getHelper().getTrainingExerciseDao().create(te);
		// add new exercise to list's adapter
		adapter.add(te);
		// refresh the adapter to update UI
		adapter.notifyDataSetChanged();
		// refresh training's exercise list
		getHelper().getTrainingDao().refresh(training);
	}

	/**
	 * Extracts the data from the closing dialog and updates the TrainingExercise entity with it
	 */
	public void updateExercise(AddExerciseDialog dialog) {
		// get trainingExercise from dialog
		TrainingExercise te = dialog.getTrainingExercise();
		setExerciseSecondsAndReps(dialog, te);
		// update on DB
		getHelper().getTrainingExerciseDao().update(te);
		// refresh training and update UI list
		updateUIList();
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new BaseActivityHelper(this);
		setContentView(R.layout.training_data);

		// create adapter for the list view
		adapter = new TrainingExerciseAdapter(this, new ArrayList<TrainingExercise>());
		// retrieve the drag'n'drop list view to set listener and adapter
		DragSortListView lv = (DragSortListView) findViewById(R.id.exercisesLayout);
		configureDragSortListView(lv);

		// get intent to get data
		Intent intent = getIntent();
		if (null != intent) {
			training = (Training) intent.getSerializableExtra(TRAINING);
			if (null != training) {
				// if there's an exercise -> update the view
				updateView();
			}
		}
	}

	protected void moveExercise(int from, int to) {
		// fix positions
		fixExercisesPosition(from, to);
		// get affected training exercises
		TrainingExercise te = adapter.getItem(from);
		// swap positions
		te.setPos(to);
		// update on DB
		getHelper().getTrainingExerciseDao().update(te);
		// refresh training and update UI list
		updateUIList();
	}

	// Private -------------------------------------------------------

	private boolean assertNotBlankName() {
		if (StringUtils.isBlank(training.getName())) {
			// if name blank -> show toast and return false
			Toast.makeText(this, R.string.trainingNameBlank, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	private boolean assertNotDuplicatedName() {
		// query an training with the same name as the passed training
		List<Training> tmp = getHelper().getTrainingDao().queryForEq("name", training.getName());
		// if the list holds trainings -> name is duplicated
		if (!tmp.isEmpty()) {
			// if only returned training has same id than passed is ok
			if (tmp.size() == 1 && training.equals(tmp.get(0))) {
				return true;
			}
			// duplicated name -> show toast and return false
			Toast.makeText(this, R.string.trainingNameDuplicated, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

	// Private -------------------------------------------------------

	private boolean assertTraining() {
		return assertNotBlankName() && assertNotDuplicatedName();
	}

	private void configureDragSortListView(DragSortListView lv) {
		lv.setDropListener(onDrop);
		lv.setDragEnabled(true);
		DragSortController controller = new DragSortController(lv);
		controller.setDragHandleId(R.id.drag_handle);
		controller.setRemoveEnabled(false);
		controller.setSortEnabled(true);
		lv.setOnTouchListener(controller);
		lv.setAdapter(adapter);
	}

	private TrainingExercise createTrainingExercise(AddExerciseDialog dialog) {
		// create new TrainingExercise entity
		TrainingExercise te = new TrainingExercise();
		// assure training
		training = getTraining();
		// set training
		te.setTraining(training);
		setExerciseSecondsAndReps(dialog, te);
		// get number of exercises entered
		int num = ((ListView) findViewById(R.id.exercisesLayout)).getChildCount();
		// set pos to num
		te.setPos(num);
		return te;
	}

	private void fixExercisesPosition(int from, int to) {
		// get min and max to now operation
		int min = Math.min(from, to);
		int max = Math.max(from, to);
		// iterate exercises to fix their pos
		for (TrainingExercise te : training.getExercises()) {
			// get position
			int pos = te.getPos();
			// if position between affected -> fix
			if ((pos > min && pos < max) || pos == to) {
				if (max == from) {
					// increase position
					te.setPos(pos + 1);
				} else {
					// decrease position
					te.setPos(pos - 1);
				}
				// update on DB
				getHelper().getTrainingExerciseDao().update(te);
			}
			if (pos > max) {
				// there will be no more affected -> exit loop
				break;
			}
		}
	}

	private int getPositionOfItemForPressedButton(View view) {
		// get position of item for which button was pressed
		DragSortListView exerciseList = (DragSortListView) findViewById(R.id.exercisesLayout);
		return exerciseList.indexOfChild((View) view.getParent().getParent());
	}

	private Training getTraining() {
		if (training == null) {
			// create the new training
			training = new Training();
		}
		// get name from view
		String name = h.getTextFromUI(R.id.trainingName);
		// set training name
		training.setName(name);
		return training;
	}

	private TrainingExercise getTrainingExercise(int pos) {
		return TrainingManager.it().getTrainingExerciseForPos(training, pos);
	}

	private void prepareAndShowDialog(TrainingExercise selected) {
		// get list of exercises
		List<Exercise> exercises = ExerciseManager.it().getAllExercises();
		if (!exercises.isEmpty()) {
			// create dialog passing this activity as listener
			Dialog dialog = new AddExerciseDialog(this, this, exercises, selected);
			// show the dialog
			dialog.show();
		} else {
			// no exercises -> show toast with error message
			Toast.makeText(this, R.string.emptyExercisesList, Toast.LENGTH_SHORT).show();
		}
	}

	private void setExerciseSecondsAndReps(AddExerciseDialog dialog, TrainingExercise te) {
		// set exercise from spinner
		Spinner spinner = (Spinner) dialog.findViewById(R.id.exerciseSpinner);
		te.setExercise((Exercise) spinner.getSelectedItem());
		// set repetitions
		String repetitions = h.getTextFromUI(dialog.findViewById(R.id.repetitions));
		te.setReps(Integer.parseInt(repetitions));
		// get minutes
		String minutes = h.getTextFromUI(dialog.findViewById(R.id.minutes));
		// get seconds
		String seconds = h.getTextFromUI(dialog.findViewById(R.id.seconds));
		te.setSeconds(te.getSeconds() + Integer.parseInt(seconds));
		// add seconds to trainingExercise
		te.setSeconds(TimeUtils.secondsFromMinutesAndSeconds(minutes, seconds));
	}

	private void updateUIList() {
		getHelper().getTrainingDao().refresh(training);
		adapter.clear();
		for (TrainingExercise obj : training.getExercises()) {
			adapter.add(obj);
		}
		adapter.notifyDataSetChanged();
	}

	private void updateView() {
		// set the name form field
		h.setTextToUI(R.id.trainingName, training.getName());
		// update the list of exercises
		updateUIList();
	}

	// Inner classes -------------------------------------------------

}
