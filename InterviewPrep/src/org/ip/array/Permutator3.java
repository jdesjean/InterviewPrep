package org.ip.array;

import java.util.Random;

// EPI 2018: 5.14
public class Permutator3 {
	public static void main(String[] s) {
		int[] array = new int[] {1,2,3,4};
		new Permutator3(new Random(0)).next(array);
		Utils.println(array);
	}
	private Random random;
	public Permutator3(Random random) {
		this.random=random;
	}
	public void next(int[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			int next = i + random.nextInt(array.length - i);
			Utils.swap(array, i, next);
		}
	}
}
