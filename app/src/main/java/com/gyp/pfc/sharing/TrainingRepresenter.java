package com.gyp.pfc.sharing;

import static com.gyp.pfc.sharing.TrainingYamlPropertyPosition.EXERCISES;

import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import com.gyp.pfc.data.domain.exercise.Training;
import com.gyp.pfc.data.domain.exercise.TrainingExercise;
import com.j256.ormlite.dao.ForeignCollection;

/**
 * Specific {@link Representer} for {@link Training} entities for handling the {@link Training#exercises}
 * collection
 * 
 * @author alfergon
 * 
 */
public class TrainingRepresenter extends Representer {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link TrainingRepresenter} putting the specific {@link RepresentTraining} as
	 * {@link Represent} for {@link Training} JavaBeans
	 */
	public TrainingRepresenter() {
		this.representers.put(Training.class, new RepresentTraining());
	}

	// Public --------------------------------------------------------

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

	/**
	 * Specific {@link Represent} for {@link Training} entities
	 * 
	 * @author alfergon
	 * 
	 */
	private class RepresentTraining extends RepresentJavaBean {

		@Override
		public Node representData(Object data) {
			Training training = (Training) data;
			ForeignCollection<TrainingExercise> exercises = training.getExercises();
			// get default representation as JavaBean
			MappingNode trainingNode = (MappingNode) super.representData(training);
			// change value of the exercises foreign collection to set of each
			// TrainingExercise
			Node exercisesNode = representSequence(Tag.MAP, exercises, false);
			NodeTuple exercisesTuple = new NodeTuple(representScalar(Tag.STR, "exercises"), exercisesNode);
			trainingNode.getValue().set(EXERCISES.pos, exercisesTuple);

			return trainingNode;
		}
	}
}
