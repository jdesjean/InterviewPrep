package org.ip.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// EPI 2018: 6.9
public class Roman2 {
	public final static Map<Character, Integer> MAP_VALUE = new HashMap<Character,Integer>();
	public final static Map<Character, Integer> MAP_POS = new HashMap<Character,Integer>();
	public final static Set<Character> EXCEPTIONS = new HashSet<Character>();
	static {
		char[] characters = new char[] {'I','V','X','L','C','D','M'};
		int[] values = new int[] {1,5,10,50,100,500,1000};
		for (int i = 0; i < values.length; i++) {
			MAP_VALUE.put(characters[i], values[i]);
			MAP_POS.put(characters[i], i);
			if (i % 2 == 0 && i < values.length - 1) {
				EXCEPTIONS.add(characters[i]);
			}
		}
	}
	public static void main(String[] s) {
		System.out.println(new Roman2().convert("XXXXXIIIIIIIII"));
		System.out.println(new Roman2().convert("LVIIII"));
		System.out.println(new Roman2().convert("LIX"));
		System.out.println(new Roman2().convert("CM"));
		System.out.println(new Roman2().convert("CDM"));
		System.out.println(new Roman2().convert("XM"));
		System.out.println(new Roman2().convert("VX"));
	}
	public int convert(String s) {
		int value = 0;
		char prev = 'M';
		char prevprev = 'M';
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int current = MAP_VALUE.get(c);
			if (current <= MAP_VALUE.get(prev)) {
				value += current;
			} else if (EXCEPTIONS.contains(prev) && EXCEPTIONS.contains(prevprev)) {
				return -1;
			} else if (EXCEPTIONS.contains(prev) && (MAP_POS.get(c) == MAP_POS.get(prev) + 1 || MAP_POS.get(c) == MAP_POS.get(prev) + 2)) {
				value += (current - MAP_VALUE.get(prev) * 2);
			} else {
				return -1;
			}
			prev = c;
		}
		return value;
	}
}
