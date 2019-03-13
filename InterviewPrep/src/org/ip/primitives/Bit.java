package org.ip.primitives;

public class Bit {
	public static void main(String[] s) {
	}
	public static void testIsGreaterThan() {
		System.out.println(isGreaterThan(2,4));
		System.out.println(isGreaterThan(3,3));
		System.out.println(isGreaterThan(-3,3));
		System.out.println(isGreaterThan(-3,4));
		System.out.println(isGreaterThan(-3,-3));
		System.out.println(isGreaterThan(-3,-2));
		System.out.println(isGreaterThan(-2,-3));
		System.out.println(isGreaterThan(4,2));
		System.out.println(isGreaterThan(66,2));
		System.out.println(isGreaterThan(3,-3));
	}
	public static void testPrintBinary() {
		int[] numbers = new int[]{0b1010, 0b101010, 0b0, 0b111000};
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + " ->  ");
			printBinary(numbers[i]);
		}
	}
	public static void testSwap() {
		int[] numbers = new int[]{0b1010, 0b101010, 0b0, 0b111000};
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i] + " ->  " + swap(numbers[i],2,3));
		}
	}
	public static boolean isGreaterThan(int a, int b) {
		int msb = 1 << 31;
		int msba = a & msb;
		int msbb = b & msb;
		if (msba != msbb) {
			return msba == 0;
		} else if (msba != 0) {
			int c = Math.abs(b);
			b = Math.abs(a);
			a = c; 
		}
		int diff = a ^ b;
		diff &= ~(diff >> 1);
		diff &= a;
		return diff != 0;
		/*int diff = a ^ b;
		int msb = 1 << 31;
	    diff &= ~(diff >> 1) | msb;
	    //check msg: if a is positive & b negative than diff != 0. if a negative & b positive
	    diff &= (a ^ msb) & (b ^ (0xffffffff >>> 1));
	    return diff != 0;*/
	}
    
	public static String toBinary(int n) {
		StringBuilder sb = new StringBuilder();
		boolean leading = true;
		for (int mask = 1 << 31; mask != 0; mask >>>= 1) {
			boolean value = (n & mask) != 0;
			if (value) sb.append('1');
			else if (!leading) sb.append('0');
			if (value) leading = false;
		}
		return sb.toString();
	}
	public static void printBinary(int n) {
		System.out.println(toBinary(n));
	}
	public static short swap(short n, int i, int j) {
		if (getBit(n,i) == getBit(n,j)) return n;
		short mask = (short)(1 << i | 1 << j);
		return (short)(n ^ mask); 
	}
	public static int swap(int n, int i, int j) {
		if (getBit(n,i) == getBit(n,j)) return n;
		int mask = 1 << i | 1 << j;
		return n ^ mask; 
	}
	public static int flip(int n, int i) {
		return n ^ 1 << i;
	}
	public static int setBit(int n, int i) {
		return n | (1 << i); 
	}
	public static boolean getBit(int n, int i) {
		return ((n >> i) & 1) != 0;
	}
	public static int removeLastBit(int n) {
		return n & n-1;
	}
}
