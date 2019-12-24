package org.ip.string;

public class ShortestPalindrome2 {
	public static void main(String[] s) {
		System.out.println(new ShortestPalindrome2().shortestPalindrome("aabba") + " == " + "abbaabba");
		System.out.println(new ShortestPalindrome2().shortestPalindrome("aacecaaa") + " == " + "aaacecaaa");
		System.out.println(new ShortestPalindrome2().shortestPalindrome("abcd") + " == " + "dcbabcd");
		System.out.println(new ShortestPalindrome2().shortestPalindrome("babbbabbaba") + " == " + "ababbabbbab");
	}
	public String shortestPalindrome(String s) {
		String reversed = reverse(s);
        int n = s.length();
        Hash shash = new Hash(s);
        Hash rhash = new Hash(reversed);
        for (int i = 0; i < n; i++) {
        	int r = n - i - 1;
            if (shash.get(0,  r) == rhash.get(i, n - 1) && s.substring(0, n - i).compareTo(reversed.substring(i)) == 0) {
                return reversed.substring(0, i) + s;
            }
        }
        return "";
    }
	public static class Hash {
		int mod = 8193;
		private int[] presum;
		private int[] pow;
		public Hash(String s) {
			int n = s.length();
			int power = 1;
			int shash = 0;
			int p = 13;
			presum = new int[n + 1];
			pow = new int[n + 1];
			pow[0] = 1;
			for (int i = 0; i < n; i++) {
	            shash = shash * p + s.charAt(i) - 'a';
	            shash = shash % mod;
	            presum[i + 1] = shash;
	            power = power * p;
	            power = power % mod;
	            pow[i + 1] = power;
	        }
		}
		public int get(int l, int r) {
	        int h = presum[r] - (presum[l] * pow[r-l]) % mod;
	        if (h < 0) h += mod;
	        if (h >= mod) h -= mod;
	        return h;
	    }
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
