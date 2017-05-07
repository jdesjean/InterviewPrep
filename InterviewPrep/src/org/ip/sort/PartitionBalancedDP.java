package org.ip.sort;

import org.ip.array.Utils;

public class PartitionBalancedDP implements PartitionInt {

	@Override
	public int partition(int[] array, int left, int right, int pivotIndex) {
		int sum = Utils.sum(array, left, right);
		if (sum % 2 != 0)
			return 0; // impossible
		int target = sum / 2;
		int sumNegatives = Utils.sumNegatives(array, left, right);
		int sumPositives = Utils.sumPositives(array, left, right);
		int total = Math.abs(sumNegatives) + sumPositives + 1;
		int indexZero = Math.abs(sumNegatives);
		int indexTarget = indexZero + target;
		int[] cache = new int[total];
		cache[indexZero] = 1; // zero

		for (int i = left; i <= right; i++) {
			if (array[i] > 0) {
				for (int j = total - 1, k = j - array[i]; k >= 0; j--, k--) {
					if (cache[j] != 0 || cache[k] == 0)
						continue;
					cache[j] = array[i];
				}
			} else if (array[i] < 0) {
				for (int j = 0, k = j - array[i]; k <= right; j++, k++) {
					if (cache[j] != 0 || cache[k] == 0)
						continue;
					cache[j] = array[i];
				}
			}
		}
		if (cache[indexTarget] == 0)
			return 0; // cannot partition
		for (int i = indexTarget, j = 0; i != indexZero && j < array.length; i -= cache[i], j++) {
			for (int k = 0; k < array.length; k++) {
				if (array[k] != cache[i])
					continue;
				Utils.swap(array, k, j);
				break;
			}
		}
		return 1;
	}

}
