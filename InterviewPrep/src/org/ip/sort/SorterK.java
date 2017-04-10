package org.ip.sort;

import org.ip.primitives.ArrayUtils;
import org.ip.sort.HeapInt.Small;

public class SorterK implements Sorter{
	public static void main(String[] s) {
		int[] array = new int[]{1,2,0,4,5,4,6,7,8,9,7};
		int k = 3;
		SorterK sorter = new SorterK(k);
		sorter.sort(array, 0, array.length-1);
		ArrayUtils.println(array,0,array.length-1);
	}
	private int k;
	public SorterK(int k) {
		this.k = k;
	}
	@Override
	public void sort(int[] array, int left, int right) {
		int[] tmp = new int[k+1];
		for (int i = 0, j = left; i < k+1; i++,j++) {
			tmp[i] = array[j];
		}
		HeapInt heap = new HeapInt(tmp, 0, k, new Small());
		for (int i = left + k + 1, j = left; j <= right; i++, j++) {
			if (i <= right)
	            array[j] = heap.replace(array[i]);
	        else
	        	array[j] = heap.remove();
		}
	}
}
