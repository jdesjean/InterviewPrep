package org.ip.sort;

import java.util.Comparator;

import org.ip.array.Utils;

public class PartitionDutchFlag<T> implements Partition<T>{

	@Override
	public int partition(T[] array, int left, int right, int pivotIndex, Comparator<T> comparator) {
		T pivot = array[pivotIndex];
		int i = left, j = left, k = right;
		for (;j <= k;) {
			if (comparator.compare(array[j], pivot) < 0) {
				Utils.swap(array, i++, j++);
			} else if (comparator.compare(array[j], pivot) > 0) {
				Utils.swap(array, j, k--);
			} else j++;
		}
		return i;
	}

}
