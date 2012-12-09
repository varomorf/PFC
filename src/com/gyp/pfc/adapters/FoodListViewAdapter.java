package com.gyp.pfc.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.data.domain.Food;

public class FoodListViewAdapter extends ArrayAdapter<Food> {
	// TODO fucking comment this
	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	private List<Food> foods;
	private ViewHolder holder;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------
	public FoodListViewAdapter(Context context, int textViewResourceId,
			List<Food> foods) {
		super(context, textViewResourceId, foods);
		this.foods = foods;
	}

	public void setFoods(List<Food> foods) {
		this.foods = foods;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.food_list_item, null);

			holder = new ViewHolder();

			holder.title = (TextView) v.findViewById(R.id.title);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		Food food = foods.get(position);

		String text = food.getName() + " / " + food.getCalories() + "KCal";

		holder.title.setText(text);
		holder.title.setBackgroundColor(food.getColor());

		return v;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

	private class ViewHolder {
		TextView title;
	}

}
