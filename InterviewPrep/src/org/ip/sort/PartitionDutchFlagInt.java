package org.ip.sort;

import org.ip.array.Utils;

//EPI: 6.1
public class PartitionDutchFlagInt implements PartitionInt{
	public static void main(String[] s) {
		PartitionInt partition = new PartitionDutchFlagInt();
		int[] array = new int[] {1,4,3,8,2,9,4};
		partition.partition(array, 0, 6, 1);
		Utils.println(array, 7);
	}
	@Override
	public int partition(int[] array, int left, int right, int pivotIndex) {
		int pivot = array[pivotIndex];
		int i = left, j = left, k = right;
		for (;j <= k;) {
			if (array[j] < pivot) {
				Utils.swap(array, i++, j++);
			} else if (array[j] > pivot) {
				Utils.swap(array, j, k--);
			} else j++;
		}
		return i;
	}

}
