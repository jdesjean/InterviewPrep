package org.ip.primitives;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/valid-number/">LC: 65</a>
 */
public class ValidNumber {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Object[] tc1 = new Object[] {true, "2"};
		Object[] tc2 = new Object[] {true, "0089"};
		Object[] tc3 = new Object[] {true, "-0.1"};
		Object[] tc4 = new Object[] {true, "+3.14"};
		Object[] tc5 = new Object[] {true, "4."};
		Object[] tc6 = new Object[] {true, "-.9"};
		Object[] tc7 = new Object[] {true, "2e10"};
		Object[] tc8 = new Object[] {true, "-90E3"};
		Object[] tc9 = new Object[] {true, "3e+7"};
		Object[] tc10 = new Object[] {true, "+6e-1"};
		Object[] tc11 = new Object[] {true, "53.5e93"};
		Object[] tc12 = new Object[] {true, "-123.456e789"};
		Object[] tc13 = new Object[] {false, "0e"};
		Object[] tc14 = new Object[] {false, "abc"};
		Object[] tc15 = new Object[] {false, "1a"};
		Object[] tc16 = new Object[] {false, "1e"};
		Object[] tc17 = new Object[] {false, "e3"};
		Object[] tc18 = new Object[] {false, "99e2.5"};
		Object[] tc19 = new Object[] {false, "--6"};
		Object[] tc20 = new Object[] {false, "-+3"};
		Object[] tc21 = new Object[] {false, "95a54e53"};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4, tc5, tc6, tc7, tc8, tc9, tc10, tc11, tc12, tc13, tc14, tc15, tc16, tc17, tc18, tc19, tc20, tc21};
		//Object[][] tcs = new Object[][] {tc16};
		Problem[] solvers = new Problem[] { new Solver(), new Solver2() };
		Test.apply(solvers, tcs);
	}
	/**   
             s
      i    +--->6
     +-+   |    |i
  i  | ve  | i  v
0 ->  1 -> 5 -> 7
|     ^  | ^   | ^
|  s i|  | | e +-+
+---->2  | |    i
|    .| .| |
|  .  v  v |
+---->3 -> 4
        i | ^
          +-+
           i
	 */
	static class Solver2 implements Problem {
		enum Edge {
			INT, SIGN, E, DOT, UNKNOWN;
			static Edge from(char c) {
				if (Character.isDigit(c)) {
					return INT;
				} else if (c == '+' || c == '-') {
					return SIGN;
				} else if (c == 'e' || c == 'E') {
					return E;
				} else if (c == '.') {
					return DOT;
				} else {
					return UNKNOWN;
				}
			}
		}
		private static final Set<Integer> END = Set.of(1, 4, 7);
		static class State implements Comparable<State> {
			final Edge edge;
			final Integer vertex;
			public State(Edge edge, Integer vertex) {
				super();
				this.edge = edge;
				this.vertex = vertex;
			}
			@Override
			public int compareTo(State o) {
				return edge.compareTo(o.edge);
			}
		}
		
		@Override
		public boolean test(String t) {
			Map<Integer, Map<Edge, Integer>> map = graph();
			int vertex = 0;
			for (int i = 0; i < t.length(); i++) {
				Map<Edge, Integer> state = map.get(vertex);
				Edge current = Edge.from(t.charAt(i));
				Integer currentVertex = state.get(current);
				if (currentVertex == null) return false;
				vertex = currentVertex;
				
			}
			return END.contains(vertex);
		}
		
		static Map<Integer, Map<Edge, Integer>> graph() {
			Map<Integer, Map<Edge, Integer>> map = new HashMap<>();
			map.put(0, Map.of(Edge.INT, 1, Edge.SIGN, 2, Edge.DOT, 3));
			map.put(1, Map.of(Edge.INT, 1, Edge.DOT, 4, Edge.E, 5));
			map.put(2, Map.of(Edge.INT, 1, Edge.DOT, 3));
			map.put(3, Map.of(Edge.INT, 4));
			map.put(4, Map.of(Edge.INT, 4, Edge.E, 5));
			map.put(5, Map.of(Edge.SIGN, 6, Edge.INT, 7));
			map.put(6, Map.of(Edge.INT, 7));
			map.put(7, Map.of(Edge.INT, 7));
			return map;
		}
	}
	/*
	 *  [\decimal\integer][eE]?[\integer]
	 *  \decimal [\+\-]?\opt1\opt2\opt3
	 *  \opt1 [digit+\.]
	 *  \opt2 [digit+\.digit+]
	 *  \opt3 [\.digit+]
	 *  \integer [\+\-]?digit+
	 */
	/*
	 * sign: \+\-
	 * integer: sign*digit+
	 * decimal: sign*digit+(.digit*)?
	 * number: (integer|decimal){1}((e)integer)? 
	 */
	static class Solver implements Problem {

		@Override
		public boolean test(String t) {
			AtomicInteger i = new AtomicInteger();
			boolean isDecimal = decimal(t, i);
			if (e(t, i)) {
				isDecimal &= integerSigned(t, i);
			}
			return isDecimal && i.get() == t.length();
		}
		boolean character(String t, AtomicInteger i, Predicate<Character> predicate) {
			if (i.get() < t.length() && predicate.test(t.charAt(i.get()))) {
				i.incrementAndGet();
				return true;
			} else {
				return false;
			}
		}
		boolean decimal(String t, AtomicInteger i) {
			sign(t, i);
			boolean isInteger = integerUnsigned(t, i);
			if (character(t, i, c -> c == '.')) {
				isInteger |= integerUnsigned(t, i);
			}
			return isInteger;
		}
		boolean integerSigned(String t, AtomicInteger i) {
			sign(t, i);
			return integerUnsigned(t, i);
		}
		boolean integerUnsigned(String t, AtomicInteger i) {
			boolean isDigit = false;
			while (i.get() < t.length() && Character.isDigit(t.charAt(i.get()))) {
				i.incrementAndGet();
				isDigit = true;
			}
			return isDigit;
		}
		boolean sign(String t, AtomicInteger i) {
			return character(t, i, c -> c == '+' || c == '-');
		}
		boolean e(String t, AtomicInteger i) {
			return character(t, i, c -> c == 'e' || c == 'E');
		}
	}
	interface Problem extends Predicate<String> {
		
	}
}
