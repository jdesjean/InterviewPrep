package org.ip.string;

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/basic-calculator/">LC: 224</a>
 */
public class BasicCalculator {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				BasicCalculator::tc1,
				BasicCalculator::tc2,
				BasicCalculator::tc3,
				BasicCalculator::tc4,
				BasicCalculator::tc5,
				BasicCalculator::tc6,
				BasicCalculator::tc7);
		Solver[] solvers = new Solver[] {new Recursive(), new BnfSolver()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		System.out.println(solver.solve("1 + 1"));
	}
	public static void tc2(Solver solver) {
		System.out.println(solver.solve(" 1-1 + 2 "));
	}
	public static void tc3(Solver solver) {
		System.out.println(solver.solve("(1-(1+1)+3)"));
	}
	public static void tc4(Solver solver) {
		System.out.println(solver.solve("-2"));
	}
	public static void tc5(Solver solver) {
		System.out.println(solver.solve("2*2*1+1"));
	}
	public static void tc6(Solver solver) {
		System.out.println(solver.solve("1+1*2*2"));
	}
	public static void tc7(Solver solver) {
		System.out.println(solver.solve("2/2*2"));
	}
	static class BnfSolver implements Solver {

		Map<Character, Tokenable> map = new HashMap<>() {{
			put('+', new PlusToken());
			put('-', new MinusToken());
			put('*', new MultToken());
			put('/', new DivToken());
			put('(', new OpenParen());
			put(')', new CloseParen());
		}};
		@Override
		public int solve(String s) {
			Deque<Tokenable> deque = parse(s);
			Bnf bnf = new Bnf(deque);
			Node node = bnf.expression();
			return node.eval();
		}
		
		private Deque<Tokenable> parse(String s) {
			Deque<Tokenable> deque = new LinkedList<>();
			for (int i = 0; i < s.length(); ) {
				if (Character.isWhitespace(s.charAt(i))) {
					i++;
					continue;
				}
				Tokenable token = map.get(s.charAt(i));
				if (token != null) {
					deque.addLast(token);
					i++;
				} else if (Character.isDigit(s.charAt(i))) {
					int value = 0;
					for (; i < s.length() && Character.isDigit(s.charAt(i)); i++) {
						value *= 10;
						value += s.charAt(i) - '0';
					}
					deque.addLast(new OperandToken(value));
				} else {
					int j = i;
					for (; j < s.length() && Character.isLetter(s.charAt(j)); j++) {}
					deque.addLast(new VariableToken(s.substring(i, j)));
					i = j;
				}
			}
			return deque;
		}
		private static class Node {
			Node left;
			Node right;
			Tokenable token;
			public Node(Tokenable token) {
				this.token=token;
			}
			public Node(Operator token, Node left, Node right) {
				this.token=token;
				this.left = left;
				this.right = right;
			}
			public int eval() {
				if (token instanceof Operator) {
					int l = left != null ? left.eval() : 0;
					int r = right != null ? right.eval() : 0;
					return ((Operator) token).eval(l, r);
				} else {
					return ((OperandToken)token).value;
				}
			}
		}
		interface Tokenable {
			Node build(Bnf bnf, Node left);
		}
		interface Operator extends Tokenable {
			int eval(int left, int right);
		}
		interface Operand extends Tokenable {
			int eval();
		}
		interface PrecedenceTwo {
			
		}
		/**
		 * With mult, div
		 * exp = term + term | term
		 * term = factor * factor | factor
		 * factor = ( exp ) | number
		 * 
		 * With plus, minus
		 * exp = term + term | term
		 * term = ( exp ) | number
		 */
		static class Bnf {
			private Deque<Tokenable> deque;
			public Bnf(Deque<Tokenable> deque) {
				this.deque = deque;
			}
			Node expression() {
				Node left = term();
				while (!deque.isEmpty()) {
					Node root = deque.removeFirst().build(this, left);
					if (root == null) break;
					left = root;
				}
				return left;
			}
			Node term() {
				Node left = factor();
				while (!deque.isEmpty()) {
					if (!(deque.peek() instanceof PrecedenceTwo)) break;
					Node root = deque.removeFirst().build(this, left);
					if (root == null) break;
					left = root;
				}
				return left;
			}
			Node factor() {
				return deque.removeFirst().build(this, null);
			}
		}
		static class OpenParen implements Tokenable {
			@Override
			public Node build(Bnf bnf, Node left) {
				return bnf.expression();
			}
		}
		static class CloseParen implements Tokenable {
			@Override
			public Node build(Bnf bnf, Node left) {
				return null;
			}
		}
		static class MultToken implements Operator, PrecedenceTwo {

			@Override
			public Node build(Bnf bnf, Node left) {
				return new Node(this, left, bnf.factor());
			}

			@Override
			public int eval(int left, int right) {
				return left * right;
			}
			
		}
		static class DivToken implements Operator, PrecedenceTwo {

			@Override
			public Node build(Bnf bnf, Node left) {
				return new Node(this, left, bnf.factor());
			}

			@Override
			public int eval(int left, int right) {
				return left / right;
			}
			
		}
		static class PlusToken implements Operator {

			@Override
			public Node build(Bnf bnf, Node left) {
				return new Node(this, left, bnf.term());
			}

			@Override
			public int eval(int left, int right) {
				return left + right;
			}
			
		}
		static class MinusToken implements Operator {

			@Override
			public Node build(Bnf bnf, Node left) {
				return new Node(this, left, bnf.term());
			}

			@Override
			public int eval(int left, int right) {
				return left - right;
			}
			
		}
		static class OperandToken implements Tokenable {
			private int value;

			public OperandToken(int value) {
				this.value = value;
			}

			@Override
			public Node build(Bnf bnf, Node left) {
				return new Node(this);
			}
			
		}
		static class VariableToken implements Tokenable {
			private String value;

			public VariableToken(String value) {
				this.value = value;
			}

			@Override
			public Node build(Bnf bnf, Node left) {
				return new Node(this);
			}
			
		}
	}
	public interface Solver {
		int solve(String s);
	}
	private static class Recursive implements Solver {
		public int calculate(String s) {
	        int res = 0, prev = 0, cur = 0;
	        s = s.trim();
	        char sign = '+';
	        for(int i = 0; i < s.length(); i++){
	            char c = s.charAt(i);
	            if(c == ' '){
	                continue;
	            }
	            if(c == '('){
	                int end = findPair(s, i);
	                cur = calculate(s.substring(i + 1, end));
	                i = end;
	            }
	            if(Character.isDigit(c)){
	                cur = cur * 10 + (c - '0');
	                if(i != s.length() - 1){
	                    continue;
	                }
	            } 
	            switch(sign){
	                case '+':
	                    res += prev;
	                    prev = cur;
	                    break;
	                case '-':
	                    res += prev;
	                    prev = -cur;
	                    break;
	                case '*':
	                    prev *= cur;
	                    break;
	                case '/':
	                    prev /= cur;
	                    break;
	            }
	            sign = c;
	            cur = 0;
	        }
	        res += prev;
	        return res;
	    }
	    
	    public int findPair(String s, int i){
	        int cnt = 0;
	        while(i < s.length()){
	            if(s.charAt(i) == '('){
	                cnt++;
	            } else if(s.charAt(i) == ')'){
	                cnt--;
	                if(cnt == 0){
	                    return i;
	                }
	            }
	            i++;
	        }
	        return i;
	    }

		@Override
		public int solve(String s) {
			return calculate(s);
		}
	}
}
