package org.ip.primitives;

// EPI 2018: 4.4
public class BitWeight2 {
	public static void main(String[] s) {
		BitWeight2 iterator = new BitWeight2(64);
		Bit.printBinary(iterator.next(0b0)); // 0b0, 0 / 0b0, 0
		Bit.printBinary(iterator.next(0b11)); // 0b11, 3 / 0b101, 5
		Bit.printBinary(iterator.next(0b101)); // 0b101, 5 / 0b110, 6
		Bit.printBinary(iterator.next(0b110)); // 0b110, 6 / 0b101, 5
		Bit.printBinary(iterator.next(0b1100)); // 0b1100, 12 / 0b1010, 10
	}
	private int size;
	private BitSwapper swapper = new BitSwapper();
	public BitWeight2(int size) {
		this.size=size;
	}
	public int next(int value) {
		if (value == 0) return value;
		int j = findNextZero(value, 0);
		if (j == -1) return value;
		int k = findNextOne(value, j);
		if (k == -1) {
			return swapper.swap(value, j, j-1);
		} else {
			return swapper.swap(value, k, k-1);
		}
	}
	private int findNextZero(int value, int j) {
		for (int i = j; i < 64; i++) {
			if ((value & 1) == 0) return i; 
			value >>= 1;
		}
		return -1;
	}
	private int findNextOne(int value, int j) {
		for (int i = j; i < 64; i++) {
			if ((value & 1) == 1) return i;
			value >>= 1;
		}
		return -1;
	}
}
