package org.ip.sort;

import java.util.Arrays;

public interface PartitionInt {
	public static void main(String[] s) {
		testEvenize();
	}
	public static void testBalance() {
		PartitionInt[] partitions = new PartitionInt[] { new PartitionBalancedRecursive(), new PartitionBalancedDP() };
		for (PartitionInt partition : partitions) {
			int[] array = new int[] { 4, 1, -5, 6, -11, 3 };
			partition.partition(array,0,array.length-1,-1);
			System.out.println(Arrays.toString(array));
		}
	}
	public static void testPolarize() {
		PartitionInt[] partitions = new PartitionInt[] { new PartitionPolarized()};
		for (PartitionInt partition : partitions) {
			int[] array = new int[] { 4, 1, -5, 6, -11, 3 };
			partition.partition(array,0,array.length-1,-1);
			System.out.println(Arrays.toString(array));
		} 
	}
	public static void testEvenize() {
		PartitionInt[] partitions = new PartitionInt[] { new PartitionEvenized()};
		for (PartitionInt partition : partitions) {
			int[] array = new int[] { 4, 1, 5, 6, 11, 3 };
			partition.partition(array,0,array.length-1,-1);
			System.out.println(Arrays.toString(array));
		} 
	}
	public int partition(int[] array, int left, int right, int pivotIndex);
}
