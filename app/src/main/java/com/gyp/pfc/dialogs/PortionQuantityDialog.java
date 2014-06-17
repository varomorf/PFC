/**
 * 
 */
package com.gyp.pfc.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gyp.pfc.R;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.data.domain.Food;
import com.gyp.pfc.data.domain.Portion;
import com.gyp.pfc.data.domain.builder.PortionBuilder;

/**
 * Dialog for specifying the quantity of a food for a portion
 * 
 * @author Alvaro
 * 
 */
public class PortionQuantityDialog extends Dialog implements android.view.View.OnClickListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/**
	 * The listener of the events launched from this dialog
	 */
	protected PortionQuantityDialogListener listener;

	/**
	 * The resulting portion from this dialog
	 */
	protected Portion portion;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	/**
	 * Creates a new dialog for entering the quantity for the passed food
	 * 
	 * @param context
	 *            the context passed to the super constructor
	 * @param listener
	 *            A listener for the dialog's commit buttons
	 * @param food
	 *            the food for which the dialog is being shown
	 */
	public PortionQuantityDialog(Context context, PortionQuantityDialogListener listener, Food food) {
		super(context);
		this.listener = listener;
		portion = new PortionBuilder().food(food).getPortion();
	}

	// Public --------------------------------------------------------

	/**
	 * Callback method for the save button
	 * 
	 * @param view
	 */
	public void onClick(View v) {
		// call the listener and dismiss
		if (v.getId() == R.id.okButton) {
			okButton();
		}
		dismiss();
	}

	/**
	 * Callback method for the ok button
	 */
	public void okButton() {
		// send entered quantity to the listener
		setPortionQuantity();
		listener.onPortionQuantityDialogAccept(portion);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.portion_quantity_dialog);
		((Button) findViewById(R.id.okButton)).setOnClickListener(this);
		((Button) findViewById(R.id.cancelButton)).setOnClickListener(this);
	}

	/**
	 * Sets the dialog's portion quantity with the one on the dialog's edit text
	 */
	protected void setPortionQuantity() {
		View quantity = findViewById(R.id.quantity);
		String text = UIUtils.getTextFromUI(quantity);
		portion.setQuantity(Integer.parseInt(text));
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

	/**
	 * Defines a listener for the {@link PortionQuantityDialog}
	 * 
	 * @author Alvaro
	 * 
	 */
	public interface PortionQuantityDialogListener {

		/**
		 * Callback method for when the dialog's accept button is clicked on for
		 * passing the portion for the quantity entered on the dialog back to
		 * the activity
		 * 
		 * @param portion
		 *            the portion for the quantity entered on the dialog
		 */
		void onPortionQuantityDialogAccept(Portion portion);

		/**
		 * Callback method for when the dialog's accept button is clicked on for
		 * passing the portion for the quantity entered on the dialog back to
		 * the activity as an edition
		 * 
		 * @param portion
		 *            the portion for the quantity entered on the dialog
		 */
		void onPortionQuantityEditDialogAccept(Portion portion);

		/**
		 * Callback method for when the dialog's cancel button is clicked on
		 */
		void onPortionQuantityDialogCancel();
	}

}
