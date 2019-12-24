package org.ip.array;

import java.util.Random;

// EPI 2018: 5.13
public class Permutator4 {
	public static void main(String[] s) {
		int[] array = new int[] {1,2,3,4};
		int n = 4; // 4 / 8
		Random random = new Random(0);
		Permutator4 permutator = new Permutator4(random);
		for (int i = 5; i <= 10; i++) {
			permutator.next(array, ++n, i);
			Utils.println(array);
		}
	}
	private Random random;
	public Permutator4(Random random) {
		this.random=random;
	}
	public void next(int[] array, int n, int v) {
		int r = random.nextInt(n);
		if (r < array.length) {
			array[r] = v;
		}
	}
}
