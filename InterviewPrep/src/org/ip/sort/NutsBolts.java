package org.ip.sort;

import java.util.Arrays;

import org.ip.array.ArrayUtils;

public class NutsBolts {
	private final static SelectorInt selection = new SelectorMidInt();
	public static void main(String[] s) {
		int[] nuts = new int[]{2,5,5,1,6,3};
		int[] bolts = new int[]{5,3,6,2,1,5};
		sort(nuts,bolts);
		System.out.println(Arrays.toString(nuts));
		System.out.println(Arrays.toString(bolts));
	}
	public static void sort(int[] nuts, int[] bolts) {
		quicksort(nuts,bolts,0,nuts.length-1, new PartitionDutchFlagInt());
	}
	public static void quicksort(int[] nuts, int[] bolts, int left, int right, PartitionInt partition) {
		if (left >= right) return;
		int indexPivot = selection.select(nuts, left, right);
		int indexPartition = partition.partition(nuts,left,right,indexPivot);
		partition.partition(bolts,left,right,ArrayUtils.indexOf(bolts,left,right,nuts[indexPartition]));
		quicksort(nuts,bolts,left,indexPartition-1, partition);
		quicksort(nuts,bolts,indexPartition+1,right, partition);
	}
}
