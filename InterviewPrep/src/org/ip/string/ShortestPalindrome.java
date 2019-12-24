package org.ip.string;

public class ShortestPalindrome {
	public static void main(String[] s) {
		System.out.println(new ShortestPalindrome2().shortestPalindrome("aabba") + " == " + "abbaabba");
		System.out.println(new ShortestPalindrome2().shortestPalindrome("aacecaaa") + " == " + "aaacecaaa");
		System.out.println(new ShortestPalindrome2().shortestPalindrome("abcd") + " == " + "dcbabcd");
		System.out.println(new ShortestPalindrome2().shortestPalindrome("babbbabbaba") + " == " + "ababbabbbabbaba");
	}
	public String shortestPalindrome(String s) {
		String reversed = reverse(s);
        int n = s.length();
        int shash = 0;
        int rhash = 0;
        int power = 1;
        int p = 13;
        for (int i = 0; i < n; i++) {
            shash = shash * p + s.charAt(i) - 'a';
            rhash = rhash * p + reversed.charAt(i) - 'a';
            power = power * p;
        }
        for (int i = 0; i < n; i++) {
            if (shash == rhash && s.substring(0, n - i).compareTo(reversed.substring(i)) == 0) {
                return reversed.substring(0, i) + s;
            }
            power /= p;
            shash = (shash - (s.charAt(n - i - 1) - 'a')) / p;
            rhash = rhash - ((reversed.charAt(i) - 'a') * power);
        }
        return "";
    }
	public String reverse(String s) {
        char[] reverse = new char[s.length()];
        int length = s.length();
        for (int i = 0, j = length - 1; i < length; i++, j--) {
            reverse[i] = s.charAt(j);
        }
        return new String(reverse);
    }
}
