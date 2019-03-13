package org.ip.primitives;

// EPI: 6.8
public class Prime {

	public static void main(String[] s) {
		Prime prime = new Prime();
		prime.generate(18, i -> System.out.println(i));
	}
	public interface LongReceiver {
		public void receive(long value);
	}
	private void generate(int max, LongReceiver receiver) {
		int size = (max - 3) / 2 + 1;
		BitSet prime = new BitSet(size);
		prime.setAll();
		for (long n = 0; n < size; n++) {
			if (!prime.get(n)) continue;
			long p = n*2 + 3;
			receiver.receive(p);
			for (long i = 2*n*n+6*n+3; i < max; i+=p ) {
				prime.clear(i);
			}
		}
	}
}
