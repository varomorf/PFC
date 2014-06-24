/**
 * 
 */
package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.exercise.TrainingHistoric;

/**
 * {@link ArrayAdapter} for {@link TrainingHistoric} entities
 * 
 * @author Alvaro
 * 
 */
public class TrainingHistoricListViewAdapter extends ArrayAdapter<TrainingHistoric> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private LayoutInflater inflater = null;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link TrainingHistoricListViewAdapter} specifying its
	 * context, and the list of {@link TrainingHistoric} entities to use
	 * 
	 * @param context
	 *            The current context.
	 * @param objects
	 *            The objects to represent in the ListView.
	 */
	public TrainingHistoricListViewAdapter(Context context, List<TrainingHistoric> historic) {
		super(context, R.layout.no_search_list, historic);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(R.layout.training_historic_list_item, null);
		}

		// populate the item with the data
		TrainingHistoric historic = getItem(position);
		UIUtils.setTextToUI(view.findViewById(R.id.historicDate), TimeUtils.formatDate(historic.getStart()));
		UIUtils.setTextToUI(view.findViewById(R.id.historicStart), TimeUtils.formatTime(historic.getStart()));
		UIUtils.setTextToUI(view.findViewById(R.id.historicEnd), TimeUtils.formatTime(historic.getEnd()));
		UIUtils.setTextToUI(view.findViewById(R.id.historicTraining), historic.getTraining().getName());
		UIUtils.setTextToUI(view.findViewById(R.id.historicCalories), historic.getTraining().getBurntCalories() + " Kcal");

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
