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
public class NullForeignCollection<T, I> extends BaseForeignCollection<T, I> {

	// Constants -----------------------------------------------------

	// Attributes ----------------------------------------------------

	/**
	 * List used for delegation
	 */
	List<T> list = new ArrayList<T>();

	// Static --------------------------------------------------------

	private static final long serialVersionUID = 1L;

	// Constructors --------------------------------------------------

	/**
	 * Creates a new {@link NullForeignCollection} with default values
	 */
	public NullForeignCollection() {
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
		// NOOP
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
		return list.size();
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
		return list.toArray();
	}

	@Override
	public <S> S[] toArray(S[] a) {
		return list.toArray(a);
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
