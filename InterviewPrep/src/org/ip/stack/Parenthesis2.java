package org.ip.stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// EPI 2018: 8.3
public class Parenthesis2 {
	public static void main(String[] s) {
		Parenthesis2 parenthesis = new Parenthesis2();
		System.out.println(parenthesis.isWellFormed("[()[]{()()}]"));
		System.out.println(parenthesis.isWellFormed("([]){()}"));
		System.out.println(parenthesis.isWellFormed("{)"));
		System.out.println(parenthesis.isWellFormed("[()[]{()()"));
		System.out.println(parenthesis.isWellFormed("[{]}"));
	}
	static Map<Character,Character> open = new HashMap<Character,Character>();
	static Map<Character,Character> close = new HashMap<Character,Character>();
	public static Character valueOf(char c) {
		return Character.valueOf(c);
	}
	static {
		open.put(valueOf('['),valueOf(']'));
		open.put(valueOf('('),valueOf(')'));
		open.put(valueOf('{'),valueOf('}'));
		close.put(valueOf(']'),valueOf('['));
		close.put(valueOf(')'),valueOf('('));
		close.put(valueOf('}'),valueOf('{'));
	}
	public boolean isWellFormed(String s) {
		Deque<Character> stack = new LinkedList<Character>();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (open.containsKey(c)) {
				stack.push(c);
			} else if (close.containsKey(c) && stack.pop() != close.get(c)) {
				return false;
			}
		}
		return stack.isEmpty();
	}
}
