package org.ip.greedy;

// EPI 2018: 17.5
public class Majority2 {
	public static void main(String[] s) {
		int[] array = new int[] {1,3,1,1,2,2,1};
		System.out.println(solve(array));
		array = new int[] {1,2,2,3,1,1,1};
		System.out.println(solve(array));
		array = new int[] {1,1,2};
		System.out.println(solve(array));
		array = new int[] {2,1,1};
		System.out.println(solve(array));
		array = new int[] {1,2,1};
		System.out.println(solve(array));
		array = new int[] {1,1};
		System.out.println(solve(array));
		array = new int[] {1};
		System.out.println(solve(array));
	}
	public static int solve(int[] array) {
		int value = array[0];
		for (int i = 1, current = 1; i < array.length; i++) {
			if (array[i] == value) {
				current++;
			} else if (current == 0){
				current = 1;
				value = array[i];
			} else {
				current--;
			}
		}
		return value;
	}
}
