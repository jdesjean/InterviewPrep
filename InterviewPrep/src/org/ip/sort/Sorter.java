package org.ip.sort;

import org.ip.array.Utils;

public interface Sorter {
	public static void main(String[] s) {
		Sorter[] sorters = new Sorter[]{new SorterBucketInt(10), new SorterMerge(), new SorterQuick(), new SorterSelection(), new SorterHeap(), new SorterInsertion(), new SorterIntro()};
		for (Sorter sorter : sorters) {
			int[] array = new int[]{1,6,8,2,7,3,7,9,4,2,7,0,1,4,7};
			sorter.sort(array,0,array.length-1);
			Utils.println(array, 0, array.length-1);
		}
	}
	public void sort(int[] array, int left, int right);
}
