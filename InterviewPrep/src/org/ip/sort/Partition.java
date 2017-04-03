package org.ip.sort;

import java.util.Comparator;

public interface Partition<T> {
	public int partition(T[] array, int left, int right, int pivotIndex, Comparator<T> comparator);
}
