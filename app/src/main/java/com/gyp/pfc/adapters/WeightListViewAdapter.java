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
import com.gyp.pfc.data.domain.biometric.Weight;

/**
 * {@link ArrayAdapter} for {@link Weight} entities
 * 
 * @author Alvaro
 * 
 */
public class WeightListViewAdapter extends ArrayAdapter<Weight> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private LayoutInflater inflater = null;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link WeightListViewAdapter} specifying its context, and
	 * the list of {@link Weight} entities to use
	 * 
	 * @param context
	 *            The current context.
	 * @param objects
	 *            The objects to represent in the ListView.
	 */
	public WeightListViewAdapter(Context context, List<Weight> weights) {
		super(context, R.layout.no_search_list, weights);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(R.layout.weight_list_item, null);
		}

		// populate the item with the data
		Weight weight = getItem(position);
		UIUtils.setTextToUI(view.findViewById(R.id.weightListItemDate), TimeUtils.formatDate(weight.getDate()));
		UIUtils.setTextToUI(view.findViewById(R.id.weightListItemWeight), weight.getWeight().toString());

		return view;
	}

	/**
	 * Sets the collection on the adapter to the contents of the passed list
	 */
	public void setData(List<Weight> weights) {
		clear();
		for (Weight weight : weights) {
			add(weight);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
