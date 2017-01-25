package org.ip;

public class ArrayUtils {
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
	public static int[] permutations(PermutationVisitor visitor, Filter filter, int[] array, int index) {
		if (index == array.length) {
			visitor.visit(array);
		}
		for (int i = index; i < array.length; i++) {
			ArrayUtils.swap(array,i,index);
			if (filter.test(array,index)) {
				permutations(visitor,filter,array,index+1);
			}
			ArrayUtils.swap(array,i,index);
		}
		return array;
	}
	public static void println(int[] array, int length) {
		int size = Math.min(array.length, length);
		for (int i = 0; i < size; i++) {
			System.out.print(array[i]);
		}
		System.out.println("");
	}
}
