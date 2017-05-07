package org.ip.sort;

import org.ip.array.Utils;

public class PartitionPolarized implements PartitionInt{

	@Override
	public int partition(int[] array, int left, int right, int pivotIndex) {
		int i = left;
		for (int j = right; i < j;) {
			if (array[i] >= 0) i++;
			else if (array[j] < 0) j--;
			else Utils.swap(array, i++, j--);
		}
		return i;
	}

}
