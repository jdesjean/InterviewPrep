package org.ip.array;

//EPI 2018: 5.4
public class Advance2 {
	public static void main(String[] s) {
		int[] array = new int[] {3,3,1,0,2,0,1};
		System.out.println(new Advance2().solve(array));
		array = new int[] {3,2,1,0,2,0,1};
		System.out.println(new Advance2().solve(array));
	}
	public boolean solve(int[] array) {
		if (array.length == 0) return true;
		for (int i = 1, j = array[0]; i < array.length; i++) {
			j--;
			if (j < 0) return false;
			j = Math.max(j, array[i]);
		}
		return true;
	}
}
