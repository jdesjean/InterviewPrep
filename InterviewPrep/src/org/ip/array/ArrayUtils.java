package org.ip.array;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ArrayUtils {
	public static void main(String[] s) {
		testSumZero();
	}
	public static void testSumZero() {
		System.out.println(sumZero(new int[]{5,1,2,-3,7,-4}));
	}
	public static void testMinimumRotated() {
		System.out.println(minimumRotated(new int[]{5,6,1,2,3,4}));
		System.out.println(minimumRotated(new int[]{1,2,3,4,5,6}));
		System.out.println(minimumRotated(new int[]{2,3,4,5,6,1}));
		System.out.println(minimumRotated(new int[]{4,4,1,2,4,4}));
		System.out.println(minimumRotated(new int[]{4,1,2,3,4,4}));
	}
	public static void testToFile() {
		toFile(new long[] { 1 }, 1);
	}

	public static void testSum() {
		System.out.println(isSum(new int[] { 2, 4, 8 }, 6) + "==true");
		System.out.println(isSum(new int[] { 2, -4, 8 }, 1) + "==false");
		System.out.println(isSum(new int[] { 2, 4, 8 }, 14) + "==true");
		System.out.println(isSum(new int[] { 2, 4, 8 }, 9) + "==false");
	}

	public static void reverse(char[] array) {
		reverse(array, 0, array.length - 1);
	}

	public static void reverse(char[] array, int i, int j) {
		for (; i < j; i++, j--) {
			swap(array, i, j);
		}
	}

	public static String reverse(String string) {
		char[] array = string.toCharArray();
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			swap(array, i, j);
		}
		return String.copyValueOf(array);
	}

	public static void swap(Object[] array, int i, int j) {
		Object tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
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

	public static void reverse(int[] array, int i, int j) {
		for (; i < j; i++, j--) {
			swap(array, i, j);
		}
	}

	public static void reverse(Object[] array, int i, int j) {
		for (; i < j; i++, j--) {
			swap(array, i, j);
		}
	}

	public interface PermutationVisitor {
		public void visit(int[] array);
	}

	public interface Filter {
		public boolean test(int[] array, int i);
	}

	public static void toFile(long[] array, int length) {
		File file = new File("C:\\Users\\Jean-Francois\\Downloads\\prime.txt");
		try {
			PrintWriter out = new PrintWriter(file, "UTF-8");
			int size = Math.min(array.length, length);
			for (int i = 0; i < size; i++) {
				if (i != 0)
					out.print(",");
				out.print(array[i]);
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void println(long[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			if (i != 0)
				System.out.print(",");
			System.out.print(array[i]);
		}
		System.out.println("");
	}

	public static void println(int[] array, int left, int right) {
		for (int i = left; i <= right; i++) {
			if (i != left)
				System.out.print(",");
			System.out.print(array[i]);
		}
		System.out.println("");
	}
	
	public static void println(int[] array, int length) {
		println(array,0,Math.min(array.length, length));
	}
	
	public static <T> void println(T[] array, int left, int right) {
		for (int i = left; i <= right; i++) {
			if (i != left)
				System.out.print(",");
			System.out.print(array[i]);
		}
		System.out.println("");
	}

	public static void println(char[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			if (i != 0)
				System.out.print(",");
			System.out.print(array[i]);
		}
		System.out.println("");
	}
	public static void println2(char[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			System.out.print(array[i]);
		}
		System.out.println("");
	}

	public static void print(char[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			if (i != 0)
				System.out.print(",");
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

	public static int indexOf(int[] a, int left, int right, int value) {
		for (int i = left; i <= right; i++) {
			if (a[i] == value) return i;
		}
		return -1;
	}
	public static int indexOf(char[] a, int left, int right, char value) {
		for (int i = left; i <= right; i++) {
			if (a[i] == value) return i;
		}
		return -1;
	}
	
	public static int sum(int[] array, int left, int right) {
		int sum = 0;
		for (int i = left; i <= right; i++) {
			sum += array[i];
		}
		return sum;
	}
	
	public static int sum(int[] array, int length) {
		return sum(array,0,Math.min(array.length, length)-1);
	}

	public static int sumNegatives(int[] array, int left, int right) {
		int sum = 0;
		for (int i = left; i <= right; i++) {
			if (array[i] > 0)
				continue;
			sum += array[i];
		}
		return sum;
	}
	
	public static int sumPositives(int[] array, int left, int right) {
		int sum = 0;
		for (int i = left; i <= right; i++) {
			if (array[i] < 0)
				continue;
			sum += array[i];
		}
		return sum;
	}

	public static boolean isSum(int[] array, int target) {
		return isSum(array, 0, new int[array.length], 0, target);
	}

	private static boolean isSum(int[] array, int read, int[] buffer, int write, int target) {
		if (read == array.length) {
			return sum(array, write) == target;
		}
		buffer[write] = buffer[read];
		return isSum(array, read + 1, buffer, write + 1, target) || isSum(array, read + 1, buffer, write, target);
	}

	public static <T extends Comparable<T>> T max(T[] array) {
		T max = null;
		for (int i = 0; i < array.length; i++) {
			max = max == null ? array[i] : array[i] == null ? max : max.compareTo(array[i]) >= 0 ? max : array[i];
		}
		return max;
	}

	public static int max(int[] array) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < array.length; i++) {
			max = Math.max(max, array[i]);
		}
		return max;
	}

	public static int shift(int[] array, int i, int j) {
		int k = array[j];
		for (; j > i; j--) {
			array[j] = array[j - 1];
		}
		return k;
	}

	public static int nextPositive(int[] array, int i) {
		for (; i < array.length; i++) {
			if (array[i] >= 0)
				return i;
		}
		return -1;
	}

	public static int nextNegative(int[] array, int i) {
		for (; i < array.length; i++) {
			if (array[i] < 0)
				return i;
		}
		return -1;
	}

	public static int minimumRotated(int[] rotated) {
		return minimumRotated(rotated,0,rotated.length-1,-1);
	}
	public static int minimumRotated(int[] rotated, int i, int j, int k) {
		for (;i < j;) {
			int mid = (j-i)/2+i;
			if (rotated[i] < rotated[mid]) {
				if (k == -1 || rotated[i] < rotated[k]) k = i;
				i = mid+1;
			} else if (rotated[mid] < rotated[j]) {
				if (k == -1 || rotated[mid] < rotated[k]) k = mid;
				j = mid-1;
			} else {
				int k1 = minimumRotated(rotated,i,mid,k);
				int k2 = minimumRotated(rotated,mid+1,j,k);
				if (k1 == -1) return k2;
				else if (k2 == -1) return k1;
				else if (rotated[k1] <= rotated[k2]) return k1;
				else return k2;
			}
		}
		if (i == j && (k == -1 || rotated[i] < rotated[k])) k = i; 
		return k;
	}
	
	public static class Pair {
		int left;
		public Pair(int left, int right) {
			super();
			this.left = left;
			this.right = right;
		}
		int right;
		@Override
		public String toString() {
			return "Pair [left=" + left + ", right=" + right + "]";
		}
	}
	public static Pair sumZero(int[] array) {
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		int sum = 0;;
		for (int i = 0; i < array.length; i++) {
			sum+=array[i];
			if (sum == 0) return new Pair(0,i);
			else if  (map.containsKey(sum)) return new Pair(map.get(sum)+1,i);
			map.put(sum, i);
		}
		return null;
	}
	public static void println(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (j > 0) System.out.print(",");
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}
}
