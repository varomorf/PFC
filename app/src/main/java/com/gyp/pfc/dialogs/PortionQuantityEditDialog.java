/**
 * 
 */
package com.gyp.pfc.dialogs;

import android.content.Context;
import android.os.Bundle;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.meal.Portion;

/**
 * Dialog for editing the quantity of a food for a portion
 * 
 * @author Alvaro
 * 
 */
public class PortionQuantityEditDialog extends PortionQuantityDialog implements android.view.View.OnClickListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new dialog for editing the quantity for the passed portion
	 * 
	 * @param context
	 *            the context passed to the super constructor
	 * @param listener
	 *            A listener for the dialog's commit buttons
	 * @param food
	 *            the food for which the dialog is being shown
	 */
	public PortionQuantityEditDialog(Context context, PortionQuantityDialogListener listener, Portion portion) {
		super(context, listener, null);
		this.portion = portion;
	}

	// Public --------------------------------------------------------

	/**
	 * Callback method for the ok button
	 */
	@Override
	public void okButton() {
		// send edited portion to the listener
		setPortionQuantity();
		listener.onPortionQuantityEditDialogAccept(portion);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// fill portion quantity
		UIUtils.setTextToUI(findViewById(R.id.quantity), portion.getQuantity().toString());
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
