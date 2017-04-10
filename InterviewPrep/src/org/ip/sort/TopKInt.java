package org.ip.sort;

import java.util.Arrays;

import org.ip.primitives.ArrayUtils;

public interface TopKInt {
	public static void main(String[] s) {
		int[] array = new int[]{5,7,8,3,2,7,8,3,7,9,2,1};
		TopKInt[] tops = new TopKInt[]{new TopKHeapInt(), new TopKPartitionIntMax()};
		int k = 3;
		for (int i = 0;i < tops.length; i++) {
			int[] copy = Arrays.copyOf(array, array.length);
			tops[i].solve(copy, k);
			ArrayUtils.println(copy, copy.length-k, copy.length-1);
		}
	}
	public void solve(int[] array, int k);
}
