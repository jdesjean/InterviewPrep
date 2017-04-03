package org.ip.sort;

import org.ip.ArrayUtils;

public class PartitionDutchFlagInt implements PartitionInt{

	@Override
	public int partition(int[] array, int left, int right, int pivotIndex) {
		int pivot = array[pivotIndex];
		int i = left, j = left, k = right;
		for (;j <= k;) {
			if (array[j] < pivot) {
				ArrayUtils.swap(array, i++, j++);
			} else if (array[j] > pivot) {
				ArrayUtils.swap(array, j, k--);
			} else j++;
		}
		return i;
	}

}
