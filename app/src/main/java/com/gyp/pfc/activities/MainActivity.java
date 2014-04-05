package com.gyp.pfc.activities;

import android.os.Bundle;
import android.view.Menu;

import com.gyp.pfc.R;
import com.gyp.pfc.adapters.MainListAdapter;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.Exercise;
import com.gyp.pfc.data.domain.Training;
import com.gyp.pfc.data.domain.manager.ExerciseManager;
import com.gyp.pfc.data.domain.manager.FoodManager;
import com.gyp.pfc.data.domain.manager.TrainingManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;

/**
 * Main activity of the application. Holds the list of the different actions.
 * 
 * @author Alvaro
 * 
 */
public class MainActivity extends OrmLiteBaseListActivity<DatabaseHelper> {

	// Constants -----------------------------------------------------
	private static final String[] MAIN_SECTIONS_NAMES = { "Foods", "Meals",
			"Exercises", "Trainings" };

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeManagers();
		// TODO change me
		loadTestData();
		// set the list adapter
		MainListAdapter adapter = new MainListAdapter(this,
				R.layout.main_list_item, MAIN_SECTIONS_NAMES);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	protected void loadTestData() {
		// load test food
		FoodManager.getInstance().createFood("Arroz", "", 364, 6.67, 81.60, 0,
				1.4, 0.9, 0.19, 0.0039);
		FoodManager.getInstance().createFood("Huevo", "", 162, 12.68, 0.68, 0,
				0, 12.10, 3.3, 0.144);
		FoodManager.getInstance().createFood("Pan blanco", "Mercadona", 261,
				8.47, 51.50, 0, 3.5, 1.6, 0.39, 0.540);
		// load test exercises
		Exercise exercise1 = ExerciseManager
				.getInstance()
				.createExercise(
						"Planchas",
						"Boca abajo, apoyamos las manos contra el suelo a la altura de los hombros y elevamos el cuerpo mediante una extensión de los brazos. Mediante una flexión controlada, volvemos a la posición de partida.");
		Exercise exercise2 = ExerciseManager
				.getInstance()
				.createExercise(
						"Abdominales",
						"Boca arriba, flexionamos las piernas de modo que los pies toquen el suelo. Elevamos el tronco hasta erguirnos. Bajamos el tronco a la posición de partida de forma controlada.");
		Exercise exercise3 = ExerciseManager
				.getInstance()
				.createExercise(
						"Zancadas",
						"De pie, damos un paso con una pierna, dejando la otra en el sitio. Flexionamos la rodilla adelantada, de tal modo que la rodilla atrasada llegue a tocar el suelo. Volvemos a la posición de partida.");
		// load test trainings
		Training training1 = TrainingManager.getInstance().createTraining(
				"Pirámide de abdominales");
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 20);
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 30);
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 40);
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 30);
		TrainingManager.getInstance().addExerciseToTraining(training1, exercise2, 0, 20);
		Training training2 = TrainingManager.getInstance().createTraining(
				"Serie de flexiones");
		TrainingManager.getInstance().addExerciseToTraining(training2, exercise1, 0, 10);
		TrainingManager.getInstance().addExerciseToTraining(training2, exercise1, 0, 10);
		TrainingManager.getInstance().addExerciseToTraining(training2, exercise1, 0, 10);
		TrainingManager.getInstance().addExerciseToTraining(training2, exercise1, 0, 10);
		Training training3 = TrainingManager.getInstance().createTraining(
				"Serie variada");
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise1, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise2, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise3, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise1, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise2, 30, 1);
		TrainingManager.getInstance().addExerciseToTraining(training3, exercise3, 30, 1);
	}

	// Private -------------------------------------------------------

	/**
	 * Initializes all managers
	 */
	private void initializeManagers() {
		TrainingManager.getInstance().setTrainingDao(
				getHelper().getTrainingDao());
		TrainingManager.getInstance().setTrainingExerciseDao(
				getHelper().getTrainingExerciseDao());
		FoodManager.getInstance().setFoodDao(getHelper().getFoodDao());
		ExerciseManager.getInstance().setExerciseDao(
				getHelper().getExerciseDao());
	}

	// Inner classes -------------------------------------------------

}
