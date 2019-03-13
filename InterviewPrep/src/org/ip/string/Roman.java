package org.ip.string;

import java.util.HashMap;
import java.util.Map;

// EPI: 7.9
public class Roman {
	public static void main(String[] s) {
		Roman roman = new Roman();
		int value = roman.convert("XXXXXIIIIIIIII");
		System.out.println(value);
		value = roman.convert("LIX");
		System.out.println(value);
		value = roman.convert("XLI");
		System.out.println(value);
		value = roman.convert("CDIV");
		System.out.println(value);
	}
	private final static Map<Character, Integer> VALUES = new HashMap<Character, Integer>();
	{
		VALUES.put('I', 1);
		VALUES.put('V', 5);
		VALUES.put('X', 10);
		VALUES.put('L', 50);
		VALUES.put('C', 100);
		VALUES.put('D', 500);
		VALUES.put('M', 1000);
	}
	public int convert(String ch) {
		int ret = 0;
		for (int i = 0; i < ch.length(); i++) {
			int current = VALUES.get(ch.charAt(i));
			int next = i < ch.length() - 1 ? VALUES.get(ch.charAt(i + 1)) : 0;
			if (current < next) {
				ret -= current;
			} else {
				ret += current;
			}
		}
		return ret;
	}
}
