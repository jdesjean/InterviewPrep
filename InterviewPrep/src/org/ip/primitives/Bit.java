package org.ip.primitives;

import java.util.BitSet;

public class Bit {
	static BitSet CACHE_PARITY;
	static short[] CACHE_REVERSE;
	public static void main(String[] s) {
		testPow();
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
	public static void testPow() {
		System.out.println(pow(2,4));
		System.out.println(pow(2,3));
		System.out.println(pow(3,3));
	}
	public static void testAdd() {
		System.out.println(add(3,4));
		System.out.println(add(10,12));
		System.out.println(add(10,13));
		System.out.println(add(3,-4));
		System.out.println(add(-3,-4));
	}
	public static void testDivide() {
		System.out.println(divide(8,4));
		System.out.println(divide(8,-2));
		System.out.println(divide(9,3));
		System.out.println(divide(8,3));
		System.out.println(divide(Integer.MAX_VALUE,4));
		System.out.println(divide2(Integer.MAX_VALUE,Integer.MAX_VALUE));
	}
	public static void testDivide2() {
		System.out.println(divide2(8,4));
		System.out.println(divide2(8,-2));
		System.out.println(divide2(9,3));
		System.out.println(divide2(8,3));
		System.out.println(divide2(Integer.MAX_VALUE,4));
		System.out.println(divide2(Integer.MAX_VALUE,Integer.MAX_VALUE));
	}
	public static void testMultiply() {
		System.out.println(multiply(3,4));
		System.out.println(multiply(3,-4));
		System.out.println(multiply(-3,-4));
	}
	public static void testClosest() {
		int[] numbers = new int[]{0b1010, 0b101010, 0b0, 0b111000, 0b111};
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + " ->  ");
			int closest = closest(numbers[i]);
			System.out.print(closest + " -> " );
			printBinary(closest);
		}
	}
	public static void testPrintBinary() {
		int[] numbers = new int[]{0b1010, 0b101010, 0b0, 0b111000};
		for (int i = 0; i < numbers.length; i++) {
			System.out.print(numbers[i] + " ->  ");
			printBinary(numbers[i]);
		}
	}
	public static void testReverse() {
		int[] numbers = new int[]{0b1010, 0b101010, 0b0, 0b111000};
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i] + " ->  " + reverse(numbers[i]));
		}
	}
	public static void testSwap() {
		int[] numbers = new int[]{0b1010, 0b101010, 0b0, 0b111000};
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i] + " ->  " + swap(numbers[i],2,3));
		}
	}
	public static void testParity() {
		int[] numbers = new int[]{0b1010, 0b101010, 0b0, 0b111000};
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i] + " ->  " + paritySingle(numbers[i]) + " -> " + parity(numbers[i]));
		}
	}
	public static int divide2(int a, int b) {
		boolean isNegative = isNegativeMultiply(a,b);
		a = abs(a);
		b = abs(b);
		if (b == 1) return isNegative ? -a : a;
		int c = 0;
		int ap = 1;
		int bp = b;
		while (bp <= a && bp > 0) { //b < 0 has overflowed
			bp <<= 1; 
			ap <<= 1; 
		} 
		while (a >= b) {
			while (bp > a || bp < 0) {
				ap >>>= 1;
				bp >>>= 1;
			}
			c |= ap;
			a = substract(a,bp);
		}
		return isNegative ? -c : c;
	}
	public static int divide(int a, int b) {
		boolean isNegative = isNegativeMultiply(a,b);
		a = abs(a);
		b = abs(b);
		if (b == 1) return isNegative ? -a : a;
		int c = 0;
		int p = 30;
		int ap = 1 << p;
		long bp = (long)b << p;
		while (a >= b) {
			while (bp > a) {
				ap >>>= 1;
				bp >>>= 1;
			}
			c |= ap;
			a = substract(a,(int)bp);
		}
		return isNegative ? negate(c) : c;
	}
	public static boolean isGreaterThan(int a, int b) {
		int msb = 1 << 31;
		int msba = a & msb;
		int msbb = b & msb;
		if (msba != msbb) {
			return msba == 0;
		} else if (msba != 0) {
			int c = abs(b);
			b = abs(a);
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
    
	public static int negate(int a) {
		return add(~a,1);
	}
	public static int substract(int a, int b) {
		return add(a, negate(b));
	}
	public static double pow(int a, int b) {
		double c = 1;
		if (b < 0) {
			b = -b;
			c = 1.0 / (double)a;
		}
		while (b != 0) {
			if ((b & 1) != 0) {
				c*=a;
			}
			a*=a;
			b>>>=1;
		}
		return c;
	}
	public static int add(int a, int b) {
		while (b != 0) {
			int c = a ^ b;
			b = (a & b) << 1;
			a = c;
		}
		return a;
	}
	public static boolean isNegative(int a) {
		int msb = 1 << 31;
		return (a & msb) != 0;
	}
	public static boolean isNegativeMultiply(int a, int b) {
		int msb = 1 << 31;
		int sign = (a & msb) ^ (b & msb);
		return sign != 0;
	}
	public static int abs(int a) {
		if (isNegative(a)) {
			return add(~a,1);
		} else {
			return a;
		}
	}
	public static int multiply(int a, int b) {
		int c = 0;
		while (b != 0) {
			if ((b & 1) != 0) {
				c=add(c,a);
			}
			a<<=1;
			b>>>=1;
		}
		return c;
	}
	public static int closest(int n) {
		int first = lastZeroBeforeOne(n);
		if (first >= 0) {
			 return swap(n,first,first+1);
		} else {
			int last = lastOneBeforeZero(n);
			if (last == -1) return 0;
			return swap(n,last,last+1);
		}
	}
	public static int lastOneBeforeZero(int n) {
		int one = -1;
		for (int i = 0; n > 0; n >>=1, i++) {
			if ((n & 1) != 0) one = i;
			else if (one != -1) return one;
		}
		return one;
	}
	public static int lastZeroBeforeOne(int n) {
		int zero = -1;
		for (int i = 0; n > 0; n >>= 1, i++) {
			if ((n & 1) == 0) zero = i;
			else if (zero != -1) return zero;
		}
		return -1;
	}
	public static void printBinary(int n) {
		boolean leading = true;
		for (int mask = 1 << 31; mask != 0; mask >>>= 1) {
			boolean value = (n & mask) != 0;
			if (value) System.out.print('1');
			else if (!leading) System.out.print('0');
			if (value) leading = false;
		}
		System.out.println("");
	}
	public static int reverse(int n) {
		return reverseCache((short)n) << 16
				| reverseCache((short)(n >> 16));
	}
	public static short reverseCache(short n) {
		int mask = 0xFFFF;
		if (CACHE_REVERSE == null) {
			CACHE_REVERSE = new short[mask+1];
			for (int i = 0; i <= mask; i++) {
				CACHE_REVERSE[i] = reverseSingle((short)i);
			}
		}
		return CACHE_REVERSE[n];
	}
	public static short reverseSingle(short n) {
		for (int i = 0,j = 15; i < j; i++,j--) {
			n = swap(n,i,j);
		}
		return n;
	}
	public static int reverseSingle(int n) {
		for (int i = 0,j = 31; i < j; i++,j--) {
			n = swap(n,i,j);
		}
		return n;
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
	public static boolean parity(int n) {
		return parityAssociative(n);
	}
	public static boolean parityAssociative(int n) {
		n ^= n >>> 16;
		return parityCache((short)n);
	}
	public static boolean parityCache(short n) {
		int mask = 0xFFFF;
		if (CACHE_PARITY == null) {
			CACHE_PARITY = new BitSet(mask+1);
			for (int i = 0; i <= mask; i++) {
				if (paritySingle(i)) CACHE_PARITY.set(i);
			}
		}
		return CACHE_PARITY.get(n & mask); 
	}
	public static boolean paritySingle(int n) {
		int parity = 0;
		while (n > 0) {
			n = removeLastBit(n);
			parity^=1;
		}
		return parity != 0;
	}
}
