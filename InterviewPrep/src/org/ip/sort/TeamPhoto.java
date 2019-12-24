package org.ip.sort;

import java.util.Arrays;

//EPI 2018: 13.10
public class TeamPhoto {
	public static void main(String[] s) {
		int[] a1 = new int[] {1,2,3,4};
		int[] a2 = new int[] {2,2,5,5};
		int[] a3 = new int[] {2,2,2,5};
		System.out.println(new TeamPhoto().canPlace(a1, a2));
		System.out.println(new TeamPhoto().canPlace(a1, a3));
	}
	public boolean canPlace(int[] a1, int[] a2) {
		if (a1.length != a2.length) return false;
		Arrays.sort(a1);
		Arrays.sort(a2);
		int[] tallest;
		int[] shortest;
		if (a1[a1.length - 1] >= a2[a2.length - 1]) {
			tallest = a1;
			shortest = a2;
		} else {
			tallest = a2;
			shortest = a1;
		}
		for (int i = tallest.length - 1; i >= 0; i--) {
			if (tallest[i] < shortest[i]) return false;
		}
		return true;
	}
}
