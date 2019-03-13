package org.ip.primitives;

import java.util.Arrays;
import java.util.Random;

// EPI: 6.15
public class DistributionSequence {
	public static void main(String[] s) {
		DistributionSequence sequence = new DistributionSequence(new Random(0), new int[] {9,6,2,1}, new int[] {3,5,7,11}, 18);
		for (int i = 0; i < 4; i++) {
			System.out.println(sequence.sequence());
		}
	}
	private int[] cumulative;
	private Random random;
	private int k;
	private int[] values;
	public DistributionSequence(Random random, int[] distribution, int[] values, int k) {
		cumulative = new int[distribution.length];
		this.random = random;
		this.values = values;
		this.k = k;
		for (int i = 1; i < distribution.length; i++) {
			cumulative[i] = this.cumulative[i - 1] + distribution[i - 1]; 
		}
		
	}
	public int sequence() {
		int next = this.random.nextInt(k);
		int index = Arrays.binarySearch(cumulative, next);
		if (index < 0) {
			index = -1 * index - 2;
		}
		return values[index];
	}
}
