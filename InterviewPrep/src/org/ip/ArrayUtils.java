package org.ip;

public class ArrayUtils {
	public static void main(String[] s) {
		testSum();
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
	public static int sum(int[] array, int length) {
		int sum = 0;
		for (int i = 0; i < length; i++) {
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
}
