package org.ip.primitives;

import java.util.Random;

//EPI 2018: 4.10
public class RandomSequence2 {
	public static void main(String[] s) {
		Random random = new Random();
		random.setSeed(0);
		RandomSequence2 sequence = new RandomSequence2(random);
		System.out.println(sequence.sequence(1,2));
		System.out.println(sequence.sequence(1,3));
		System.out.println(sequence.sequence(1,4));
		System.out.println(sequence.sequence(2,5));
	}
	private Random random;
	public RandomSequence2(Random random) {
		this.random=random;
	}
	public int log2(int value) {
		if( value == 0 )
	        return 0; // or throw exception
		return 32 - Integer.numberOfLeadingZeros( value );
	}
	public int sequence(int from, int to) {
		int diff = to - from;
		int log2 = log2(diff);
		while (true) {
			int result = 0;
			for (int i = 0; i < log2; i++) {
				result <<= 1;
				result |= random.nextBoolean() ? 1 : 0;
			}
			int target = result + from;
			if (target <= to) {
				return target;
			}
		}
	}
}
