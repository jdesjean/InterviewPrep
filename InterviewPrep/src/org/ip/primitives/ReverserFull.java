package org.ip.primitives;

public class ReverserFull implements BitReverser {
	private int size;
	public ReverserFull(int size) {
		this.size = size - 1;
	}
	public int reverse(int word) {
		Swapper swapper = new Swapper();
		for (int i = size; i > size / 2; i--) {
			word = swapper.swap(word, i, size - i);
		}
		return word;
	}
}
