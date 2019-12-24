package org.ip.array;

public class Permutator22 {
	public static void main(String[] s) {
		//1234 -> 1243
		//1243 -> 1324
		//1032 -> 1203
		int n = 1*2*3*4;
		int[] array = new int[] {1,2,3,4};
		Utils.println(array);
		Permutator22 p = new Permutator22(); 
		for (int i = 1; i < n; i++) {
			p.next(array);
			Utils.println(array);
		}
	}
	public void next(int[] array) {
		int l = array.length - 2;
		for (; l >= 0; l--) {
			if (array[l] < array[l + 1]) break;
		}
		if (l < 0) throw new IllegalArgumentException("No next");
		for (int i = array.length - 1; i > l; i--) {
			if (array[i] > array[l]) {
				Utils.swap(array, i, l);
				break;
			}
		}
		Utils.reverse(array, l + 1, array.length - 1);
	}
}
 