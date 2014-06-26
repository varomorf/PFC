/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

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

	/** Flag for workaround known DatePickerDialog bug */
	private boolean onDateSetCalled;

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
		onDateSetCalled = false;
		h.showDatePickerDialogForToday();
	}

	/**
	 * Callback method for the ok button
	 * 
	 * @param view
	 *            the ok button
	 */
	public void okButton(View view) {
		Double weightValue = Double.parseDouble(UIUtils.getTextFromUI(findViewById(R.id.weightAddWeight)));
		weight.setWeight(weightValue);
		getHelper().getWeightDao().create(weight);
		finish();
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		if (!onDateSetCalled) {
			onDateSetCalled = true;
		} else {
			onDateSetCalled = false;
			return;
		}
		Date date = new Date();
		date = DateUtils.setYears(date, year);
		date = DateUtils.setMonths(date, monthOfYear);
		date = DateUtils.setDays(date, dayOfMonth);
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
