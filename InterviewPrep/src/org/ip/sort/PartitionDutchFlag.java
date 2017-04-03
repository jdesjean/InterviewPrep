package org.ip.sort;

import java.util.Comparator;

import org.ip.ArrayUtils;

public class PartitionDutchFlag<T> implements Partition<T>{

	@Override
	public int partition(T[] array, int left, int right, int pivotIndex, Comparator<T> comparator) {
		T pivot = array[pivotIndex];
		int i = left, j = left, k = right;
		for (;j <= k;) {
			if (comparator.compare(array[j], pivot) < 0) {
				ArrayUtils.swap(array, i++, j++);
			} else if (comparator.compare(array[j], pivot) > 0) {
				ArrayUtils.swap(array, j, k--);
			} else j++;
		}
		return i;
	}

}
