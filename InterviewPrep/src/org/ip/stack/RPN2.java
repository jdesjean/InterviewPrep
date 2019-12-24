package org.ip.stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// EPI 2018: 8.2
public class RPN2 {
	public static void main(String[] s) {
		RPN2 rpn = new RPN2();
		System.out.println(rpn.evaluate("1729"));
		System.out.println(rpn.evaluate("3,4,+,2,x,1,+"));
		System.out.println(rpn.evaluate("1,1,+,-2,x"));
		System.out.println(rpn.evaluate("-641,6,/,28,/"));
	}
	static Map<Character,Operator> operators = new HashMap<Character,Operator>(); 
	static {
		operators.put(Character.valueOf('x'), new Multiply());
		operators.put(Character.valueOf('/'), new Divide());
		operators.put(Character.valueOf('+'), new Add());
		operators.put(Character.valueOf('-'), new Remove());
	}
	public int evaluate(String s) {
		Deque<Integer> stack = new LinkedList<Integer>();
		for (int i = 0, v = 0, b = 1; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '-') {
				 b = -1;
			} else if (Character.isDigit(c)) {
				if (v == 0) {
					v = 1;
					stack.push(b * Character.digit(c, 10));
				} else {
					stack.push(b * (Math.abs(stack.pop()) * 10 + Character.digit(c, 10))); 
				}
			} else if (c == ',') {
				v = 0;
				b = 1;
			} else {
				int v2 = stack.pop();
				int v1 = stack.pop();
				stack.push(operators.get(c).execute(v1, v2));
			}
		}
		return stack.pop();
	}
	public interface Operator {
		public int execute(int v1, int v2);
	}
	public static class Add implements Operator{
		@Override
		public int execute(int v1, int v2) {
			return v1 + v2;
		}
	}
	public static class Remove implements Operator {
		@Override
		public int execute(int v1, int v2) {
			return v1 - v2;
		}
	}
	public static class Multiply implements Operator {
		@Override
		public int execute(int v1, int v2) {
			return v1 * v2;
		}
	}
	public static class Divide implements Operator {
		@Override
		public int execute(int v1, int v2) {
			return v1 / v2;
		}
	}
}
