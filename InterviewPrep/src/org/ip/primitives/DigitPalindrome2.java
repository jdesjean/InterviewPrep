package org.ip.primitives;

//EPI 2018: 4.9
public class DigitPalindrome2 {
	public static void main(String[] s) {
		DigitPalindrome2 palindrome = new DigitPalindrome2();
		System.out.println(palindrome.check(123321));
		System.out.println(palindrome.check(1231321));
		System.out.println(palindrome.check(1231322));
		System.out.println(palindrome.check(10));
	}
	public boolean check(int value) {
		if (value < 0) {
			return false;
		}
		double log = Math.log10(value);
		int length = (int)Math.ceil(log);
		int lengthMod = (int)Math.pow(10, Math.floor(log));
		for (int l = length, r = 0; l > r; l--, r++) {
			int ll = value / lengthMod;
			int rr = value % 10;
			if (ll != rr) return false;
			value %= lengthMod;
			value /= 10;
			lengthMod /= 100;
		}
		return true;
	}
}
