package org.ip.array;

//EPI 2018: 5.8
public class Interleave2 {
	public static void main(String[] s) {
		int[] array = new int[] {5,1,3,0,4,2};
		new Interleave().solve(array);
		Utils.println(array, array.length);
	}
	public void solve(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			boolean even = i % 2 == 0;
			boolean greaterThan = array[i] > array[i] + 1;
			if (even && greaterThan) {
				Utils.swap(array, i, i + 1);
			} else if (!even && !greaterThan) {
				Utils.swap(array, i, i + 1);
			}
		}
	}
}
