package org.ip.sort;

import org.ip.primitives.ArrayUtils;

public class PartitionBalancedRecursive implements PartitionInt {

	@Override
	public int partition(int[] array, int left, int right, int pivotIndex) {
		int sum = ArrayUtils.sum(array, left, right);
		return balance(array, sum / 2, left, right + 1) ? 1 : 0;
	}

	private static boolean balance(int[] array, int target, int left, int right) {
		if (target == 0) return true;
		if (left >= right) return false;
		int next = right - 1;
		return balance(array, target, left, next) || balance(array, target - array[next], left, next);
	}
}
