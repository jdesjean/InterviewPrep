package org.ip.string;

import java.util.function.IntPredicate;

import org.ip.string.Palindrome.ComparatorChar;

// EPI: 7.5
// ignore space, comma, case
public class PalindromeChecker {
	public static void main(String[] s) {
		PalindromeChecker checker = new PalindromeChecker(SKIP_NON_LETTER, COMPARATOR_LOWER_CASE);
		String s1 = "A man, a plan, a canal, Panama";
		String s2 = "Ray a Ray";
		System.out.println(checker.check(s1, 0, s1.length() - 1));
		System.out.println(checker.check(s2, 0, s2.length() - 1));
	}
	public static final ComparatorChar COMPARATOR_CASE = new ComparatorChar(){
		@Override
		public int compare(char c1, char c2) {
			return c1 - c2;
		}
	};
	public static final ComparatorChar COMPARATOR_LOWER_CASE = new ComparatorChar(){
		@Override
		public int compare(char c1, char c2) {
			return Character.toLowerCase(c1) - Character.toLowerCase(c2);
		}
	};
	public static final IntPredicate SKIP_NON_LETTER = c -> !Character.isLetter((char)c);
	public static final IntPredicate SKIP_ALWAYS_FALSE = c -> false;
	private ComparatorChar comparator;
	private IntPredicate skipper;
	public PalindromeChecker() {
		this.skipper = SKIP_ALWAYS_FALSE;
		this.comparator = COMPARATOR_CASE;
	}
	public PalindromeChecker(IntPredicate skipper, ComparatorChar comparator) {
		this.comparator = comparator;
		this.skipper = skipper;
	}
	public boolean check(String s, int i, int j) {
		for (; i < j;) {
			if (skipper.test(s.charAt(i))) i++;
			else if (skipper.test(s.charAt(j))) j--;
			else if (comparator.compare(s.charAt(i), s.charAt(j)) != 0) return false;
			else {
				i++;
				j--;
			}
		}
		return true;
	}
}
