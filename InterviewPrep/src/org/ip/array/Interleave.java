package org.ip.array;

//EPI 2018: 5.8
public class Interleave {
	public static void main(String[] s) {
		int[] array = new int[] {5,1,3,0,4,2};
		new Interleave().solve(array);
		Utils.println(array, array.length);
	}
	public void solve(int[] array) {
		for (int i = 1; i < array.length; i+= 2) {
			int max = i;
			if (array[i - 1] > array[max]) {
				max = i - 1;
			}
			if (i + 1 < array.length && array[i + 1] > array[max]) {
				max = i + 1;
			}
			if (max != i) {
				Utils.swap(array, max, i);
			}
		}
	}
}
