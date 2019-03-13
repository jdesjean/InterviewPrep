package org.ip.primitives;

public class ReverserCache implements BitReverser {
	private int size = 4;
	private int[] cache;
	private void init() {
		int length = (int)Math.pow(2, size);
		BitReverser reverser = new ReverserFull(size);
		cache = new int[length];
		for (int i = 0; i < length; i++) {
			cache[i] = reverser.reverse(i);
		}
	}
	@Override
	public int reverse(int word) {
		if (cache == null) {
			init();
		}
		int mask = (int)Math.pow(2, size) - 1;
		int iterations = 32 / size;
		int result = 0;
		for (int i = 0; i < iterations; i++) {
			result |= cache[word & mask];
			if (i < iterations - 1) {
				word >>>= size;
				result <<= size;
			}
		}
		return result;
	}

}
