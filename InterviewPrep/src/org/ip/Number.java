package org.ip;

public class Number {
	public static void main(String[] s) {
		System.out.println(nextPalindrome(23545));
		System.out.println(nextPalindrome(99));
		System.out.println(nextPalindrome(6789876));
		System.out.println(nextPalindrome(8998));
	}
	public static int countDigit(int n) {
		int count = 0;
		while (n > 0) {
			n/=10;
			count++;
		}
		return count;
	}
	public static int reverse(int n) {
		int next = 0;
		while (n > 0) {
			next = next*10+n%10;
			n/=10;
		}
		return next;
	}
	public static int nextPalindrome(int n) {
		int count = countDigit(n);
		for (int i = 0; i < count/2; i++) {
			n /= 10;
		}
		int next = ++n;
		int nextCount = countDigit(n)+count/2;
		if (nextCount % 2 != 0) n/=10;
		for (int i = 0; i < count/2; i++) {
			next=next*10+ n % 10;
			n/=10;
		}
		return next;
	}
}
