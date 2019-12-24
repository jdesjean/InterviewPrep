package org.ip.string;

// EPI 2018: 6.8
public class LookAndSay2 {
	public static void main(String[] s) {
		System.out.println(new LookAndSay2().generate(5));
	}
	public int generate(int n) {
		int value = 1;
		for (int i = 1; i < n; i++) {
			value = next(value);
		}
		return value;
	}
	public int next(int value) {
		int length = (int)Math.ceil(Math.log10(value + 1));
		int div = (int)Math.pow(10, length - 1);
		int prev = -1;
		int ret = 0;
		int counter = 0;
		for (int i = length; i > 0; i--, div/=10) {
			int current = value / div;
			value %= div;
			if (prev != -1 && prev != current) {
				ret *= 10;
				ret += counter;
				ret *= 10;
				ret += prev;
				counter = 1;
			} else {
				counter++;
			}
			prev = current;
		}
		ret *= 10;
		ret += counter;
		ret *= 10;
		ret += prev;
		return ret;
	}
}
