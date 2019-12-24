package org.ip.array;

// EPI 2018: 5.1
public class Partitioner {
	public static void main(String[] s) {
		int[] a = new int[] {0,1,2,0,2,1,1};
		new Partitioner().partition(a, 2);
		Utils.println(a, a.length);
	}
	public void partition(int[] array, int pivot) {
		int value = array[pivot];
		for (int l = 0, r = array.length - 1, m = l; m <= r;) {
			if (array[m] < value) {
				Utils.swap(array, l++, m++);
			} else if (array[m] == value) {
				m++;
			} else {
				Utils.swap(array, r--, m);
			}
		}
	}
}
