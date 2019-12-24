package org.ip.primitives;

// EPI 2018: 4.3
public class BitReverser2 implements BitReverser{
	public static void main(String[] s) {
		Bit.printBinary(new BitReverser2().reverse(0b1110000000000001));
	}
	private BitReverserCache reverser = new BitReverserCache(new BitReverserFull(new BitSwapper(), 8), 8);
	public interface BitReverser {
		public int reverse(int i);
	}
	public static class BitReverserCache implements BitReverser {
		private int maxValue;
		private short[] table;
		public static final int MAX_BIT = 64;
		private int size;
		public BitReverserCache(BitReverser reverser, int size) {
			this.size = size;
			this.maxValue = (int)Math.pow(2, size) - 1;
			table = new short[maxValue + 1];
			for (int i = 0; i <= maxValue; i++) {
				table[i] = (short)reverser.reverse(i);
			}
		}

		@Override
		public int reverse(int value) {
			int result = 0;
			for (int i = 0, sl = MAX_BIT - size; i < MAX_BIT; i+= size, sl-= size) {
				result |= table[value >> sl & maxValue] << i;
			}
			return result;
		}
		
	}
	public static class BitReverserFull implements BitReverser {
		private BitSwapper swapper;
		private int size;
		public BitReverserFull(BitSwapper swapper, int size) {
			this.size = size;
			this.swapper = swapper;
		}
		@Override
		public int reverse(int value) {
			for (int i = 0, j = size - 1; i < j; i++, j--) {
				value = swapper.swap(value, i, j);
			}
			return value;
		}
		
	}
	public int reverse(int i) {
		return reverser.reverse(i);
	}
}
