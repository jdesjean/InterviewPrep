package org.ip.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/construct-binary-tree-from-string/">LC: 536</a>
 */
public class ConstructFromString {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new Integer[] {4,2,6,3,1,5}, "4(2(3)(1))(6(5))"};
		Object[] tc2 = new Object[] {new Integer[] {4,2,6,3,1,5,7}, "4(2(3)(1))(6(5)(7))"};
		Object[] tc3 = new Object[] {new Integer[] {-4,2,6,3,1,5,7}, "-4(2(3)(1))(6(5)(7))"};
		Object[] tc4 = new Object[] {new Integer[] {4}, "4"};
		Object[][] tcs = new Object[][] {tc4, tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver(), new Solver2()};
		Test.apply(solvers, tcs);
	}
	public static class Solver2 implements Problem  {

		@Override
		public TreeNode apply(String t) {
			return _apply(t, new AtomicInteger(0));
		}
		TreeNode _apply(String t, AtomicInteger idx) {
			if (idx.get() == t.length()) {
				return null;
			}
			TreeNode node = new TreeNode(getInteger(t, idx));
			node.left = getNode(t, idx);
			node.right = node.left != null ? getNode(t, idx) : null;
			return node;
		}
		int getInteger(String t, AtomicInteger idx) {
			int v = 0;
			int sign = 1;
			if (t.charAt(idx.get()) == '-') {
				sign = -1;
				idx.incrementAndGet();
			}
			for (; idx.get() < t.length() && Character.isDigit(t.charAt(idx.get())); idx.incrementAndGet()) {
				v = v * 10 + Character.digit(t.charAt(idx.get()), 10);
			}
			v *= sign;
			return v;
		}
		TreeNode getNode(String t, AtomicInteger idx) {
			if (idx.get() >= t.length() || t.charAt(idx.get()) != '(') return null;
			idx.incrementAndGet();
			TreeNode child = _apply(t, idx);
			idx.incrementAndGet();
			return child;
		}
	}
	public static class Solver implements Problem  {

		@Override
		public TreeNode apply(String t) {
			if (t == null || t.length() == 0) return null;
			List<Token> tokens = toToken(t);
			return _apply(tokens, new AtomicInteger());
		}
		TreeNode _apply(List<Token> tokens, AtomicInteger idx) {
			if (idx.get() > tokens.size()) {
				return null;
			}
			Token token = tokens.get(idx.getAndIncrement());
			TreeNode current = new TreeNode(((NumberToken)token).value);
			if (idx.get() < tokens.size()) {
				token = tokens.get(idx.getAndIncrement());
				if (((ParenToken)token).paren == Paren.OPEN) {
					current.left = _apply(tokens, idx);
				} else {
					return current; // questions says we cannot get null left nodes with non null right node
				}
			}
			if (idx.get() < tokens.size()) {
				token = tokens.get(idx.getAndIncrement());
				if (((ParenToken)token).paren == Paren.OPEN) {
					current.right = _apply(tokens, idx);
				} else {
					return current; // questions says we cannot get null left nodes with non null right node
				}
				idx.getAndIncrement(); // ending paren for the current node
			}
			return current;
		}
		List<Token> toToken(String t) {
			List<Token> tokens = new ArrayList<>();
			int sign = 1;
			int value = 0;
			boolean hasValue = false;
			for (int i = 0; i < t.length(); i++) {
				if (t.charAt(i) == '-') {
					sign = -1;
				} else if (t.charAt(i) == '(') {
					if (hasValue) {
						tokens.add(new NumberToken(value * sign));
						value = 0;
						sign = 1;
						hasValue = false;
					}
					tokens.add(new ParenToken(Paren.OPEN));
				} else if (t.charAt(i) == ')') {
					if (hasValue) {
						tokens.add(new NumberToken(value * sign));
						value = 0;
						sign = 1;
						hasValue = false;
					}
					tokens.add(new ParenToken(Paren.CLOSE));
				} else {
					hasValue = true;
					value = value * 10 + Character.getNumericValue(t.charAt(i));
				}
			}
			if (hasValue) {
				tokens.add(new NumberToken(value * sign));
			}
			return tokens;
		}
		public interface Token {
			
		}
		public static class NumberToken implements Token {
			int value;
			public NumberToken(int value) {
				this.value = value;
			}
		}
		public static class ParenToken implements Token {
			private Paren paren;

			public ParenToken(Paren paren) {
				this.paren = paren;
			}
		}
		enum Paren {
			OPEN, CLOSE
		}
	}
	public interface Problem extends Function<String, TreeNode> {
		
	}
}
