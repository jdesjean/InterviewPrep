package org.ip.sort;

import org.ip.primitives.ArrayUtils;

public class PartitionEvenized implements PartitionInt{

	@Override
	public int partition(int[] array, int left, int right, int pivotIndex) {
		int i = left;
		for (int j = right; i < j;) {
			if (array[i] % 2 == 0) i++;
			else if (array[j] % 2 == 1) j--;
			else ArrayUtils.swap(array, i++, j--);
		}
		return i;
	}

}
