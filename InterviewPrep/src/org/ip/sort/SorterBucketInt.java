package org.ip.sort;

public class SorterBucketInt implements Sorter{
	private int[] buckets;
	public SorterBucketInt(int size) {
		buckets = new int[size]; 
	}
	@Override
	public void sort(int[] array, int left, int right) {
		for (int i = left; i <= right; i++) buckets[array[i]]++;
		for (int i = left, j = 0; i <= right; j++) {
			for (int k = 0; k < buckets[j]; k++) {
				array[i++] = j; 
			}
		}
	}
}
