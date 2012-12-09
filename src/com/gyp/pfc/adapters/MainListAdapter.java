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
	private ViewHolder holder;
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
	public MainListAdapter(Context context, int textViewResourceId,
			String[] mainSectionsNames) {
		super(context, textViewResourceId, mainSectionsNames);
		inflator = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	// Public --------------------------------------------------------
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (view == null) {
			// generate view inflating the layout
			view = inflator.inflate(R.layout.main_list_item, null);
			// create a new holder
			holder = new ViewHolder();
			// populate holder
			holder.title = (TextView) view.findViewById(R.id.entryName);
			holder.addButton = (Button) view.findViewById(R.id.entryAddButton);
			holder.listButton = (Button) view
					.findViewById(R.id.entryListButton);
			// set the holder as tag
			view.setTag(holder);
		} else {
			// get the holder from the view
			holder = (ViewHolder) view.getTag();
		}
		// fill holder with data
		String str = getItem(position);

		holder.title.setText(str);
		// add click listener for list entities
		OnClickListener listListener = new ListButtonOnClickListener(str);
		holder.listButton.setOnClickListener(listListener);
		// add click listener for add entities
		OnClickListener addListener = new AddButtonOnClickListener(str);
		holder.addButton.setOnClickListener(addListener);

		return view;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

	private class ViewHolder {
		TextView title;
		Button addButton;
		Button listButton;
	}

}
