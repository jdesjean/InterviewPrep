package org.ip.string;

// EPI 2018: 6.13
public class RabinKarpe2 {
	public static void main(String[] s) {
		System.out.println(new RabinKarpe2().find("this is a string", "string"));
		System.out.println(new RabinKarpe2().find("this is a string", "this"));
		System.out.println(new RabinKarpe2().find("this is a string", "bleh"));
	}
	public int find(String s, String t) {
		if (s.length() < t.length()) return -1;
		final int BASE = 26;
		int shash = 0;
		int thash = 0;
		int p = 1;
		for (int i = 0; i < t.length(); i++) {
			shash = shash * BASE + s.charAt(i);
			thash = thash * BASE + t.charAt(i);
			p = i > 0 ? p * BASE : 1;
		}
		if (thash == shash && compare(s, 0, t.length() - 1, t) == 0) {
			return 0;
		}
		for (int i = t.length(); i < s.length(); i++) {
			int prev = i - t.length();
			shash -= s.charAt(prev) * p;
			shash = shash * BASE + s.charAt(i);
			if (thash == shash && compare(s, prev + 1, i, t) == 0) {
				return i;
			}
		}
		return -1;
	}
	public int compare(String s, int i, int j, String t) {
		for (int k = 0;i <= j; i++, k++) {
			if (s.charAt(i) != t.charAt(k)) {
				return s.charAt(i) - t.charAt(k);
			}
		}
		return 0;
	}
}
