package org.ip.sort;

import java.util.Arrays;

import org.ip.array.ArrayUtils;

public class PartitionAlternate implements PartitionInt{
	public static void testAlternate() {
		int[] array = new int[] { 2, 3, -4, -9, -1, -7, 1, -5, -6 };
		PartitionAlternate partition = new PartitionAlternate();
		partition.partition(array,0,array.length-1,-1);
		System.out.println(Arrays.toString(array));
	}
	@Override
	public int partition(int[] array, int left, int right, int pivotIndex) {
		for (int i = left; i <= right; i++) {
			boolean positive = i % 2 == 0;
			if (positive && array[i] >= 0)
				continue;
			else if (!positive && array[i] <= 0)
				continue;
			else if (i + 1 >= array.length)
				continue;
			int k = positive ? ArrayUtils.nextPositive(array, i) : ArrayUtils.nextNegative(array, i);
			if (k < 0)
				return i;
			int tmp = array[k];
			ArrayUtils.shift(array, i, k);
			array[i] = tmp;
		}
		return right;
	}

}
