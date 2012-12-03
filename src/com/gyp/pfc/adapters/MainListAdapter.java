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
 * @author Alvaro
 * 
 */
public class MainListAdapter extends ArrayAdapter<String> {

	private String[] data;
	private ViewHolder holder;
	private LayoutInflater inflator;

	class ViewHolder {
		TextView title;
		Button addButton;
		Button listButton;
	}

	public MainListAdapter(Context context, int textViewResourceId,
			String[] mainSectionsNames) {
		super(context, textViewResourceId, mainSectionsNames);
		this.data = mainSectionsNames;
		inflator = (LayoutInflater) getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			v = inflator.inflate(R.layout.main_list_item, null);

			holder = new ViewHolder();

			holder.title = (TextView) v.findViewById(R.id.entryName);
			holder.addButton = (Button) v.findViewById(R.id.entryAddButton);
			holder.listButton = (Button) v.findViewById(R.id.entryListButton);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		String str = data[position];

		holder.title.setText(str);
		OnClickListener listListener = new ListButtonOnClickListener(str);
		holder.listButton.setOnClickListener(listListener);
		OnClickListener addListener = new AddButtonOnClickListener(str);
		holder.addButton.setOnClickListener(addListener);

		return v;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
