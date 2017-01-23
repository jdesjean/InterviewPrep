package org.ip;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Palindrome {
	public static void main(String[] s) {
		/*
		 * a|b|r|a|c|a|d|a|b|r|a|
			a|b|r|a|c|ada|b|r|a|
			a|b|r|aca|d|a|b|r|a|
		 */
		System.out.println(new InterativeDecomposer().decompose("abracadabra"));
		System.out.println(new RecursiveDecomposer().decompose("abracadabra"));
	}
	
	public interface Decomposer {
		public List<String> decompose(String palindrome);
	}
	
	//IterativeDecomposer needs some optimization
	//A lot of the generated combination are filtered
	public static class InterativeDecomposer implements Decomposer {

		private static boolean isPalindrome(String palindrome) {
			for (int i = 0, j = 0; i < palindrome.length(); i++) {
				if ((palindrome.charAt(i) == '|' || i == palindrome.length()-1) && j != i) {
					int jj = palindrome.charAt(j) == '|'  ? j+1 : j;
					int ii = palindrome.charAt(i) == '|'  ? i-1 : i;
					if (!Palindrome.isPalindrome(palindrome,jj,ii)) return false;
					j = i;
				}
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
		public List<String> decompose(String palindrome) {
			/*IntStream
					.rangeClosed(0, (int)Math.pow(2, palindrome.length())-1)
					.mapToObj(i -> NumbersUtil.convertToBase(i, 2, palindrome.length))
					.map(base2 -> base2.stream().map(i -> i == 0 ? palindrome.charAt(i) : palindrome.charAt(i)+"|").collect(Collectors.joining("")));*/
			int digits = palindrome.length();
			int from = 0;
			int to = (int)Math.pow(2, digits)-1;
			return IntStream
					.rangeClosed(from, to)
					.mapToObj(i -> NumbersUtil.convertToBase(i, 2, digits))
					.map(base2 -> base2.stream().map(i -> i == 0 ? "." : "|").collect(Collectors.joining("")))
					.map(operators -> join(palindrome,operators))
					.filter(string -> isPalindrome(string))
					.collect(Collectors.toList());
		}
	}
	
	public static class RecursiveDecomposer implements Decomposer {
		
		private static List<String> decompose(List<String> result, String palindrome, char[] buffer, int read, int write, int lastDecomposition) {
			if (read == palindrome.length() && buffer[write-1] == '|') {
				result.add(String.copyValueOf(buffer, 0, write));
				return result;
			}
			
			if (write > 0 && buffer[write-1] != '|' && isPalindrome(buffer,lastDecomposition,write-1)) {
				buffer[write] = '|';
				decompose(result,palindrome,buffer,read,write+1,write+1);
			}
			
			if (read < palindrome.length()) {
				buffer[write] = palindrome.charAt(read);
				decompose(result,palindrome,buffer,read+1,write+1,lastDecomposition);
			}
			return result;
		}

		@Override
		public List<String> decompose(String palindrome) {
			return decompose(new ArrayList<String>(), palindrome, new char[palindrome.length()*2],0,0,0);
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
