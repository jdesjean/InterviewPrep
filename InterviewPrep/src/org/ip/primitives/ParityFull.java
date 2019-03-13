package org.ip.primitives;

public class ParityFull implements Parity {

	@Override
	public boolean check(long word) {
		boolean parity = false;
		for (; word != 0;) {
			parity ^= true;
			word &= word - 1;
		}
		return parity;
	}

}
