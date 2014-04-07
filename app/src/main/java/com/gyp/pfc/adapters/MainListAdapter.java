/**
 * 
 */
package com.gyp.pfc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.listeners.AddButtonOnClickListener;
import com.gyp.pfc.listeners.ListButtonOnClickListener;

/**
 * {@link ArrayAdapter} for the main activity list
 * 
 * @author Alvaro
 * 
 */
public class MainListAdapter extends ArrayAdapter<String> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	private LayoutInflater inflator;

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
	 * @param mainSectionsNames
	 *            The names of the different sections.
	 */
	public MainListAdapter(Context context, int textViewResourceId, String[] mainSectionsNames) {
		super(context, textViewResourceId, mainSectionsNames);
		inflator = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// Public --------------------------------------------------------
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			// generate view inflating the layout
			view = inflator.inflate(R.layout.main_list_item, null);
		}
		// get item's components from view
		TextView title = (TextView) view.findViewById(R.id.entryName);
		Button addButton = (Button) view.findViewById(R.id.entryAddButton);
		Button listButton = (Button) view.findViewById(R.id.entryListButton);

		// fill item with data
		String str = getItem(position);
		title.setText(str);
		// add click listener for list entities
		OnClickListener listListener = new ListButtonOnClickListener(str);
		listButton.setOnClickListener(listListener);
		// add click listener for add entities
		OnClickListener addListener = new AddButtonOnClickListener(str);
		addButton.setOnClickListener(addListener);

		return view;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
