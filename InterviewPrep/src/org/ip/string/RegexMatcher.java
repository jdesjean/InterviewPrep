package org.ip.string;

public class RegexMatcher {
	public static void main(String[] s) {
		System.out.println(match("aaabbb","a*b*"));
		System.out.println(match("aaabbb","a*b.."));
		System.out.println(match("aaabbb","a..b.."));
		System.out.println(match("aaabbb","a.b."));
		System.out.println(match("aaabbb","a*bbb"));
		System.out.println(match("aaabbb","aaabbb"));
		System.out.println(match("aaabbb","a*b"));
		System.out.println(match("aaabbb","ab*"));
		System.out.println(match("aaabbb","ab"));
	}
	public static boolean match(String text, String pattern) {
		return match(text,0,pattern,0);
	}
	public static boolean match(String text, int i, String pattern, int j) {
		if (j == pattern.length() && i == text.length()) return true;
		if (j >= pattern.length() || i >= text.length()) return false;
		if (pattern.charAt(j) == '.' || 
				pattern.charAt(j) == text.charAt(i)) return match(text,i+1,pattern,j+1);
		if (pattern.charAt(j) == '*' && (j == 0 || pattern.charAt(j-1) == '*' || pattern.charAt(j-1) == '.')) return false;
		if (pattern.charAt(j) == '*' && text.charAt(i) == pattern.charAt(j-1)) return match(text,i+1,pattern,j+1) || match(text,i+1,pattern,j);
		return false;
	}
}
