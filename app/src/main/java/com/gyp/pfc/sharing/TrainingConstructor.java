package com.gyp.pfc.sharing;

import static com.gyp.pfc.sharing.TrainingExerciseYamlPropertyPosition.*;
import static com.gyp.pfc.sharing.TrainingYamlPropertyPosition.*;

import java.util.Map;

import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import com.gyp.pfc.data.domain.exercise.Exercise;
import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;

/**
 * Specific {@link Constructor} for {@link Training} entities for handling the {@link Training#exercises}
 * collection
 * 
 * @author alfergon
 * 
 */
public class TrainingConstructor extends Constructor {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link TrainingConstructor} putting the specific {@link ConstructTraining} as
	 * {@link Construct} for {@link Training} and {@link TrainingExercise} tags
	 */
	public TrainingConstructor() {
		this.yamlConstructors.put(new Tag(Training.class), new ConstructTraining());
		this.yamlConstructors.put(new Tag(TrainingExercise.class), new ConstructTrainingExercise());
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

	/**
	 * Specific {@link Construct} for {@link Training} entities.
	 * 
	 * It will only construct executable trainings as only executable trainings should have been exported.
	 * 
	 * @author alfergon
	 * 
	 */
	private class ConstructTraining extends AbstractConstruct {

		@Override
		public Object construct(Node node) {
			// prepare training
			// no need for parsing executable as it will always be true
			Training training = new Training();
			// no longer 2 steps construction needed
			MappingNode tNode = (MappingNode) node;
			tNode.setTwoStepsConstruction(false);
			// first direct values
			String name = constructScalar((ScalarNode) tNode.getValue().get(NAME.pos).getValueNode()).toString();
			training.setName(name);
			// now exercises collection is processed and each TrainingExercise added to the Training
			for (Object o : constructSequence((SequenceNode) tNode.getValue().get(EXERCISES.pos).getValueNode())) {
				training.addTrainingExercise((TrainingExercise) o);
			}
			return training;
		}

	}

	/**
	 * Specific {@link Construct} for {@link TrainingExercise} entities.
	 * 
	 * It will construct a {@link TrainingExercise} and its included {@link Exercise}O
	 * 
	 * @author alfergon
	 * 
	 */
	private class ConstructTrainingExercise extends AbstractConstruct {

		@Override
		public Object construct(Node node) {
			// prepare training exercise
			TrainingExercise trainingExercise = new TrainingExercise();
			// no longer 2 steps construction needed
			MappingNode teNode = (MappingNode) node;
			teNode.setTwoStepsConstruction(false);
			// first direct values
			String pos = constructScalar((ScalarNode) teNode.getValue().get(POS.pos).getValueNode()).toString();
			String reps = constructScalar((ScalarNode) teNode.getValue().get(REPS.pos).getValueNode()).toString();
			String seconds = constructScalar((ScalarNode) teNode.getValue().get(SECONDS.pos).getValueNode())
					.toString();
			trainingExercise.setPos(Integer.parseInt(pos));
			trainingExercise.setReps(Integer.parseInt(reps));
			trainingExercise.setSeconds(Integer.parseInt(seconds));
			// now exercise
			Map<Object, Object> data = constructMapping((MappingNode) teNode.getValue().get(EXERCISE.pos)
					.getValueNode());
			trainingExercise.setExercise(createExercise(data));
			return trainingExercise;
		}

		/**
		 * Creates an {@link Exercise} object from the passed data
		 * 
		 * @param data
		 *            the map with the exercise data
		 * @return the built exercise
		 */
		private Exercise createExercise(Map<Object, Object> data) {
			Exercise exercise = new Exercise();
			exercise.setName(data.get("name").toString());
			exercise.setDescription(data.get("description").toString());
			exercise.setBurntCalories(Integer.parseInt(data.get("burntCalories").toString()));
			return exercise;
		}

	}
}
