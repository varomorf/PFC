/**
 * 
 */
package com.gyp.pfc.data.domain.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.dao.BaseForeignCollection;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;

/**
 * @author Alvaro
 * 
 */
public class NullForeignCollection<T, ID> extends BaseForeignCollection<T, ID> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/**
	 * List used for delegation
	 */
	List<T> list = new ArrayList<T>();

	// Static --------------------------------------------------------

	private static final long serialVersionUID = 1L;

	public static final NullForeignCollection NULL_COLLECTION = new NullForeignCollection();

	// Constructors --------------------------------------------------

	private NullForeignCollection() {
		super(null, null, null, null, null, false);
	}

	// Public --------------------------------------------------------
	
	@Override
	public boolean add(T data) {
		return list.add(data);
	}

	@Override
	public CloseableIterator<T> iteratorThrow() throws SQLException {
		return null;
	}

	@Override
	public CloseableWrappedIterable<T> getWrappedIterable() {
		return null;
	}

	@Override
	public void closeLastIterator() throws SQLException {
	}

	@Override
	public boolean isEager() {
		return false;
	}

	@Override
	public int updateAll() throws SQLException {
		return 0;
	}

	@Override
	public int refreshAll() throws SQLException {
		return 0;
	}

	@Override
	public int refreshCollection() throws SQLException {
		return 0;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public CloseableIterator<T> closeableIterator() {
		return null;
	}

	@Override
	public boolean remove(Object data) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return false;
	}

	// Package protected ---------------------------------------------

	// Protected -----------------------------------------------------

	// Private -------------------------------------------------------

	// Inner classes -------------------------------------------------

}
