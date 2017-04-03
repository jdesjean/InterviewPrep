package org.ip.sort;

import java.util.Arrays;

import org.ip.ArrayUtils;
import org.ip.IteratorInt;

public class HeapInt {
	public static void main(String[] s) {
		HeapInt heap = new HeapInt(10,new Small());
		for (int i = 10; i > 0; i--) {
			heap.add(i);
		}
		System.out.println(Arrays.toString(heap.a));
	}
	int[] a;
	private int count = 0;
	private Best compare;
	public interface Best {
		public boolean isBetter(int a);
	}
	public static class Small implements Best {
		@Override
		public boolean isBetter(int a) {
			return a < 0;
		}
	}
	public static class Big implements Best {
		@Override
		public boolean isBetter(int a) {
			return a > 0;
		}
	}
	public HeapInt(int size, Best compare) {
		a = new int[size];
		this.compare = compare;
	}
	public static HeapInt createMin(int size) {
		return new HeapInt(size, new Small());
	}
	public static HeapInt createMax(int size) {
		return new HeapInt(size, new Big());
	}
	public void add(int value) {
		a[count] = value;
		siftUp();
		count++;
	}
	public int remove() {
		int current = a[0];
		a[0] = a[--count]; 
		shiftDown();
		return current;
	}
	private void siftUp() {
		for (int j = count; j > 0;) {
			int mid = (j-1) / 2;
			//compare.isBetter(a[j], a[mid])
			if (compare.isBetter(a[j]-a[mid])) {
				ArrayUtils.swap(a, mid, j);
				j = mid;
			} else {
				break;
			}
		}
	}
	private void shiftDown() {
		for (int i = 0; i < count;) {
			int j = i*2+1;
			int k = j + 1;
			if (j >= count && k >= count) break;
			if (k >= count || compare.isBetter(a[k]-a[j])) {
				if (compare.isBetter(a[j] - a[i])) {
					ArrayUtils.swap(a, i, j);
					i = j;
				} else break;
			} else if (compare.isBetter(a[k] - a[i])){
				ArrayUtils.swap(a, i, k);
				i = k;
			} else break;
		}
	}
	public int peek() {return a[0];}
	public int size() {return count;}
	public boolean isEmpty(){return count == 0;}
	public IteratorInt iterator() { 
		return new IteratorInt(){
			private final int size = count;
			private int i = -1;
			@Override
			public boolean hasNext() {
				return i < size - 1;
			}

			@Override
			public int next() {
				return a[++i];
			}
		
		}; 
	}
	public void copyTo(int[] array, int left) {
		for (IteratorInt iterator = iterator(); iterator.hasNext();) {
			array[left++] = iterator.next();
		}
	}
}
