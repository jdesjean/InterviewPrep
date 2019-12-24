package org.ip.greedy;

// EPI: 18.5
public class Majority {
	public static void main(String[] s) {
		int[] array = new int[] {2,1,3,1,1,2,1,1,3,1};
		System.out.println(solve(array));
		array = new int[] {1,3,1,1,2,2};
		System.out.println(solve(array));
	}
	public static int solve(int[] array) {
		int e = array[0];
		for (int i = 1, count = 1; i < array.length; i++) {
			if (array[i] == e) {
				count++;
			} else if (count > 1){
				count--;
			} else {
				count = 1;
				e = array[i];
			}
		}
		return e;
	}
}
