package org.ip.primitives;

public class ParityCache implements Parity{
	private int size;
	private boolean[] table;
	public ParityCache(int size) {
		this.size = size;
	}
	private void init() {
		int length = (int)Math.pow(2, size);
		table = new boolean[length];
		Parity parity = new ParityFull();
		for (int i = 0; i < length; i++) {
			table[i] = parity.check(i);
		}
	}
	@Override
	public boolean check(long word) {
		if (table == null) {
			init();
		}
		boolean parity = false;
		int mask = (int)Math.pow(2, size) - 1;
		for (; word != 0;) {
			parity ^= table[(int)(word & mask)];
			word >>>= size;
		}
		return parity;
	}

}
