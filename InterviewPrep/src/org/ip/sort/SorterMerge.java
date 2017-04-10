package org.ip.sort;

public class SorterMerge implements Sorter{
	private final static SelectorInt selector = new SelectorMidInt();
	private int[] buffer;
	@Override
	public void sort(int[] array, int left, int right) {
		this.buffer = new int[right-left+1];
		divide(array,left,right);
	}
	private void divide(int[] array, int left, int right) {
		if (left >= right) return;
		int mid = selector.select(array,left,right);
		divide(array,left,mid);
		divide(array,mid+1,right);
		merge(array,left,mid+1,right);
	}
	private void merge(int[] array, int left, int mid, int right) {
		if (array[mid-1] < array[mid]) return; //already merged
		for (int i = left; i <= right; i++) { buffer[i] = array[i]; }
		for (int i = left, j = mid, k = left; i < mid; k++) {
			array[k] = j > right || buffer[i] <= buffer[j] ? buffer[i++] : buffer[j++];
		}
	}
}
