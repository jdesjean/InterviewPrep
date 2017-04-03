package org.ip.sort;

import org.ip.ArrayUtils;

public class SortMerge {
	private final static SelectorInt selector = new SelectorMidInt();
	public static void main(String[] s) {
		int[] array = new int[]{1,6,8,2,7,3,7,9,4,2,7,0,1,4,7};
		SortMerge sort = new SortMerge();
		sort.sort(array,0,array.length-1);
		ArrayUtils.println(array, 0, array.length-1);
	}
	public void sort(int[] array, int left, int right) {
		divide(array,left,right,new int[right-left+1]);
	}
	private void divide(int[] array, int left, int right, int[] buffer) {
		if (left >= right) return;
		int mid = selector.select(array,left,right);
		divide(array,left,mid,buffer);
		divide(array,mid+1,right,buffer);
		merge(array,left,mid+1,right,buffer);
	}
	private void merge(int[] array, int left, int mid, int right, int[] buffer) {
		for (int i = left; i <= right; i++) { buffer[i] = array[i]; }
		for (int i = left, j = mid, k = left; i < mid || j <= right; k++) {
			if ( j > right ) array[k] = buffer[i++];
			else if ( i >= mid ) array[k] = buffer[j++];
			else if (buffer[i] <= buffer[j]) array[k] = buffer[i++];
			else array[k] = buffer[j++];
		}
	}
}
