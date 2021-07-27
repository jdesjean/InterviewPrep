package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.ip.Test;
import org.ip.Test.TriFunction;

/**
 * <a href="https://leetcode.com/problems/basic-calculator-iv/">LC: 770</a>
 */
public class BasicCalculatorIV {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new String[] {"14","-1*a"}, "e + 8 - a + 5", new String[] {"e"}, new int[] {1} };
		Object[] tc2 = new Object[] { new String[] {"4*a"}, "a*e*2", new String[] {"e"}, new int[] {2} };
		Object[] tc3 = new Object[] { new String[] {"2","3*a", "1*a*a"}, "(a + 1) * (a + 2)", new String[] {"e"}, new int[] {2} };
		Object[][] tcs = new Object[][] {tc1, tc2, tc3};
		Problem[] solvers = new Problem[] { new BnfSolver(), new Solver() };
		Test.apply(solvers, tcs);
	}
	
	static class Solver implements Problem {
		@Override
		public List<String> apply(String a, String[] b, int[] c) {
			 List<String> res= new LinkedList<>();
	        Map<String, Integer> map= new HashMap<>();
	        for (int i=0; i<b.length; i++) map.put(b[i], c[i]);
	        Sequence sq= helper(a, map, new AtomicInteger());
	        Collections.sort(sq.terms);
	        for (Term t: sq.terms) 
	            if (!t.toString().equals("0"))
	                res.add(t.toString());
	        return res;
		}
		class Term implements Comparable<Term>{
	        int coef=1;
	        List<String> vars = new LinkedList<>();
	        public Term(int coef){ this.coef=coef; }
	        public Term(String s){ vars.add(s); }
	        public String varString(){ 
	            return vars.isEmpty()?"":("*"+String.join("*", vars)); 
	        }
	        public String toString(){ return coef+varString(); }
	        public boolean equals(Term o){return vars.equals(o.vars); }
	        public int compareTo(Term t){
	        	int cmp = Integer.compare(vars.size(), t.vars.size());
	        	if (cmp != 0) return cmp;
	        	for (int i = 0; i < vars.size(); i++) {
	        		cmp = vars.get(i).compareTo(t.vars.get(i));
	        		if (cmp != 0) return cmp;
	        	}
	        	return 0;
	        }
	        public Term multi(Term t){
	            Term res= new Term(coef*t.coef);
	            res.vars.addAll(vars);
	            res.vars.addAll(t.vars);
	            Collections.sort(res.vars);
	            return res;
	        }
	    }
	    class Sequence{
	        List<Term> terms= new LinkedList<>();
	        public Sequence(){}
	        public Sequence(int n){terms.add(new Term(n));}
	        public Sequence(String s){terms.add(new Term(s));}
	        public Sequence(Term t){terms.add(t);}
	        public Sequence add(Sequence sq){
	            for (Term t2: sq.terms){
	                if (t2.coef==0) continue;
	                boolean found=false;
	                for (Term t1: terms){
	                    if (t1.equals(t2)){
	                        t1.coef+=t2.coef;
	                        if (t1.coef==0) terms.remove(t1);
	                        found=true;
	                        break;
	                    }
	                }
	                if (!found) terms.add(t2);
	            }
	            return this;
	        }
	        public Sequence multi(Sequence sq){
	            Sequence res= new Sequence();
	            for (Term t1: terms)
	                for (Term t2: sq.terms)
	                    res.add(new Sequence(t1.multi(t2)));
	            return res;
	        }
	    }
	    private Sequence helper(String e, Map<String, Integer> map, AtomicInteger idx){
	        Deque<Sequence> stack=new LinkedList<>();
	        stack.push(new Sequence(0));
	        int flag=1;
	        while (idx.get() < e.length()){
	            char c= e.charAt(idx.getAndIncrement());
	            if (c==' ') continue;
	            else if (c=='('){
	                Sequence sq= helper(e, map, idx);
	                addToStack(stack, sq, flag);
	            }else if (c==')') break;
	            else if (c=='+' || c=='-' || c=='*') flag= c=='+'?1:c=='-'?-1:0;
	            else if (Character.isDigit(c)){
	                int coef=c-'0';
	                while (idx.get() < e.length() && Character.isDigit(e.charAt(idx.get()))) coef = coef*10 + e.charAt(idx.getAndIncrement()) - '0';
	                addToStack(stack, new Sequence(coef), flag);
	            }else{
	                StringBuilder sb=new StringBuilder(c+"");
	                while (idx.get() < e.length() && Character.isLetter(e.charAt(idx.get()))) sb.append(e.charAt(idx.getAndIncrement()));
	                String var= sb.toString();
	                if (map.containsKey(var)) addToStack(stack, new Sequence(map.get(var)), flag);
	                else addToStack(stack, new Sequence(var), flag);
	            }
	        }
	        Sequence res= new Sequence();
	        while (!stack.isEmpty()) res.add(stack.pop());
	        return res;
	    }
	    private void addToStack(Deque<Sequence> stack, Sequence sq, int flag){
	        if (flag==0) stack.push(stack.pop().multi(sq));
	        else stack.push(new Sequence(flag).multi(sq));
	    }
	}
	static class BnfSolver implements Problem {

		Map<Character, Tokenable> map = new HashMap<>() {{
			put('+', new PlusToken());
			put('-', new MinusToken());
			put('*', new MultToken());
			put('/', new DivToken());
			put('(', new OpenParen());
			put(')', new CloseParen());
		}};
		
		@Override
		public List<String> apply(String s, String[] vars, int[] vals) {
			Map<String, Integer> varValues = new HashMap<>();
			for (int i = 0; i < vars.length; i++) {
				varValues.put(vars[i], vals[i]);
			}
			Deque<Tokenable> deque = parse(s, varValues);
			Bnf bnf = new Bnf(deque);
			Node node = bnf.expression();
			return null;
		}
		
		private Deque<Tokenable> parse(String s, Map<String, Integer> varValues) {
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
					deque.addLast(new VariableToken(value));
				} else {
					int j = i;
					for (; j < s.length() && Character.isLetter(s.charAt(j)); j++) {}
					String var = s.substring(i, j);
					Integer value = varValues.get(var);
					deque.addLast(value == null ? new VariableToken(var) : new VariableToken(value));
					i = j;
				}
			}
			return deque;
		}
		private static class Node {
			Node left;
			Node right;
			Tokenable token;
			public Node(Operand token) {
				this.token=token;
			}
			public Node(Operator token, Node left, Node right) {
				this.token=token;
				this.left = left;
				this.right = right;
			}
			
			
			@Override
			public String toString() {
				if (left != null) {
					return left.toString() + token.toString() + right.toString();
				} else {
					return token.toString();
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
			
			@Override
			public String toString() {
				return "*";
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
			
			@Override
			public String toString() {
				return "/";
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
			
			@Override
			public String toString() {
				return "+";
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
			
			@Override
			public String toString() {
				return "-";
			}
			
		}
		static class VariableToken implements Tokenable, Operand {
			private List<String> vars = new ArrayList<>();
			int value;

			public VariableToken(String var) {
				this.value = 1;
				vars.add(var);
			}
			
			public VariableToken(int value) {
				this.value = value;
			}

			@Override
			public Node build(Bnf bnf, Node left) {
				return new Node(this);
			}

			@Override
			public String toString() {
				return String.valueOf(value) + (vars.size() > 0 ? "*" + String.join("*", vars) : "");
			}
		}
	}
	interface Problem extends TriFunction<String, String[], int[], List<String>> {
		
	}
}
