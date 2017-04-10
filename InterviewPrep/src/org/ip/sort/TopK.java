package org.ip.sort;

import java.util.Arrays;
import java.util.Comparator;

import org.ip.primitives.ArrayUtils;

public interface TopK<T> {
	public final static Comparator<Integer> COMPARATOR_INTEGER = new Comparator<Integer>(){

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1 - o2;
		}
		
	};
	public static void main(String[] s) {
		Integer[] array = new Integer[]{5,7,8,3,2,7,8,3,7,9,2,1};
		TopK<Integer>[] tops = new TopK[]{new TopKPartitionMin<Integer>()};
		int k = 3;
		for (int i = 0;i < tops.length; i++) {
			Integer[] copy = Arrays.copyOf(array, array.length);
			tops[i].solve(copy, k,COMPARATOR_INTEGER);
			ArrayUtils.println(copy, copy.length-k, copy.length-1);
		}
	}
	public void solve(T[] array, int k, Comparator<T> comparator);
}
