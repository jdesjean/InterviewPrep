package org.ip.primitives;

import java.util.Random;

// EPI: 5.10
public class RandomSequence {
	public static void main(String[] s) {
		Random random = new Random();
		random.setSeed(0);
		RandomSequence sequence = new RandomSequence(random);
		System.out.println(sequence.sequence(1,2));
		System.out.println(sequence.sequence(1,3));
		System.out.println(sequence.sequence(1,4));
		System.out.println(sequence.sequence(2,5));
	}
	private Random random;
	public RandomSequence(Random random) {
		this.random = random;
	}
	public int log2(int value) {
		if( value == 0 )
	        return 0; // or throw exception
		return 32 - Integer.numberOfLeadingZeros( value );
	}
	public int sequence(int from, int to) {
		int size = to - from;
		int digits = log2(size);
		while (true) {
			int value = 0;
			for (int i = 0; i < digits; i++) {
				value <<= 1;
				value |= random.nextBoolean() ? 1 : 0; 
			}
			if (value <= size) {
				return from + value;
			}
		}
	}
}
