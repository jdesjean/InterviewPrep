package org.ip.array;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Utils {
	public static void main(String[] s) {
		testSumZero();
	}

	public static void testSumZero() {
		System.out.println(sumZero(new int[] { 5, 1, 2, -3, 7, -4 }));
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
	
	public static void swap(double[] array, int i, int j) {
		double tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
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

	public static void println(Object[] array) {
		println(array, array.length);
	}
	
	public static void println(Object[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			if (i != 0)
				System.out.print(",");
			System.out.print(array[i]);
		}
		System.out.println("");
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
	
	public static void println(int[] array) {
		println(array, array.length);
	}

	public static void println(int[] array, int length) {
		println(array, 0, Math.min(array.length, length) - 1);
	}

	public static <T> void println(T[] array, int left, int right) {
		for (int i = left; i <= right; i++) {
			if (i != left)
				System.out.print(",");
			System.out.print(array[i]);
		}
		System.out.println("");
	}
	
	public static void println(char[] array) { 
		println(array, array.length, true);
	}
	
	public static void println(char[] array, int length) {
		println(array, length, true);
	}

	public static void println(char[] array, int length, boolean printComa) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			if (i != 0 && printComa)
				System.out.print(",");
			System.out.print(array[i]);
		}
		System.out.println("");
	}

	public static void println(CharSequence array) {
		for (int i = 0; i < array.length(); i++) {
			if (i != 0)
				System.out.print(",");
			System.out.print(array.charAt(i));
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
	
	public static void print(Object array) {
		if (array instanceof int[][]) {
			Utils.print((int[][]) array);
		} else if (array instanceof int[]) {
			Utils.print((int[]) array);
		} else if (array instanceof double[]) {
			Utils.print((double[]) array);
		} else if (array instanceof Integer[]) {
			System.out.print(Arrays.toString((Integer[])array));
		} else if (array instanceof Boolean[]) {
			System.out.print(Arrays.toString((Boolean[])array));
		} else if (array instanceof char[][]) {
			Utils.print((char[][]) array);
		} else if (array instanceof String[][]) {
			Utils.print((String[][]) array);
		} else if (array != null) {
			System.out.print(array.toString());
		} else {
			System.out.print("[]");
		}
	}
	
	public static void print(String[][] array) {
		print(System.out, array);
	}
	
	public static void print(PrintStream os, String[][] array) {
		os.print("[");
		for (int r = 0; r < array.length; r++) {
			if (r != 0) {
				os.print(",");
			}
			os.println(Arrays.toString(array[r]));
		}
		os.print("]");
	}
	
	public static void print(PrintStream os, Integer[][] array) {
		os.print("[");
		for (int r = 0; r < array.length; r++) {
			if (r != 0) {
				os.print(",");
			}
			os.print(Arrays.toString(array[r]));
		}
		os.print("]");
	}
	
	public static void print(double[] array) {
		System.out.print("[");
		for (int i = 0; i < array.length; i++) {
			if (i != 0)
				System.out.print(", ");
			System.out.print(array[i]);
		}
		System.out.print("]");
	}
	
	public static void print(int[] array) {
		print(array, array.length);
	}
	
	public static void print(boolean[] array) {
		print(array, array.length);
	}
	
	public static void print(char[][] array) {
		print(System.out, array);
	}
	
	public static void print(PrintStream os, char[][] array) {
		os.print("[");
		for (int r = 0; r < array.length; r++) {
			if (r != 0) {
				os.print(",");
				os.println();
				os.print(' ');
			}
			os.print(Arrays.toString(array[r]));
		}
		os.print("]");
	}
	
	public static void print(char[] array) {
		print(array, array.length);
	}
	
	public static void print(boolean[] array, int length) {
		for (int i = 0; i < length; i++) {
			if (i != 0)
				System.out.print(",");
			System.out.print(array[i]);
		}
	}
	
	public static void print(int[] array, int length) {
		int size = Math.min(array.length, length);
		System.out.print("[");
		for (int i = 0; i < size; i++) {
			if (i != 0)
				System.out.print(", ");
			System.out.print(array[i]);
		}
		System.out.print("]");
	}
	
	public static void print(int[][] array) {
		print(System.out, array);
	}
	
	public static void print(PrintStream os, int[][] array) {
		os.print("[");
		for (int r = 0; r < array.length; r++) {
			if (r != 0) {
				os.print(",");
				os.println();
				os.print(' ');
			}
			os.print(Arrays.toString(array[r]));
		}
		os.print("]");
	}

	public static int min(int a, int b, int c) {
		return Math.min(a, Math.min(b, c));
	}
	
	public static int min(int[] a) {
		return min(a, a.length);
	}
	
	public static int minIndex(int[] a) {
		return minIndex(a, a.length);
	}

	public static int min(int[] array, int length) {
		int size = Math.min(array.length, length);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < size; i++) {
			min = Math.min(min, array[i]);
		}
		return min;
	}
	
	public static int minIndex(int[] array, int length) {
		if (array == null || array.length == 0) return -1;
		int size = Math.min(array.length, length);
		int min = 0;
		for (int i = 1; i < size; i++) {
			if (array[i] < array[min]) {
				min = i;
			}
		}
		return min;
	}

	public static int indexOf(int[] a, int left, int right, int value) {
		for (int i = left; i <= right; i++) {
			if (a[i] == value)
				return i;
		}
		return -1;
	}

	public static int indexOf(char[] a, int left, int right, char value) {
		for (int i = left; i <= right; i++) {
			if (a[i] == value)
				return i;
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
		return sum(array, 0, Math.min(array.length, length) - 1);
	}
	
	public static int sum(int[] array) {
		return sum(array, 0, array.length - 1);
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
	public static int shiftBackward(int[] array, int i, int j) {
		int k = array[i];
		for (; j > i; i++) {
			array[i] = array[i + 1];
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

	public static void rotate(int[] array, int d) {
		int i, j, k, temp;
		int n = array.length;
		int gcd = org.ip.primitives.Utils.gcd(n, d);
		for (i = 0; i < gcd; i++) {
			/* move i-th values of blocks */
			temp = array[i];
			j = i;
			while (true) {
				k = j + d;
				if (k >= n)
					k = k - n;
				if (k == i)
					break;
				array[j] = array[k];
				j = k;
			}
			array[j] = temp;
		}
	}
	
	public static void arraycopy(int[][] source, int[][] target) {
		for(int i = 0; i < source.length; i++) {
		  System.arraycopy(source[i], 0, target[i], 0, source[i].length);
		}
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
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int sum = 0;
		;
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
			if (sum == 0)
				return new Pair(0, i);
			else if (map.containsKey(sum))
				return new Pair(map.get(sum) + 1, i);
			map.put(sum, i);
		}
		return null;
	}

	public static void println(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (j > 0)
					System.out.print(",");
				System.out.print(array[i][j]);
			}
			System.out.println();
		}
	}
	public static void println(boolean[][] map) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print("," + (map[i][j] ? "1" : "0"));
			}
			System.out.println();
		}
	}
	public static int binarySearch(int[] a,int value) {
		return binarySearch(a,0,a.length-1,value);
	}
	public static int binarySearch(int[] a, int left, int right, int value) {
		int i = left, j = right, mid =0;
		while (i <= j) {
			mid = i+(j-i)/2;
			if (a[mid] == value) return mid;
			else if (a[mid] < value) i = mid+1;
			else if (a[mid] > value) j = mid-1;
		}
		return -1*mid;
	}
}
