package org.ip.array;

import java.util.Arrays;

public interface Partition {
	public void partition(int[] array);
	public static void main(String[] s) {
		Partition[] partitions = new Partition[] { new RecursivePartitionBalanced(), new DPPartitionBalanced() };
		for (Partition partition : partitions) {
			int[] array = new int[] { 4, 1, -5, 6, -11, 3 };
			partition.partition(array);
			System.out.println(Arrays.toString(array));
		}
	}
	public static class RecursivePartitionBalanced implements Partition {

		@Override
		public void partition(int[] array) {
			int[] buffer = new int[array.length];
			int sum = Utils.sum(array, array.length);
			if (sum % 2 == 1) return;
			partition(array, buffer, sum / 2, 0, 0, 0);
		}
		public boolean partition(int[] array, int[] buffer, int target, int current, int w, int i) {
			if (current == target) {
				Utils.print(buffer, w);
				return true;
			}
			if (i >= array.length) {
				return false;
			}
			buffer[w] = array[i];
			return partition(array,buffer,target,current+array[i],w + 1,i + 1) || 
			partition(array,buffer,target,current,w,i + 1);
			
		}
		
	}
	public static class DPPartitionBalanced implements Partition {

		@Override
		public void partition(int[] array) {
			int sum = Utils.sum(array, array.length);
			if (sum % 2 == 1) return;
			boolean[][] cache = new boolean[sum / 2][array.length + 1];
			for (int i = 0; i <= array.length; i++) {
				cache[0][i] = true;
			}
			for (int l = 1; l <= sum / 2; l++) {
				for (int c = 1; c <= array.length + 1; c++) {
					cache[l][c] = cache[l][c] || cache[l - array[c - 1]][c - 1]; 
				}
			}
		}
		
	}
}
