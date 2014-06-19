/**
 * 
 */
package com.gyp.pfc.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;

/**
 * {@link ArrayAdapter} for adapting dates
 * 
 * @author Alvaro
 * 
 */
public class DateListViewAdapter extends ArrayAdapter<Date> {

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
	 * @param dates
	 *            The dates to represent in the ListView.
	 */
	public DateListViewAdapter(Context context, List<Date> dates) {
		super(context, android.R.layout.simple_list_item_1, android.R.id.text1, dates);
		inflater = LayoutInflater.from(context);
	}

	// Public --------------------------------------------------------

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflater.inflate(android.R.layout.simple_list_item_1, null);
		}

		// populate the item with the data
		Date date = getItem(position);
		String text = TimeUtils.formatDate(date);
		UIUtils.setTextToUI(view.findViewById(android.R.id.text1), text);

		return view;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
