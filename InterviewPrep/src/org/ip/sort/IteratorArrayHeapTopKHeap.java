package org.ip.sort;

import java.util.Collections;
import java.util.PriorityQueue;

//EPI: 11.5
//Time: O(klog(k)), Space: O(k)
public class IteratorArrayHeapTopKHeap implements IteratorInt{
	private static class ArrayReference implements Comparable<ArrayReference>{
		private int[] array;
		private int i;

		public ArrayReference(int[] array, int i) {
			this.array=array;
			this.i=i;
		}
		
		public int value() {
			return array[i];
		}

		@Override
		public int compareTo(ArrayReference o) {
			return value() - o.value();
		}
	}
	int i = 0;
	int k;
	private PriorityQueue<ArrayReference> heap;
	private int[] array;
	public IteratorArrayHeapTopKHeap(int[] array, int k) {
		int size = Math.min(k, array.length);
		heap = new PriorityQueue<ArrayReference>(size,Collections.reverseOrder());
		this.k=Math.min(k, array.length);
		this.array=array;
		//assuming array is max heap with max element at [0]
		heap.add(new ArrayReference(array,0));
	}

	@Override
	public boolean hasNext() {
		return i < k;
	}

	@Override
	public int next() {
		ArrayReference reference = heap.remove();
		i++;
		int left = reference.i*2+1;
		int right = left+1;
		if (left < array.length) {
			heap.add(new ArrayReference(array,left));
			if (right < array.length) heap.add(new ArrayReference(array,right)); 
		}
		return reference.value();
	}
}
