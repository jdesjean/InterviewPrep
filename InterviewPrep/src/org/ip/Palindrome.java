package org.ip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.ip.Visitors.StringVisitor;

public class Palindrome {
	public static void main(String[] s) {
		/*
		 * a|b|r|a|c|a|d|a|b|r|a|
			a|b|r|a|c|ada|b|r|a|
			a|b|r|aca|d|a|b|r|a|
		 */
		StringVisitor visitor = new StringVisitor(){

			@Override
			public void visit(String string) {
				System.out.println(string);
			}
			
		};
		new InterativeDecomposer().decompose(visitor,"abracadabra");
		System.out.println("");
		new RecursiveDecomposer().decompose(visitor,"abracadabra");
	}
	
	public interface Decomposer {
		public void decompose(StringVisitor visitor, String palindrome);
	}
	
	//IterativeDecomposer needs some optimization
	//A lot of the generated combination are filtered
	public static class InterativeDecomposer implements Decomposer {

		private static boolean isPalindrome(String palindrome) {
			for (int i = 0, j = 0; i < palindrome.length(); i++) {
				if (palindrome.charAt(i) == '|' && j != i) {
					int jj = palindrome.charAt(j) == '|'  ? j+1 : j;
					int ii = palindrome.charAt(i) == '|'  ? i-1 : i;
					if (!Palindrome.isPalindrome(palindrome,jj,ii)) return false;
					j = i;
				} else if (i == palindrome.length()-1 && j != i) return false;
			}
			return true;
		}
		
		private static String join(String letters, String operators) {
			char[] buffer = new char[letters.length()*2];
			int length = 0;
			for (int i = 0; i < letters.length(); i++) {
				buffer[length++] = letters.charAt(i);
				if (i >= operators.length() || operators.charAt(i) == '.') continue;
				buffer[length++] = operators.charAt(i);
			}
			return String.copyValueOf(buffer, 0, length);
		}
		
		@Override
		public void decompose(StringVisitor visitor, String palindrome) {
			/*IntStream
					.rangeClosed(0, (int)Math.pow(2, palindrome.length())-1)
					.mapToObj(i -> NumbersUtil.convertToBase(i, 2, palindrome.length))
					.map(base2 -> base2.stream().map(i -> i == 0 ? palindrome.charAt(i) : palindrome.charAt(i)+"|").collect(Collectors.joining("")));*/
			int digits = palindrome.length();
			int from = 0;
			int to = (int)Math.pow(2, digits)-1;
			IntStream
					.rangeClosed(from, to)
					.mapToObj(i -> NumberUtils.convertToBase(i, 2, digits))
					.map(base2 -> base2.stream().map(i -> i == 0 ? "." : "|").collect(Collectors.joining("")))
					.map(operators -> join(palindrome,operators))
					.filter(string -> isPalindrome(string))
					.forEach(string -> visitor.visit(string));
		}
	}
	
	public static class RecursiveDecomposer implements Decomposer {
		
		private static void decompose(StringVisitor visitor, String palindrome, char[] buffer, int read, int write, int lastDecomposition) {
			if (read == palindrome.length() && buffer[write-1] == '|') {
				visitor.visit(String.copyValueOf(buffer, 0, write));
				return ;
			}
			
			if (write > 0 && buffer[write-1] != '|' && isPalindrome(buffer,lastDecomposition,write-1)) {
				buffer[write] = '|';
				decompose(visitor,palindrome,buffer,read,write+1,write+1);
			}
			
			if (read < palindrome.length()) {
				buffer[write] = palindrome.charAt(read);
				decompose(visitor,palindrome,buffer,read+1,write+1,lastDecomposition);
			}
			return;
		}

		@Override
		public void decompose(StringVisitor visitor, String palindrome) {
			decompose(visitor, palindrome, new char[palindrome.length()*2],0,0,0);
		}
	}
	private static boolean isPalindrome(String palindrome, int left, int right) {
		for (int i = left, j = right; i < j; i++, j--) {
			if (palindrome.charAt(i) != palindrome.charAt(j)) return false;
		}
		return true;
	}
	private static boolean isPalindrome(char[] palindrome, int left, int right) {
		for (int i = left, j = right; i < j; i++, j--) {
			if (palindrome[i] != palindrome[j]) return false;
		}
		return true;
	}
	
}
