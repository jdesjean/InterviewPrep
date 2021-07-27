package org.ip.string;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/simplify-path/">LC: 71</a>
 * EPI 2018: 8.4
 * EPI 9.4
 */
public class SimplifyPath {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {"/home", "/home/"};
		Object[] tc2 = new Object[] {"/", "/../"};
		Object[] tc3 = new Object[] {"/home/foo", "/home//foo/"};
		Object[] tc4 = new Object[] {"/c", "/a/./b/../../c/"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	public static class Solver implements Problem {

		@Override
		public String apply(String t) {
			String[] tokens = t.split("/");
			Deque<String> stack = new LinkedList<>();
			for (int i = 0; i < tokens.length; i++) {
				if (tokens[i].compareTo("..") == 0) {
					if (stack.size() > 0) {
						stack.removeLast();
					}
				} else if (tokens[i].compareTo(".") == 0 || tokens[i].compareTo("") == 0) {
					//no op
				} else {
					stack.addLast(tokens[i]);
				}
			}
			if (stack.isEmpty()) {
				stack.addLast("");
			}
			StringBuilder res = new StringBuilder();
			while (!stack.isEmpty()) {
				res.append("/" + stack.removeFirst());
			}
			return res.toString();
		}
		
	}
	public static class Solver2 implements Problem {
		static Map<String,Operator> operators = new HashMap<String,Operator>() {{
			put("..", new Pop());
			put("", new NoOp());
			put(".",  new NoOp());
		}};
		@Override
		public String apply(String path) {
			Deque<String> stack = new LinkedList<String>();
			boolean absolute = path.startsWith("/");
			if (absolute) path = path.substring(1);
			String[] aTokens = path.split("/");
			for (int i = 0; i < aTokens.length; i++) {
				String token = aTokens[i];
				if (operators.containsKey(token)) {
					operators.get(token).execute(stack);
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
	public static interface Problem extends Function<String, String> {
		
	}
}
