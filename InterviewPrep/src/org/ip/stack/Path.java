package org.ip.stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// EPI 9.4
public class Path {
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
	//EPI 9.4
	public static String shortestEquivalent(String path) {
		Deque<String> stack = new LinkedList<String>();
		boolean absolute = path.startsWith("/");
		if (absolute) path = path.substring(1);
		String[] aTokens = path.split("/");
		for (int i = 0; i < aTokens.length; i++) {
			String token = aTokens[i];
			if (operators.containsKey(token)) {
				operators.get(token).execute(stack, token);
			} else {
				stack.push(token);
			}
		}
		StringBuilder sb = new StringBuilder();
		if (absolute) sb.append('/');
		while (!stack.isEmpty()) {
			sb.append(stack.removeLast());
			sb.append('/');
		}
		return sb.toString();
	}
	public interface Operator {
		public void execute(Deque<String> stack, String token);
	}
	public static class Pop implements Operator{
		@Override
		public void execute(Deque<String> stack, String token) {
			stack.pop();
		}
	}
	public static class NoOp implements Operator {
		@Override
		public void execute(Deque<String> stack, String token) {
		}
	}
	public static class Push implements Operator {

		@Override
		public void execute(Deque<String> stack, String token) {
			stack.push(token);
		}
		
	}
}
