package org.ip.primitives;

//EPI 5.9
public class DigitPalindrome {
	public static void main(String[] s) {
		DigitPalindrome palindrome = new DigitPalindrome();
		System.out.println(palindrome.check(123321));
		System.out.println(palindrome.check(1231321));
		System.out.println(palindrome.check(1231322));
	}
	DigitReverser reverser = new DigitReverser();
	public boolean check(int value) {
		int digits = (int)Math.log10(value) + 1;
		for (int i = 0, max = (int)Math.pow(10, digits - 1); i < digits / 2; i++, max /= 100, value /= 10) {
			int high = value / max % 10;
			int low = value % 10;
			if (high != low) return false;
		}
		return true;
	}
}
