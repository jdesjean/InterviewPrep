package org.ip.array;

//EPI 2018: 5.11
public class Permutator2 {
	public static void main(String[] s) {
		//1234 -> 1243
		//1243 -> 1324
		//1032 -> 1203
		int n = 1*2*3*4;
		int[] array = new int[] {1,2,3,4};
		Utils.println(array);
		Permutator2 p = new Permutator2(); 
		for (int i = 1; i < n; i++) {
			p.next(array);
			Utils.println(array);
		}
	}
	public void next(int[] array) {
		//walk aback to find first element out of order descending order
		int idx = array.length - 2;
		for (; idx >= 0; idx--) {
			if  (array[idx] < array[idx + 1]) {
				break;
			}
		}
		if (idx < 0) {
			return; // No next
		}
		// Find next biggest number. They're conveniently in reverse order
		for (int i = array.length - 1; i > idx; i--) {
			if (array[i] > array[idx]) {
				Utils.swap(array, idx, i);
				break;
			}
		}
		// reverse the array to ascending order
		reverse(array, idx + 1, array.length - 1);
	}
	public static void reverse(int[] array, int l, int r) {
		for (int i = l, j = r; i < j; i++, j--) {
			Utils.swap(array, i, j);
		}
	}
}
