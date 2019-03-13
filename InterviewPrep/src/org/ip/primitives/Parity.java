package org.ip.primitives;

// EPI: 5.1
public interface Parity {
	public static void main(String[] s) {
		Parity[] parity = new Parity[] {new ParityFull(), new ParityCache(4), new ParityLog()};
		for (Parity checker :  parity) {
			System.out.println(checker.check(0b01010101));
			System.out.println(checker.check(0b01010001));
		}
	}
	public boolean check(long word);
}
