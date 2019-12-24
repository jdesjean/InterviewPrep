package org.ip.primitives;
import java.util.BitSet;

// EPI 2018: 4.1
public class Parity2 {
	public static void main(String[] s) {
		//10001000, 0
		//1011, 1
		ParityChecker[] checkers = new ParityChecker[] {new ParityFull(), new ParityCache(new ParityFull())};
		for (ParityChecker checker : checkers) {
			System.out.println(checker.parity(0b1011));
			System.out.println(checker.parity(0b10001000));
			System.out.println(checker.parity(0b1011000000000000));
			System.out.println(checker.parity(0b1000100000000000));
		}
	}
	public interface ParityChecker {
		public boolean parity(int value);
	}
	public static class ParityCache implements ParityChecker {
		BitSet set = new BitSet();
		private ParityChecker parity;
		private final int MAX_VAL = 0b11111111;
		public ParityCache(ParityChecker parity) {
			this.parity = parity;
			for (int i = 0; i <= MAX_VAL; i++) {
				if (!parity.parity(i)) continue;
				set.set(i);
			}
		}
		@Override
		public boolean parity(int value) {
			boolean parity = false;
			while (value != 0) {
				parity ^= set.get(value & MAX_VAL);
				value >>= 8;
			}
			return parity;
		}
	}
	public static class ParityFull implements ParityChecker {
		public boolean parity(int v) {
			boolean parity = false;;
			while (v != 0) {
				v &= v - 1;
				parity ^= true;
			}
			return parity;
		}
	}
}
