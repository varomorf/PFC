/**
 * 
 */
package com.gyp.pfc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		String str = data[position];

		holder.title.setText(str);

		return v;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
