/**
 * 
 */
package com.gyp.pfc.data.domain.biometric;

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

	public static final String AGE_KEY = "AGE_KEY";

	public static final String SEX_IS_MAN_KEY = "SEX_IS_MAN_KEY";

	public static final String HEIGHT_KEY = "HEIGHT_KEY";

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
	 * Returns the user's height formatted as meters
	 * 
	 * @return the user's height formatted as meters
	 */
	public String getFormattedHeight() {
		return new Double(height / 100d).toString() + " m.";
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
