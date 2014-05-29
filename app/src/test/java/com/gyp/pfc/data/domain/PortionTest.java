/**
 * 
 */
package com.gyp.pfc.data.domain;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for the {@link Portion} entity
 * 
 * @author Alvaro
 * 
 */
public class PortionTest {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	@Test
	public void shouldReturnExpectedString() {
		// GIVEN
		Portion portion = new Portion();
		portion.setId(1);
		portion.setQuantity(100);
		String expected = "1 - 100gr. of Null Food";
		// WHEN
		String ret = portion.toString();
		// THEN
		assertEquals("Should have returned expected string", expected, ret);
	}
	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
