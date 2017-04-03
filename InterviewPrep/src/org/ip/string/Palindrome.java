package org.ip.string;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;

import org.ip.Visitors.StringVisitor;

public class Palindrome {
	public static void main(String[] s) {
		testRotation();
	}
	public static void testRotation() {
		System.out.println(isRotation("aab"));
		System.out.println(isRotation("aba"));
		System.out.println(isRotation("baa"));
		System.out.println(isRotation("abc"));
	}
	public static void testDecomposer() {
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
		Decomposer[] decomposers = new Decomposer[]{new IterativeDecomposer(),new IterativeFilteredDecomposer(),new RecursiveDecomposer()};
		for (int i = 0; i < decomposers.length; i++) {
			decomposers[i].decompose(visitor, "abracadabra");
			System.out.println("");
		}
	}
	
	public interface Decomposer {
		public void decompose(StringVisitor visitor, String palindrome);
	}
	
	//Naive IterativeDecomposer. 
	public static class IterativeDecomposer implements Decomposer {

		protected static int isPalindrome(String palindrome, char[] operators) {
			for (int i = 0, j = 0; i < palindrome.length(); i++) {
				if (operators[i] == '|' && j != i) {
					int jj = operators[j] == '|'  ? j+1 : j;
					int ii = operators[i] == '|'  ? i : i;
					if (!Palindrome.isPalindrome(palindrome,jj,ii)) return i;
					j = i;
				} else if (i == palindrome.length()-1 && j != i) return i;
			}
			return palindrome.length();
		}
		protected static String join(String letters, char[] operators) {
			char[] buffer = new char[letters.length()*2];
			int length = 0;
			for (int i = 0; i < letters.length(); i++) {
				buffer[length++] = letters.charAt(i);
				if (i >= operators.length || operators[i] == '.') continue;
				buffer[length++] = operators[i];
			}
			return String.copyValueOf(buffer, 0, length);
		}
		protected static char[] toOperators(char[] buffer, int current) {
			for (int i = buffer.length-1, value = current; i >= 0; i--, value>>=1) {
				buffer[i] = (value & 1) == 0 ? '.' : '|';
			}
			return buffer;
		}
		@Override
		public void decompose(StringVisitor visitor, String palindrome) {
			int digits = palindrome.length();
			int from = 0;
			int to = (int)Math.pow(2, digits)-1;
			char[] buffer = new char[digits];
			IntStream
					.rangeClosed(from, to)
					.filter(current -> isPalindrome(palindrome,IterativeDecomposer.toOperators(buffer,current)) == palindrome.length())
					.forEach(string -> visitor.visit(join(palindrome,buffer)));
		}
	}
	//Still not as efficient as RecursiveDecomposer, but much better than Naive iterative
	public static class IterativeFilteredDecomposer implements Decomposer {
		
		private static class PalindromeSupplier implements IntSupplier {
			private int digits;
			private int max;
			private int current;
			private String palindrome;
			private char[] buffer;

			public PalindromeSupplier(String palindrome, int digits) {
				this.palindrome=palindrome;
				this.digits = digits;
				this.max = (int)Math.pow(2, digits)-1;
				this.current = this.max;
				this.buffer = new char[digits];
			}
			
			@Override
			public int getAsInt() {
				while (current > 0) {
					IterativeDecomposer.toOperators(buffer,current);
					int index = IterativeDecomposer.isPalindrome(palindrome,buffer);
					if (index == palindrome.length()) return current--;
					current-=Math.pow(2, digits-index-1);
				}
				return current;
				//return current--;
			}
			
		}
		
		@Override
		public void decompose(StringVisitor visitor, String palindrome) {
			int digits = palindrome.length();
			IntSupplier supplier = new PalindromeSupplier(palindrome,digits);
			int current = 0;
			char[] buffer = new char[digits];
			while ((current = supplier.getAsInt()) > 0) {
				IterativeDecomposer.toOperators(buffer,current);
				visitor.visit(IterativeDecomposer.join(palindrome,buffer));
			}
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
	public static int previous(int n, int length) {
		n--;
		return n < 0 ? length+n : n;
	}
	public static int next(int n, int length) {
		return ++n % length;
	}
	public static boolean isPalindrome(String palindrome, int rotation) {
		int l = palindrome.length();
		for (int i = rotation, j = previous(rotation,l), k = 0; k < l;k++,i=next(i,l),j=previous(j,l)) {
			if (palindrome.charAt(i) != palindrome.charAt(j)) return false;
		}
		return true;
	}
	public static boolean isRotation(String palindrome) {
		for (int i = 0; i < palindrome.length(); i++) {
			if (isPalindrome(palindrome,i)) return true;
		}
		return false;
	}
	public static boolean isPalindrome(String palindrome) {
		return isPalindrome(palindrome,0,palindrome.length()-1);
	}
	public static boolean isPalindrome(String palindrome, int left, int right) {
		for (int i = left, j = right; i < j; i++, j--) {
			if (palindrome.charAt(i) != palindrome.charAt(j)) return false;
		}
		return true;
	}
	public static boolean isPalindrome(char[] palindrome, int left, int right) {
		for (int i = left, j = right; i < j; i++, j--) {
			if (palindrome[i] != palindrome[j]) return false;
		}
		return true;
	}
	
}
