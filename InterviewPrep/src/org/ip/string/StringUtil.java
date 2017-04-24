package org.ip.string;

import java.util.Arrays;

import org.ip.array.ArrayUtils;

public class StringUtil {
	public static void main(String[] s) {
		testRunLengthEncoding();
	}
	public static void testRunLengthEncoding() {
		String encoded = runLengthEncoding("aaaabccaa");
		System.out.println(encoded);
		String decoded = runLengthDecoding(encoded);
		System.out.println(decoded);
	}
	public static void testReverseSentence() {
		System.out.println(reverseSentence("Alice likes Bob"));
	}
	public static String reverseSentence(String s) {
		char[] array = s.toCharArray();
		ArrayUtils.reverse(array);
		for (int i = 0, right = array.length-1; i <= right; i++) {
			int index = ArrayUtils.indexOf(array, i, right, ' ');
			if (index == i) continue;
			else if (index < 0) index = array.length;
			ArrayUtils.reverse(array,i,index-1);
			i = index;
		}
		return String.copyValueOf(array);
	}
	public static int toChar(char[] buffer, int n, int left) {
		int begin = left;
		do {
			int digit = n % 10;
			n/=10;
			buffer[left++] = Character.forDigit(digit, 10);
		} while (n > 0);
		ArrayUtils.reverse(buffer,begin,left-1);
		return left;
	}
	public static String runLengthEncoding(String s) {
		if (s.length() == 0) return s;
		StringBuilder sb = new StringBuilder();
		char prev = s.charAt(0);
		for (int i = 1, j = 0, current = 1; i < s.length(); i++) {
			if (s.charAt(i) == prev) {
				current++;
			}
			if (i == s.length()-1 || s.charAt(i) != prev) {
				sb.append(current);
				sb.append(prev);
				prev = s.charAt(i);
				current = 1;
			}
		}
		return sb.toString();
	}
	public static int nextNumber(String s, int left) {
		int count = 0;
		for (int i = left; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!Character.isDigit(c)) break;
			count++;
		}
		return count;
	}
	public static int toNumber(String s, int left, int right) {
		int value = 0;
		for (int i = left; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!Character.isDigit(c)) break;
			value = value * 10 + Character.digit(c, 10);
		}
		return value;
	}
	public static String runLengthDecoding(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length();) {
			int j = nextNumber(s,i);
			if (j == 0) {
				sb.append(s.charAt(i));
				i++;
			} else {
				int value = toNumber(s,i,i+j);
				while (value > 0) {
					sb.append(s.charAt(i+j));
					value--;
				}
				i+=(j+1);
			}
		}
		return sb.toString();
	}
}
