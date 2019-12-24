package org.ip.honor;

import java.util.Arrays;

import org.ip.array.Utils;

// EPI 2018: 24.14
public class Bonus {
	public static void main(String[] s) {
		System.out.println(new Bonus().solve(new int[] {300,400,400,400}));
		System.out.println(new Bonus().solve(new int[] {300,400,200,500}));
		System.out.println(new Bonus().solve(new int[] {300,400,300,200}));
		System.out.println(new Bonus().solve(new int[] {100,200,300,400}));
	}
	public int solve(int[] a) {
		int[] bonus = new int[a.length];
		Arrays.fill(bonus, 1);
		for (int i = 1; i < a.length; i++) {
			if (a[i] > a[i - 1]) {
				bonus[i] = Math.max(bonus[i], bonus[i - 1] + 1);
			}
		}
		for (int i = a.length - 2; i >= 0; i--) {
			if (a[i] > a[i + 1]) {
				bonus[i] = Math.max(bonus[i], bonus[i + 1] + 1);
			}
		}
		return Utils.sum(bonus, bonus.length);
	}
}
