package com.gyp.pfc.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * Matcher for String's contains method
 * 
 * @author Alvaro
 * 
 */
public class ContainsMatcher<T> extends BaseMatcher<T> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	private final String equalArg;

	// Static --------------------------------------------------------

	/**
	 * Is the value contained into another value, as tested by the
	 * {@link java.lang.String#contains} invokedMethod?
	 */
	@Factory
	public static <T> Matcher<T> contains(T operand) {
		return new ContainsMatcher<T>(operand);
	}

	// Constructors --------------------------------------------------

	public ContainsMatcher(T operand) {
		this.equalArg = operand.toString();
	}

	// Public --------------------------------------------------------

	@Override
	public boolean matches(Object expected) {
		if (expected instanceof String) {
			return ((String) expected).contains(equalArg);
		}
		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendValue("A text containing " + equalArg);
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------
}
