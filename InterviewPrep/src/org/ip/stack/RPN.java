package org.ip.stack;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

// EPI: 9.2
public class RPN {
	public static void main(String[] s) {
		RPN rpn = new RPN();
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
	//EPI 9.2
	public int evaluate(String s) {
		Deque<Integer> stack = new LinkedList<Integer>();
		String[] aTokens = s.split(",");
		for (int i = 0; i < aTokens.length; i++) {
			String token = aTokens[i];
			
			if (token.length() == 1 && operators.containsKey(Character.valueOf(token.charAt(0)))) {
				int v2 = stack.pop();
				int v1 = stack.pop();
				int v3 = operators.get(token.charAt(0)).execute(v1, v2);
				stack.push(v3);
			} else {
				stack.push(org.ip.primitives.Number.fromString(token));
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
