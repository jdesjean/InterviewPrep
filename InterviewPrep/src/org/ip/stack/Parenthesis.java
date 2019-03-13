package org.ip.stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// EPI: 9.3
public class Parenthesis {
	public static void main(String[] s) {
		Parenthesis parenthesis = new Parenthesis();
		System.out.println(parenthesis.isWellFormed("[()[]{()()}]"));
		System.out.println(parenthesis.isWellFormed("([]){()}"));
		System.out.println(parenthesis.isWellFormed("{)"));
		System.out.println(parenthesis.isWellFormed("[()[]{()()"));
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
			Character c = valueOf(s.charAt(i));
			if (open.containsKey(c)) stack.push(c);
			else if (!stack.isEmpty() && stack.peek() == close.get(c)) stack.pop();
			else return false;
		}
		return stack.isEmpty();
	}
}
