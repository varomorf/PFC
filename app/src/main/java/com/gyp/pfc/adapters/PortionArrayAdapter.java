package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.Portion;

/**
 * ArrayAdapter for Portion entitieso
 * 
 * @author Alvaro
 * 
 */
public class PortionArrayAdapter extends ArrayAdapter<Portion> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private LayoutInflater inflater = null;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The current context.
	 * @param textViewResourceId
	 *            The resource ID for a layout file containing a TextView to use
	 *            when instantiating views.
	 * @param portions
	 *            The portions to adapt
	 */
	public PortionArrayAdapter(Context context, int textViewResourceId, List<Portion> portions) {
		super(context, textViewResourceId, portions);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(R.layout.portion_list_item, null);
		}

		// populate the item with the data
		Portion portion = getItem(position);
		UIUtils.setTextToUI(view.findViewById(R.id.portionQuantity), portion.getQuantity().toString());
		UIUtils.setTextToUI(view.findViewById(R.id.portionName), portion.getFood().getName());

		return view;
	}

	/**
	 * Sets the collection on the adapter to the contents of the passed list
	 */
	public void setPortions(List<Portion> portions) {
		clear();
		for (Portion food : portions) {
			add(food);
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
