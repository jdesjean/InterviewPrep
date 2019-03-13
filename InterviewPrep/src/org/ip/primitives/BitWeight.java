package org.ip.primitives;

// EPI 5.4
public class BitWeight {
	public static void main(String[] s) {
		BitWeight closest = new BitWeight();
		Bit.printBinary(closest.next(0b1000));
		Bit.printBinary(closest.next(0b10011));
	}
	public int next(int word) {
		int i = firstZero(word, 0);
		int j = firstOne(word, i);
		if (i == -1 || j == -1) return word;
		int lastZero = j - 1;
		Swapper swapper = new Swapper();
		return swapper.swap(word, j, lastZero);
	}
	private int firstZero(int word, int start) {
		word >>>= start;
		for (int i = start; i < 32; i++) {
			if ((word & 1) == 0) {
				return i;
			}
			word >>>= 1;
		}
		return -1;
	}
	private int firstOne(int word, int start) {
		word >>>= start;
		for (int i = start; i < 32; i++) {
			if ((word & 1) != 0) {
				return i;
			}
			word >>>= 1;
		}
		return -1;
	}
}
