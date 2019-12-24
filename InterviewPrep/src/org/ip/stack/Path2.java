package org.ip.stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//EPI 2018: 8.4
public class Path2 {
	public static void main(String[] s) {
		System.out.println(shortestEquivalent("/usr/lib/../bin/gcc"));
		System.out.println(shortestEquivalent("scripts//./../scripts/awkscripts/././"));
		System.out.println(shortestEquivalent("/usr/../../"));
	}
	static Map<String,Operator> operators = new HashMap<String,Operator>();
	static {
		operators.put("..", new Pop());
		operators.put("", new NoOp());
		operators.put(".",  new NoOp());
	}
	public static String shortestEquivalent(String path) {
		String[] tokens = path.split("/");
		Deque<String> stack = new LinkedList<String>();
		for (int i = 0; i < tokens.length; i++) {
			if (operators.containsKey(tokens[i])) {
				if (!operators.get(tokens[i]).execute(stack)) {
					throw new RuntimeException("Illegal path");
				}
			} else {
				stack.push(tokens[i]);
			}
		}
		StringBuilder sb = new StringBuilder();
		if (path.charAt(0) == '/') {
			sb.append('/');
		}
		while (!stack.isEmpty()) {
			sb.append(stack.removeLast() + '/');
		}
		return sb.toString();
	}
	public interface Operator {
		public boolean execute(Deque<String> stack);
	}
	public static class Pop implements Operator{
		@Override
		public boolean execute(Deque<String> stack) {
			if (!stack.isEmpty()) {
				stack.pop();
				return true;
			} else {
				return false;
			}
		}
	}
	public static class NoOp implements Operator {
		@Override
		public boolean execute(Deque<String> stack) {
			return true;
		}
	}
}

