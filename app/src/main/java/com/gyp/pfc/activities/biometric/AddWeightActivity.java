/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import java.util.Date;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.biometric.Weight;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * @author Alvaro
 * 
 */
public class AddWeightActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnDateSetListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** Weight being created */
	private Weight weight = new Weight();

	/** Helper to be used */
	private BaseActivityHelper h;

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * Callback method for the weightAddDateButton button
	 * 
	 * @param view
	 *            the weightAddDateButton button
	 */
	public void findDate(View view) {
		h.showDatePickerDialogForToday();
	}

	/**
	 * Callback method for the ok button
	 * 
	 * @param view
	 *            the ok button
	 */
	public void okButton(View view) {
		try {
			String weightString = UIUtils.getTextFromUI(findViewById(R.id.weightAddWeight));
			// replace commas so always . is decimal separator
			weightString = weightString.replace(',', '.');
			Double weightValue = Double.parseDouble(weightString);
			weight.setWeight(weightValue);
			getHelper().getWeightDao().create(weight);
			finish();
		} catch (NumberFormatException e) {
			TextView field = (TextView) findViewById(R.id.weightAddWeight);
			field.requestFocus();
			field.setError(getString(R.string.enterCorrectWeight));
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		Date date = h.getDateFromPicker(year, monthOfYear, dayOfMonth);
		// date is truncated on weight
		weight.setDate(date);
		UIUtils.setTextToUI(findViewById(R.id.weightAddDate), TimeUtils.formatDate(date));
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		h = new BaseActivityHelper(this);

		setContentView(R.layout.weight_add);
	}

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
