package org.ip;

import java.util.Arrays;

public class ArrayUtils {
	public static void main(String[] s) {
		testPartition();
	}
	public static void testPartition() {
		Partition[] partitions = new Partition[]{new RecursivePartition(), new DPPartition()};
		
		for (Partition partition : partitions) {
			int[] array = new int[]{4,1,-5,6,-11,3};
			partition.balance(array);
			System.out.println(Arrays.toString(array));
		}
		
	}
	public static void testSum() {
		System.out.println(isSum(new int[]{2,4,8},6) + "==true");
		System.out.println(isSum(new int[]{2,-4,8},1) + "==false");
		System.out.println(isSum(new int[]{2,4,8},14) + "==true");
		System.out.println(isSum(new int[]{2,4,8},9) + "==false");
	}
	public static void reverse(char[] array) {
		for (int i = 0, j = array.length-1; i < j; i++, j--) {
			swap(array,i,j);
		}
	}
	public static void swap(char[] array, int i, int j) {
		char tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	public static void swap(int[] array, int i, int j) {
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	public interface PermutationVisitor {
		public void visit(int[] array);
	}
	public interface Filter {
		public boolean test(int[] array, int i);
	}
	public static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
	public static void println(int[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			System.out.print(array[i]);
		}
		System.out.println("");
	}
	public static void println(char[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			System.out.print(array[i]);
		}
		System.out.println("");
	}
	public static void print(char[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			System.out.print(array[i]);
		}
	}
	public static int min(int a, int b, int c) {
		return Math.min(a, Math.min(b, c));
	}
	public static int min(int[] array, int length) {
		int size = Math.min(array.length, length);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < size; i++) {
			min = Math.min(min, array[i]);
		}
		return min;
	}
	public static int sum(int[] array, int length) {
		int size = Math.min(array.length, length);
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum+=array[i];
		}
		return sum;
	}
	public static int sumNegatives(int[] array, int length) {
		int size = Math.min(array.length, length);
		int sum = 0;
		for (int i = 0; i < size; i++) {
			if (array[i] > 0) continue;
			sum+=array[i];
		}
		return sum;
	}
	public static int sumPositives(int[] array, int length) {
		int size = Math.min(array.length, length);
		int sum = 0;
		for (int i = 0; i < size; i++) {
			if (array[i] < 0) continue;
			sum+=array[i];
		}
		return sum;
	}
	public static boolean isSum(int[] array, int target) {
		return isSum(array, 0, new int[array.length], 0, target);
	}
	private static boolean isSum(int[] array, int read, int[] buffer, int write, int target) {
		if (read == array.length) {
			return sum(array,write) == target;
		}
		buffer[write] = buffer[read];
		return isSum(array,read+1,buffer,write+1,target) || isSum(array,read+1,buffer,write,target);  
	}
	public interface Partition {
		public boolean balance(int[] array);
	}
	public static class DPPartition implements Partition {

		@Override
		public boolean balance(int[] array) {
			int sum = sum(array,array.length);
			if (sum % 2 != 0) return false; //impossible
			int target = sum/2;
			int sumNegatives = sumNegatives(array,array.length);
			int sumPositives = sumPositives(array,array.length);
			int total = Math.abs(sumNegatives) + sumPositives+1;
			int indexZero = Math.abs(sumNegatives);
			int indexTarget = indexZero+target;
			int[] cache = new int[total];
			cache[indexZero] = 1; // zero
			
			for (int i = 0; i < array.length; i++) {
				if (array[i] > 0) {
					for (int j = total-1, k = j-array[i]; k >= 0; j--,k--) {
						if (cache[j] != 0 || cache[k] == 0) continue;
						cache[j] = array[i];
					}
				} else if (array[i] < 0) {
					for (int j = 0, k = j-array[i]; k < cache.length; j++,k++) {
						if (cache[j] != 0 || cache[k] == 0) continue;
						cache[j] = array[i]; 
					}
				}
			}
			if (cache[indexTarget] == 0) return false; //cannot partition
			for (int i = indexTarget, j = 0; i != indexZero && j < array.length; i-=cache[i], j++) {
				for (int k = 0; k < array.length; k++) {
					if (array[k] != cache[i]) continue;
					swap(array,k,j);
					break;
				}
			}
			return true;
		}
		
	}
	public static class RecursivePartition implements Partition {

		@Override
		public boolean balance(int[] array) {
			int sum = sum(array,array.length);
			return balance(array,sum/2,array.length);
		}
		private static boolean balance(int[] array, int target, int depth) {
			if (target == 0) return true;
			if (depth == 0) return false;
			return balance(array,target,depth-1) || balance(array,target-array[depth-1],depth-1);
		}
	}
}
