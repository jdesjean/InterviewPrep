package org.ip.sort;

public class SelectorMidInt implements SelectorInt{

	@Override
	public int select(int[] array, int left, int right) {
		return (right - left)/2 + left;
	}

}
