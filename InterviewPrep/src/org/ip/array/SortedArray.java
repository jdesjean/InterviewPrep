package org.ip.array;

// EPI 2018: 5.5
public class SortedArray {
	public static void main(String[] s) {
		SortedArrayRemoveDups[] algos = new SortedArrayRemoveDups[] {new SortedArray1(), new SortedArray2()};
		for (SortedArrayRemoveDups algo : algos) {
			int[] array = new int[] {2,3,5,5,7,11,11,11,13};
			int length = algo.removeDups(array);
			Utils.println(array, length);
			array = new int[] {2,5,5,5,5,11,11,11,13};
			length = algo.removeDups(array);
			Utils.println(array, length);
			array = new int[] {2,5,5,5,5,11,11,11,11};
			length = algo.removeDups(array);
			Utils.println(array, length);
			array = new int[] {2,5,5,5,5,11,12,13};
			length = algo.removeDups(array);
			Utils.println(array, length);
			System.out.println("**");
		}
	}
	
	public interface SortedArrayRemoveDups {
		public int removeDups(int[] array);
	}
	public static class SortedArray1 implements SortedArrayRemoveDups {
		public int removeDups(int[] array) {
			int i = 0, j = 1;
			for (; j < array.length;) {
				if (array[i] == array[j]) {
					j++;
				} else if (j - i > 1){
					array[++i] = array[j];
					j++;
				} else {
					i++;
					j++;
				}
			}
			return i + 1;
		}
	}
	public static class SortedArray2 implements SortedArrayRemoveDups {

		@Override
		public int removeDups(int[] array) {
			if (array.length == 0) return 0;
			int j = 1;
			for (int i = 1; i < array.length; i++) {
				if (array[j - 1] != array[i]) {
					array[j++] = array[i];
				}
			}
			return j;
		}
		
	}
}
