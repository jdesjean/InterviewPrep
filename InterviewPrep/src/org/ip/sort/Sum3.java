package org.ip.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sum3 {
	public static void main(String[] s) {
		int target = 5;
		List<Triplet> triplets = solve(new int[]{9,7,5,3,1,0,-2,-4,-6,-8}, target);
		System.out.println(triplets);
	}
	public static class Triplet {
		public final int[] array;
		public final int i;
		public final int j;
		public final int k;
		public Triplet(int[] array, int i, int j, int k) {
			super();
			this.array = array;
			this.i = i;
			this.j = j;
			this.k = k;
		}
		@Override
		public String toString() {
			return "Triplet [i=" + array[i] + ", j=" + array[j] + ", k=" + array[k] + "]";
		}
	}
	public static Map<Integer,Integer> index(int[] array) {
		Map<Integer,Integer> values = new HashMap<Integer,Integer>();
		for (int i = 0; i < array.length; i++) {
			if (!values.containsKey(array[i])) {
				values.put(array[i],i);
			}
		}
		return values;
	}
	public static List<Triplet> solve(int[] array, int target){
		List<Triplet> triplets = new ArrayList<Triplet>();
		Arrays.sort(array);
		Map<Integer,Integer> values = index(array);
		for (int i = 0, j = array.length-1; i < j; ) {
			int value = target - array[i] - array[j];
			if (values.containsKey(value)) triplets.add(new Triplet(array,i++,j--,values.get(value)));
			else if (value > 0) i++;
			else j--;
		}
		return triplets;
	}
}
