package org.ip.leetcode;

// letcode 233
public class CountOneDigit {
	public static void main(String[] s) {
		CountOneDigit CountOneDigit = new CountOneDigit();
		System.out.println(CountOneDigit.countDigitOne(100));
	}
	public int countDigitOne(int n) {
        int cnt = 0;
        for (long m = 1; m <= n; m *= 10) {
          long a = n / m, b = n % m;
          cnt += (a + 8) / 10 * m + (a % 10 == 1 ? b + 1 : 0);
        }
        return cnt;
    }
}
