/**
 * 
 */
package com.gyp.pfc.activities.biometric;

import java.util.Date;
import java.util.List;

import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.gyp.pfc.R;
import com.gyp.pfc.TimeUtils;
import com.gyp.pfc.UIUtils;
import com.gyp.pfc.activities.exception.DuplicatedDateException;
import com.gyp.pfc.activities.helpers.BaseActivityHelper;
import com.gyp.pfc.data.db.DatabaseHelper;
import com.gyp.pfc.data.domain.biometric.Weight;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Activity for entering new weights on the application.
 * 
 * The users will use it to keep a track of their weight.
 * 
 * @author Alvaro
 * 
 */
public class AddWeightActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnDateSetListener {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/** Weight being created */
	protected Weight weight = new Weight();

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
			assertDateDuplication(weight);
			getHelper().getWeightDao().createOrUpdate(weight);
			finish();
		} catch (NumberFormatException e) {
			TextView field = (TextView) findViewById(R.id.weightAddWeight);
			field.requestFocus();
			field.setError(getString(R.string.enterCorrectWeight));
		} catch (DuplicatedDateException e) {
			Toast.makeText(this, getString(R.string.duplicatedWeight), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		Date date = h.getDateFromPicker(year, monthOfYear, dayOfMonth);
		if(null != date){
			// date is truncated on weight
			weight.setDate(date);
			UIUtils.setTextToUI(findViewById(R.id.weightAddDate), TimeUtils.formatDate(date));
		}
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

	/**
	 * Assert that the is no weight on DB with the same date as the passed
	 * weight's one
	 * 
	 * @param weight
	 *            the weight to check for duplicated
	 * @throws DuplicatedDateException
	 *             if there is a duplicate
	 */
	private void assertDateDuplication(Weight weight) throws DuplicatedDateException {
		List<Weight> weights = getHelper().getWeightDao().queryForEq("date", weight.getDate());
		if (!weights.isEmpty() && !weights.get(0).equals(weight)) {
			throw new DuplicatedDateException(weight.getDate());
		}
	}

	// Inner classes -------------------------------------------------

}
