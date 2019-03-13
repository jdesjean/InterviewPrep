package org.ip.primitives;

// EPI 5.3
public interface BitReverser {
	public static void main(String[] s) {
		BitReverser[] reversers = new BitReverser[] {new ReverserFull(32), new ReverserCache()};
		for (BitReverser reverser: reversers) {
			Bit.printBinary(reverser.reverse(0b10100011));
		}
	}
	public int reverse(int word);
}
