package org.ip.sort;
import org.ip.array.ArrayUtils;

public class SorterSelection implements Sorter{
	@Override
	public void sort(int[] array, int left, int right) {
		for (int i = left; i <= right; i++) {
			int min = i;
			for (int j = i + 1; j <= right; j++) {
				if (array[j] >= array[min]) continue;
				min = j;
			}
			ArrayUtils.swap(array,i,min);
		}
	}
}

