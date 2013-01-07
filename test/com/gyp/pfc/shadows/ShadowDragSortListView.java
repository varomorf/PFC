package com.gyp.pfc.shadows;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.mobeta.android.dslv.DragSortItemView;
import com.mobeta.android.dslv.DragSortListView;
import com.xtremelabs.robolectric.internal.Implements;
import com.xtremelabs.robolectric.internal.RealObject;
import com.xtremelabs.robolectric.shadows.ShadowListView;

@Implements(DragSortListView.class)
public class ShadowDragSortListView extends ShadowListView {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------
	@RealObject
	private DragSortListView realDragSortListView;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Override
	protected void addViews() {
		super.addViews();
		int count = getChildCount();
		if (count != 0) {
			List<View> children = new ArrayList<View>();
			for (int i = 0; i < count; i++) {
				children.add(getChildAt(i));
			}
			removeAllViews();
			for (View view : children) {
				DragSortItemView tmp = new DragSortItemView(context);
				tmp.addView(view);
				addView(tmp);
			}
		}
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
