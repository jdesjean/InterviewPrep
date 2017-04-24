package org.ip.primitives;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Number {
	public static void main(String[] s) {
		testConvertFromRoman();
	}
	public static <T>  List<T> list(T[] t) {
		return new ArrayList<T>(Arrays.asList(t));
	}
	public static void testConvertFromRoman() {
		System.out.println(convertFromRoman("XXXXXIIIIIIIII"));
		System.out.println(convertFromRoman("LVIIII"));
		System.out.println(convertFromRoman("LIX"));
	}
	public static void testBaseConvert() {
		System.out.println(baseConvert("1F1",16,2));
	}
	public static void testToString() {
		System.out.println(toString(123));
		System.out.println(toString(-123));
	}
	public static void testFromString() {
		System.out.println(fromString("123"));
		System.out.println(fromString("-123"));
	}
	public static void testMultiply() {
		System.out.println(multiply(list(new Integer[]{2}), list(new Integer[]{5})));
		System.out.println(multiply(list(new Integer[]{2}), list(new Integer[]{-5})));
		System.out.println(multiply(list(new Integer[]{-2}), list(new Integer[]{5})));
		System.out.println(multiply(list(new Integer[]{-2}), list(new Integer[]{-5})));
		System.out.println(multiply(list(new Integer[]{1,2}), list(new Integer[]{1,2})));
		System.out.println(multiply(list(new Integer[]{9,9}), list(new Integer[]{9,9})));
	}
	public static void testPlusOne() {
		System.out.println(plusOne(list(new Integer[]{1})));
		System.out.println(plusOne(list(new Integer[]{1,2})));
		System.out.println(plusOne(list(new Integer[]{9,9})));
	}
	public static void testNextPalindrome() {
		int[] numbers = new int[]{23545, 99, 6789876, 8998};
		for (int i = 0; i < numbers.length; i++) {
			System.out.println(numbers[i] + " ->  " + nextPalindrome(numbers[i]));
		}
	}
	public static List<Integer> multiply(List<Integer> a, List<Integer> b) {
		List<Integer> c = new ArrayList<Integer>();
		int firstA = a.size() > 0 ? a.get(0) : 0;
		int firstB = a.size() > 0 ?  b.get(0) : 0;
		int msb = 1 << 31;
		int sign = (firstA & msb) ^ (firstB & msb);
		if (a.size() > 0) a.set(0, Math.abs(a.get(0)));
		if (b.size() > 0) b.set(0, Math.abs(b.get(0)));
		
		for (int i = b.size()-1, carry = 0, j = 0; i >= 0; i--, j++) {
			int current = b.get(i);
			for (int k = a.size()-1, l = 0; k >= 0; k--, l++) {
				int index = c.size()-1-(j + l);
				int previous = index < 0 ? 0 : c.get(index);
				int next = a.get(k)*current+carry+previous;
				int digit = next % 10;
				carry = next / 10;
				if (index < 0) c.add(0, digit);
				else c.set(index, digit);
			}
			if (carry > 0) {
				int index = c.size()-1-(j + a.size());
				if (index < 0) c.add(0, carry);
				else c.set(index, carry);
				carry = 0;
			}
		}
		if (c.size() > 0 && sign != 0) {
			c.set(0, -c.get(0));
		}
		return c;
	}
	public static List<Integer> plusOne(List<Integer> list) {
		int carry = 1;
		for (int i = list.size()-1; carry != 0 && i >= 0; i--) {
			int next = list.get(i) + carry;
			int digit = next % 10;
			carry = next / 10;
			list.set(i, digit);
		}
		if (carry != 0) {
			list.add(0, carry);
		}
		return list;
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
	public static int fromString(String s) {
		int value = 0;
		int negative = 1;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '-') negative = -1;
			else {
				int digit = c - '0';
				value = value*10+digit;
			}
		}
		return negative*value;
	}
	public static String toString(int n) {
		StringBuilder sb = new StringBuilder();
		boolean isNegative = n < 0;
		n = Math.abs(n);
		do {
			char c = (char)(n % 10 + '0');
			sb.append(c);
			n/=10;
		} while (n > 0);
		if (isNegative) sb.append('-');
		return sb.reverse().toString();
	}
	
	public static int convertFromBase(String n, int base) {
		int value = 0;
		int negative = 1;
		for (int i = 0; i < n.length(); i++) {
			char c = n.charAt(i);
			if (c == '-') {
				negative = -1;
				continue;
			}
			int digit = c - '0';
			boolean isNumeric = digit >= 0 && digit <= 9;
			if (!isNumeric) {
				digit = 10 + c - 'A';
			}
			value = value * base + digit; 
		}
		return value*negative;
	}
	//78x20
	public static String convertToBase(int n, int base) {
		StringBuilder sb = new StringBuilder();
		boolean isNegative = n < 0;
		n = Math.abs(n);
		do {
			sb.append(n % base);
			n/=base;
		} while (n > 0);
		if (isNegative) sb.append('-');
		return sb.reverse().toString();
	}
	public static String baseConvert(String n1, int base1, int base2) {
		int n = convertFromBase(n1,base1);
		return convertToBase(n,base2);
	}
	public static int fromCharacter(char[] buffer, int left, int right) {
		int value = 0;
		for (int i = left; i <= right; i++) {
			value = value * 10 + Character.digit(buffer[i], 10);
		}
		return value;
	}
	public static int convertFromRoman(String n) {
		Map<String,Integer> values = new HashMap<String,Integer>();
		values.put("I", 1);
		values.put("V", 5);
		values.put("X", 10);
		values.put("L", 50);
		values.put("C", 100);
		values.put("D", 500);
		values.put("M", 1000);
		int value = 0;
		for (int i = n.length()-1, prev = 0; i >= 0; i--) {
			char c = n.charAt(i);
			int digit = values.get(Character.toString(c));
			if (digit < prev) {
				digit = prev - digit;
			} else {
				value += prev;
			}
			if (i == 0) {
				value += digit;
			}
			prev = digit;
		}
		return value;
	}
}
