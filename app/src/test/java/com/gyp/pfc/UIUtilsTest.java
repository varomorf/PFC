/**
 * 
 */
package com.gyp.pfc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import android.view.View;

/**
 * Tests for UIUtils
 * 
 * @author Alvaro
 * 
 */
public class UIUtilsTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Test
	public void shouldReturnNullIfViewIsNull() {
		// GIVEN
		// WHEN
		String text = UIUtils.getTextFromUI(null);
		// THEN
		assertEquals("View's text should be the expected one", null, text);
	}

	@Test
	public void shouldDoNothingIfViewIsNotTextView() {
		// GIVEN
		View view = null;
		// WHEN
		UIUtils.setTextToUI(view, "");
		// THEN
		// NOOP
	}

	@Test
	public void shouldReturnNullIfNotGroupView() {
		// GIVEN
		View view = null;
		// WHEN
		View child = UIUtils.getChildFromView(view, 0);
		// THEN
		assertNull("Child should be null", child);
	}

	@Test
	public void shouldReturnNullWithNullValue() {
		// GIVEN
		// WHEN
		Double ret = UIUtils.parseDouble(null);
		// THEN
		assertNull("Value should be nul", ret);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
