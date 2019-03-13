package org.ip.primitives;

public class ParityLog implements Parity{

	@Override
	public boolean check(long word) {
		for (int i = 32; i != 0; i /= 2) {
			word ^= word >>> i;
		}
		return (word & 1) != 0;
	}

}
