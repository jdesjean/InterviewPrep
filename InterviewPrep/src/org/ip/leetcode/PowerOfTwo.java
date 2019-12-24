package org.ip.leetcode;

//leetcode 231
public class PowerOfTwo {
	public static void main(String[] s) {
		PowerOfTwo PowerOfTwo = new PowerOfTwo();
		System.out.println(PowerOfTwo.isPowerOfTwo(1));
		System.out.println(PowerOfTwo.isPowerOfTwo(2));
		System.out.println(PowerOfTwo.isPowerOfTwo(4));
		System.out.println(PowerOfTwo.isPowerOfTwo(8));
		System.out.println(PowerOfTwo.isPowerOfTwo(6));
	}
	public boolean isPowerOfTwo(int n) {
		return countOnes(n) == 1;
    }
	private int countOnes(int n) {
		int c = 0;
		while (n > 0) {
			n &= n & (n - 1);
			c++;
		}
		return c;
	}
}
