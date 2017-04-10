package org.ip.sort;

public class SorterInsertion implements Sorter{

	@Override
	public void sort(int[] array, int left, int right) {
		for (int i = left, prev = Integer.MIN_VALUE; i <= right; prev = array[i++]) {
			if (prev < array[i]) continue;
			for (int j = i - 1, tmp = array[i]; j >= left; j--) {
				if (array[j] > tmp) {
					array[j+1] = array[j];
					if (j == left) array[j] = tmp;
				} else {
					array[j+1] = tmp;
					break;
				}
			}
		}
	}

}
