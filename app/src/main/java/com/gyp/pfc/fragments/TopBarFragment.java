package com.gyp.pfc.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gyp.pfc.R;

/**
 * Fragment for the top bar so the app name is always shown next to a go back
 * button
 * 
 * @author alfergon
 * 
 */
public class TopBarFragment extends Fragment {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.top_bar, container, false);
		ImageButton button = (ImageButton) view.findViewById(R.id.previousActivity);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// finish the activity
				getActivity().finish();
			}
		});
		return view;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
