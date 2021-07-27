package org.ip.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * <a href="https://leetcode.com/problems/regular-expression-matching/">LC: 10</a>
 */
public class PatternMatch {
	public static void main(String[] s) {
		List<Consumer<Solver>> consumers = Arrays.asList(
				PatternMatch::tc1,
				PatternMatch::tc2,
				PatternMatch::tc3,
				PatternMatch::tc4,
				PatternMatch::tc5);
		Solver[] solvers = new Solver[] {new Recursive(), new DP()};
		for (Consumer<Solver> consumer : consumers) {
			for (Solver solver : solvers) {
				consumer.accept(solver);
			}
			System.out.println();
		}
	}
	public static void tc1(Solver solver) {
		//false
		System.out.println(solver.solve("aa", "a"));
	}
	public static void tc2(Solver solver) {
		//true
		System.out.println(solver.solve("aa", "a*"));
	}
	public static void tc3(Solver solver) {
		//true
		System.out.println(solver.solve("ab", ".*"));
	}
	public static void tc4(Solver solver) {
		//true
		System.out.println(solver.solve("aab", "c*a*b"));
	}
	public static void tc5(Solver solver) {
		//false
		System.out.println(solver.solve("mississippi", "mis*is*p*."));
	}
	public static class DP implements Solver {

		@Override
		public boolean solve(String s, String p) {
			List<Token> tokens = Token.parse(p);
			boolean[][] cache = new boolean[s.length() + 1][tokens.size() + 1];
			cache[s.length()][tokens.size()] = true;
			for (int i = s.length(); i >= 0; i--) {
				for (int j = tokens.size() - 1; j>= 0; j--) {
					Token token = tokens.get(j);
					if (token.count.isOptional()) {
						cache[i][j] |= cache[i][j + 1]; 
					}
					if (i < s.length() && token.characterClass.matches(s.charAt(i))) {
						if (token.count.hasNext()) {
							cache[i][j] |= cache[i + 1][j];
						} else {
							cache[i][j] |= cache[i + 1][j + 1];
						}
					}
				}
			}
			return cache[0][0];
		}
		
	}
	public static class Recursive implements Solver {

		@Override
		public boolean solve(String s, String p) {
			List<Token> tokens = Token.parse(p);
			return _solve(s, 0, tokens, 0);
		}
		boolean _solve(String s, int sIndex, List<Token> tokens, int tokensIndex) {
			if (tokens.size() == tokensIndex) {
 				return sIndex == s.length() && tokens.size() == tokensIndex;
			}
			Token token = tokens.get(tokensIndex);
			boolean matches = false;
			if (token.count.isOptional()) {
				matches = _solve(s, sIndex, tokens, tokensIndex + 1); // Match next character
			}
			if (matches) {
				return matches;
			}
			if (sIndex == s.length()) {
				return false;
			}
			if (token.characterClass.matches(s.charAt(sIndex))) {
				if (token.count.hasNext()) {
					matches = _solve(s, sIndex + 1, tokens, tokensIndex); // Try to match same token again
					return matches;
				} else {
					matches = _solve(s, sIndex + 1, tokens, tokensIndex + 1); // Try to match with next character
					return matches;
				}
			}
			return false;
		}
		
	}
	private static class Token {
		final CharacterClass characterClass;
		final Count count;
		public Token(CharacterClass characterClass, Count count) {
			this.characterClass = characterClass;
			this.count = count;
		}
		@Override
		public String toString() {
			return "Token [characterClass=" + characterClass + ", count=" + count + "]";
		}
		static List<Token> parse(String p) {
			Count count = null;
			List<Token> tokens = new ArrayList<Token>();
			for (int i = p.length() - 1; i >= 0; i--) {
				if (p.charAt(i) == '*') {
					count = new StarCount();
				} else {
					if (count == null) {
						count = new FiniteCount(1);
					}
					CharacterClass characterClass; 
					if (p.charAt(i) == '.') {
						characterClass = new AnyCharacter();
					} else {
						characterClass = new SingleCharacter(p.charAt(i));
					}
					tokens.add(0, new Token(characterClass, count));
					count = null;
				}
			}
			return tokens;
		}
	}
	private interface Count {
		boolean hasNext();
		boolean isOptional(); 
	}
	private interface CharacterClass {
		boolean matches(char c);
	}
	private static class SingleCharacter implements CharacterClass {
		char c;
		public SingleCharacter(char c) {
			this.c = c;
		}
		@Override
		public boolean matches(char c) {
			return this.c == c;
		}
		@Override
		public String toString() {
			return String.valueOf(c);
		}
		
	}
	private static class AnyCharacter implements CharacterClass {
		@Override
		public boolean matches(char c) {
			return true;
		}

		@Override
		public String toString() {
			return ".";
		}
		
	}
	private static class FiniteCount implements Count {
		private int value;
		public FiniteCount(int value) {
			this.value = value;
		}
		@Override
		public boolean hasNext() {
			return value > 1;
		}
		@Override
		public boolean isOptional() {
			return false;
		}
		
		@Override
		public String toString() {
			return String.valueOf(value);
		}
		
	}
	private static class StarCount implements Count {

		@Override
		public boolean hasNext() {
			return true;
		}
		
		@Override
		public boolean isOptional() {
			return true;
		}

		@Override
		public String toString() {
			return "*";
		}
		
	}
	public interface Solver {
		public boolean solve(String s, String p);
	}
}
