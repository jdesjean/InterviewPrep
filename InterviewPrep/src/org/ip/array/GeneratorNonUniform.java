package org.ip.array;

import java.util.Arrays;
import java.util.Random;

// EPI 2018: 5.16
public class GeneratorNonUniform {
	public static void main(String[] s) {
		int[] distribution = new int[] {9,6,2,1};
		GeneratorNonUniform generator = new GeneratorNonUniform(new Random(0), distribution);
		for (int i = 0; i < 10; i++) {
			System.out.println(generator.next());
		}
	}
	private Random random;
	private int sum;
	private int[] cumulative;
	public GeneratorNonUniform(Random random, int[] distribution) {
		this.random = random;
		this.sum = Utils.sum(distribution, distribution.length);
		int cum = 0;
		this.cumulative = new int[distribution.length];
		for (int i = 0; i < distribution.length; i++) {
			cum += distribution[i];
			cumulative[i] = cum;
		}
	}
	public int next() {
		int value = (int)Math.ceil(random.nextDouble() * sum);
		int index = Arrays.binarySearch(cumulative, value);
		if (index < 0) {
			index = -index - 1;
		}
		return index;
	}
}
