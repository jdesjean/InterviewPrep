package org.ip.primitives;

// EPI 5.2
public class Swapper {
	public static void main(String[] s) {
		Swapper swapper = new Swapper();
		Bit.printBinary(swapper.swap(0b01101, 1, 2));
		Bit.printBinary(swapper.swap(0b01101, 2, 3));
	}
	public int swap(int word, int i, int j) {
		boolean value_i = (word >>> i & 1) != 0;
		boolean value_j = (word >>> j & 1) != 0;
		if (value_i == value_j) return word;
		int mask = 1 << i | 1 << j;
		return word ^ mask;
	}
}
