package org.ip.string;

// EPI: 7.13
public class RabinKarpe {
	public static void main(String[] s) {
		String one = "this is a test string";
		String two = "tes";
		RabinKarpe searcher = new RabinKarpe();
		System.out.println(searcher.find(one, two));
	}
	private final static int d = 256;
	public int find(String t, String p) {
		int q = 101;
		int shash = hash(p, p.length(), q);
		int thash = hash(t, p.length(), q);
		int power = power(p.length(), q);
		if (thash == shash && equal(t, 0, p)) return 0;
		for (int i = p.length(); i < t.length(); i++) {
			thash -= t.charAt(i - p.length()) * power;
			thash = (thash * d + t.charAt(i)) % q;
			if (thash < 0) thash = thash + q;
			if (thash == shash && equal(t, i - p.length() + 1, p)) return i - p.length() + 1;
		}
		return -1;
	}
	private boolean equal(String one, int l, String two) {
		for (int i = 0; i < two.length(); i++) {
			if (one.charAt(i + l) != two.charAt(i)) return false;
		}
		return true;
	}
	private int power(int length, int q) {
		int power = 1;
		for (int i = 0; i < length; i++) {
			if (i > 0) power = (power * d) % q;
		}
		return power;
	}
	private int hash(String s, int length, int q) {
		int hash = 0;
		for (int i = 0; i < length; i++) {
			hash = (d * hash + s.charAt(i)) % q;
		}
		return hash;
	}
}
