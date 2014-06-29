/**
 * 
 */
package com.gyp.pfc.activities.sharing.file;

/**
 * Implement a Groovy like closure
 * 
 * @author Alvaro
 * 
 */
public interface Closure {

	/**
	 * Call this closure passing a parameter for its correct function (can be null)
	 * 
	 * @param it
	 *            the parameter for executing this closure (can be null)
	 * @return the result of executing this closure (can be null)
	 */
	public Object call(Object it);

}
