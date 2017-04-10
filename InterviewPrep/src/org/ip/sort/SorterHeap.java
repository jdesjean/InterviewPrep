package org.ip.sort;

import org.ip.sort.HeapInt.Big;

public class SorterHeap implements Sorter {

	@Override
	public void sort(int[] array, int left, int right) {
		HeapInt heap = new HeapInt(array, left, right, new Big());
		for (int i = right; i > left; i--) {
			array[i] = heap.remove();
		}
	}
}
