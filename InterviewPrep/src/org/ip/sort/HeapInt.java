package org.ip.sort;

import java.util.Arrays;

import org.ip.array.Utils;

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
	private int left = 0;
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
	public HeapInt(int[] a, int left, int right, Best compare) {
		this.a = a;
		this.compare = compare;
		count = 1;
		this.left  = left;
		int size = right - left + 1;
		for (; count < size; count++) {
			siftUp();
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
	protected int first() {
		return left;
	}
	protected int last() {
		return left+count;
	}
	protected int parent(int i) {
		return left+(i-left-1) / 2;
	}
	protected int left(int i) {
		return left+(i-left)*2+1;
	}
	protected int right(int i) {
		return left+(i-left)*2+2;
	}
	public void add(int value) {
		a[last()] = value;
		siftUp();
		count++;
	}
	public int remove() {
		int current = a[first()];
		count--;
		a[first()] = a[last()]; 
		shiftDown();
		return current;
	}
	public int replace(int value) {
		int current = a[first()];
		a[last()] = value;
		shiftDown();
		return current;
	}
	private void siftUp() {
		for (int j = last(); j > first();) {
			int mid = parent(j);
			//compare.isBetter(a[j], a[mid])
			if (compare.isBetter(a[j]-a[mid])) {
				Utils.swap(a, mid, j);
				j = mid;
			} else {
				break;
			}
		}
	}
	private void shiftDown() {
		for (int i = first(); i <= last();) {
			int j = left(i);
			int k = right(i);
			if (j >= count && k >= count) break;
			if (k >= count || compare.isBetter(a[j] - a[k])) {
				if (compare.isBetter(a[j] - a[i])) {
					Utils.swap(a, i, j);
					i = j;
				} else break;
			} else if (compare.isBetter(a[k] - a[i])){
				Utils.swap(a, i, k);
				i = k;
			} else break;
		}
	}
	public int peek() {return a[first()];}
	public int size() {return count;}
	public boolean isEmpty(){return count == 0;}
	public IteratorInt iterator() { 
		return new IteratorInt(){
			private final int size = count;
			private int i = a.length;
			@Override
			public boolean hasNext() {
				return a.length-i < size;
			}

			@Override
			public int next() {
				return a[--i];
			}
		
		}; 
	}
	public void copyTo(int[] array, int left) {
		for (IteratorInt iterator = iterator(); iterator.hasNext();) {
			array[left++] = iterator.next();
		}
	}
}