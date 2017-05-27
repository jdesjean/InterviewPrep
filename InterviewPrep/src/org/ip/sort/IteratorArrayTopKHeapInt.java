package org.ip.sort;

import org.ip.sort.HeapInt.Big;
import org.ip.sort.HeapInt.Small;

//EPI: 11.4
//Time: O(nlog(k)), Space: O(k)
public class IteratorArrayTopKHeapInt implements IteratorInt{
	private HeapInt heap;
	public IteratorArrayTopKHeapInt(int[] array, int k) {
		heap = new HeapInt(k,new Small());
		int size = Math.min(k, array.length); 
		for (int i = 0; i < size; i++) {
			heap.add(array[i]);
		}
		for (int i = size; i < array.length; i++) {
			if (array[i] > heap.peek()) {
				heap.remove();
				heap.add(array[i]);
			}
		}
	}
	@Override
	public boolean hasNext() {
		return !heap.isEmpty();
	}
	@Override
	public int next() {
		return heap.remove();
	}

}
