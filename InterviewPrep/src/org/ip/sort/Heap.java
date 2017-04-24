package org.ip.sort;

import java.util.Arrays;

import org.ip.array.ArrayUtils;

public class Heap<T extends Comparable<T>> {
	public static void main(String[] s) {
		Heap<Integer> heap = new Heap<Integer>(10,new Small());
		for (int i = 10; i > 0; i--) {
			heap.add(i);
		}
		System.out.println(Arrays.toString(heap.a));
		heap = new Heap<Integer>(10,new Big());
		for (int i = 0; i < 10; i++) {
			heap.add(i);
		}
		System.out.println(Arrays.toString(heap.a));
	}
	T[] a;
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
	public Heap(int size, Best compare) {
		a = (T[])new Comparable[size];
		this.compare = compare;
	}
	public static <T extends Comparable<T>> Heap<T> createMin(int size) {
		return new Heap<T>(size, new Small());
	}
	public static <T extends Comparable<T>> Heap<T> createMax(int size) {
		return new Heap<T>(size, new Big());
	}
	public void add(T value) {
		a[count] = value;
		siftUp();
		count++;
	}
	public T remove() {
		T current = a[0];
		a[0] = a[count-1]; 
		shiftDown();
		count--;
		return current;
	}
	private void siftUp() {
		for (int j = count; j > 0;) {
			int mid = (j-1) / 2;
			if (compare.isBetter(a[j].compareTo(a[mid]))) {
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
			if (k >= count || compare.isBetter(a[j].compareTo(a[k]))) {
				if (compare.isBetter(a[j].compareTo(a[i]))) {
					ArrayUtils.swap(a, i, j);
					i = j;
				} else break;
			} else if (compare.isBetter(a[k].compareTo(a[i]))){
				ArrayUtils.swap(a, i, k);
				i = k;
			} else break;
		}
	}
	public T get() {return a[0];}
	public int size() {return count;}
	public boolean isEmpty(){return count == 0;}
}
