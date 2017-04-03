package org.ip.sort;

import java.util.Comparator;

public interface Selector<T> {
	public int select(T[] array, int left, int right, Comparator<T> comparator);
}
