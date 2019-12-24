package org.ip.array;

import java.util.Random;

// EPI 2018: 5.12
public class Permutator5 {
	public static void main(String[] s) {
		int[] array = new int[] {1,2,3,4};
		int k = 2;
		new Permutator5(new Random(0)).next(array, k);
		Utils.println(array, k);
	}
	private Random random;
	public Permutator5(Random random) {
		this.random=random;
	}
	public void next(int[] array, int k) {
		for (int i = 0; i < k; i++) {
			int next = i + random.nextInt(array.length - i);
			Utils.swap(array, i, next);
		}
	}
}
