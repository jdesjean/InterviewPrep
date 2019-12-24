package org.ip.primitives;

// EPI: EPI 2018: 4.2
public class BitSwapper {
	public static void main(String[] s) {
		Bit.printBinary(new BitSwapper().swap(0b0101, 1, 2));
		Bit.printBinary(new BitSwapper().swap(0b0101, 2, 3));
		Bit.printBinary(new BitSwapper().swap(0b0101, 1, 3));
		Bit.printBinary(new BitSwapper().swap(0b0101, 0, 2));
	}
	public int swap(int value, int i, int j) {
		int vi = value >> i & 1;
		int vj = value >> j & 1;
		int xor = vi ^ vj;
		value ^= xor << j;
		value ^= xor << i;
		return value;
	}
}
