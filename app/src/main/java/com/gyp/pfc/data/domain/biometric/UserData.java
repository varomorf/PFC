/**
 * 
 */
package com.gyp.pfc.data.domain.biometric;

import java.text.DecimalFormat;

/**
 * Bean for the user's biometric data.
 * 
 * @author Alvaro
 * 
 */
public class UserData {

	// Constants -----------------------------------------------------

	public static final int DEFAULT_AGE = 30;

	public static final int DEFAULT_HEIGHT = 150;

	public static final double DEFAULT_WEIGHT = 70d;

	public static final String AGE_KEY = "AGE_KEY";

	public static final String SEX_IS_MAN_KEY = "SEX_IS_MAN_KEY";

	public static final String HEIGHT_KEY = "HEIGHT_KEY";

	public static final DecimalFormat DF = new DecimalFormat("####0.00");

	// Attributes ----------------------------------------------------

	/**
	 * The user's age. Will be forced to be greater than 0. Defaults to {@link UserData#DEFAULT_AGE}
	 */
	private Integer age = DEFAULT_AGE;

	/** The user's age. Will be forced to have value. Default value will be {@link UserSex#MAN} */
	private UserSex sex = UserSex.MAN;

	/**
	 * The user's height in centimeters. Will be forced to be greater than 0. Defaults to
	 * {@link UserData#DEFAULT_HEIGHT}.
	 */
	private Integer height = DEFAULT_HEIGHT;

	/**
	 * The user's weight
	 */
	private Weight weight = new Weight();

	// Static --------------------------------------------------------

	// Constructors --------------------------------------------------

	// Public --------------------------------------------------------

	/**
	 * @return the age
	 */
	public Integer getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(Integer age) {
		this.age = age;
		if (null == this.age || this.age <= 0) {
			this.age = DEFAULT_AGE;
		}
	}

	/**
	 * @return the sex
	 */
	public UserSex getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(UserSex sex) {
		this.sex = sex;
		if (null == this.sex) {
			this.sex = UserSex.MAN;
		}
	}

	/**
	 * @return the height
	 */
	public Integer getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(Integer height) {
		this.height = height;
		if (null == this.height || this.height <= 0) {
			this.height = DEFAULT_HEIGHT;
		}
	}

	/**
	 * @return the user's weight
	 */
	public Weight getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(Weight weight) {
		this.weight = weight;
		if (null == this.weight) {
			this.weight = new Weight();
		}
	}

	/**
	 * Returns the user's height formatted as meters rounded to two decimals
	 * 
	 * @return the user's height formatted as meters
	 */
	public String getFormattedHeight() {
		return DF.format(getHeightInMeters()) + " m.";
	}

	/**
	 * Returns the user's weight formatted as kilograms rounded to two decimals
	 * 
	 * @return the user's weight formatted as meters
	 */
	public String getFormattedWeight() {
		return DF.format(weight.getWeight()) + " Kg.";
	}

	/**
	 * Returns the user's height in centimeters
	 * 
	 * @return the user's height in centimeters
	 */
	public Double getHeightInMeters() {
		return new Double(height / 100d);
	}

	/**
	 * Returns the user's BMI
	 * 
	 * {@link http://en.wikipedia.org/wiki/Body_mass_index}
	 * 
	 * @return
	 */
	public Double getBMI() {
		return weight.getWeight() / Math.pow(getHeightInMeters(), 2);
	}

	/**
	 * Returns the user's BMI string representation rounded to two decimals
	 * 
	 * @return the user's BMI string representation rounded to two decimals
	 */
	public String getFormattedBMI() {
		return DF.format(getBMI());
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
