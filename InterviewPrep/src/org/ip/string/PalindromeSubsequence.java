package org.ip.string;

import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ip.string.Visitors.CharSequenceVisitor;

public class PalindromeSubsequence {
	public static void main(String[] s) {
		String string = "abebfa";
		Solver[] solvers = new Solver[]{new PalindromeSubsequenceRecursive(), new PalindromeSubsequenceRecursive2()};
		for (int i = 0; i < solvers.length; i++) {
			solvers[i].solve(string, Visitors.PRINT_CONSOLE_CHAR_SEQUENCE);
			System.out.println("***");
		}
		System.out.println(new PLS().solve(string));
		new PalindromeSubsequenceDP().solve(string);
	}
	public interface Solver {
		public void solve(String string, CharSequenceVisitor visitor);
	}
	//Time: 2^n, Space: n
	public static class PalindromeSubsequenceRecursive implements Solver{
		@Override
		public void solve(String string, CharSequenceVisitor visitor) {
			solve(string.toCharArray(),visitor,new char[string.length()], 0, 0);
		}
		public void solve(char[] input, CharSequenceVisitor visitor, char[] buffer, int read, int write) {
			if (read == input.length) {
				if (Palindrome.isPalindrome(buffer, 0, write-1)) {
					visitor.visit(CharBuffer.wrap(buffer,0,write));
				}
				return;
			}
			solve(input,visitor,buffer,read+1,write);
			buffer[write] = input[read];
			solve(input,visitor,buffer,read+1,write+1);
		}
	}
	//Time: n^2*k where k is # of duplicate pair, Space: n^2
	public static class PalindromeSubsequenceRecursive2 implements Solver {
		@Override
		public void solve(String string, CharSequenceVisitor visitor) {
			char[] array = string.toCharArray();
			char[] buffer = new char[string.length()];
			boolean[][] visited = new boolean[string.length()][string.length()];
			solve(array,visitor,buffer,0,string.length()-1,0,string.length()-1,visited);
		}
		public void solve(char[] input, CharSequenceVisitor visitor, char[] buffer, int readI, int readJ, int writeI, int writeJ, boolean[][] visited) {
			if (readI > readJ || visited[readI][readJ]) {
				return;
			}
			visited[readI][readJ] = true;
			if (readI == readJ) {
				buffer[writeI] = input[readI];
				visitor.visit(new PLSCharSequence(buffer,writeI+1,writeJ));
				return;
			}
			solve(input,visitor,buffer,readI+1,readJ,writeI,writeJ,visited);
			solve(input,visitor,buffer,readI,readJ-1,writeI,writeJ,visited);
			if (input[readI] == input[readJ]) {
				buffer[writeI] = input[readI];
				buffer[writeJ] = input[readJ];
				visitor.visit(new PLSCharSequence(buffer,writeI+1,writeJ-1));
				for (int i = readI+1; i <= readJ-1; i++) {
					for (int j = i; j <= readJ; j++) {
						visited[i][j] = false;
					}
				}
				solve(input,visitor,buffer,readI+1,readJ-1,writeI+1,writeJ-1,visited);
			}
		}
	}
	public static class PLSCharSequence implements CharSequence {
		private char[] buffer;
		private int writeI;
		private int writeJ;

		public PLSCharSequence(char[] buffer, int writeI, int writeJ) {
			this.buffer = buffer;
			this.writeI = writeI;
			this.writeJ = writeJ;
		}
		@Override
		public int length() {
			return writeI + buffer.length-writeJ-1;
		}

		@Override
		public char charAt(int index) {
			if (index < writeI) {
				return buffer[index];
			} else {
				return buffer[writeJ+index+1-writeI];
			}
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	//Time: n^2*k?, Space: n^2
	public static class PalindromeSubsequenceDP {
		public static List<Integer> add(List<Integer> buffer, int i, int j) {
			List<Integer> data = new ArrayList<Integer>(buffer.size()+2);
			data.add(i);
			data.addAll(buffer);
			data.add(j);
			return data;
		}
		public void solve(String string) {
			Set<List<Integer>>[][] L = new HashSet[string.length()][string.length()];
			char[] str = string.toCharArray();
			for (int i = 0; i < str.length; i++) {
				L[i][i] = new HashSet<List<Integer>>(1);
				L[i][i].add(Arrays.asList(new Integer[]{i}));
			}
			
			for (int i = str.length - 2; i >= 0; i--) {
				for (int j = i+1; j < str.length; j++) {
					if (str[j] == str[i] && j == i-1) {
						L[i][i] = new HashSet<List<Integer>>(1);
						L[i][i].add(Arrays.asList(new Integer[]{i,j}));
					}
				    else if (str[j] == str[i]) {
				    	Set<List<Integer>> list = new HashSet<List<Integer>>(L[i+1][j-1].size()*2+1);
				    	for (List<Integer> buffer : L[i+1][j-1]) {
				    		list.add(add(buffer,i,j));
				    	}
				    	list.add(Arrays.asList(new Integer[]{i,j}));
				    	list.addAll(L[i+1][j-1]);
				    	L[i][j] = list;
				    }
				    else {
				    	int max = L[i][j-1].size() + L[i+1][j].size();
				    	Set<List<Integer>> list = new HashSet<List<Integer>>(max);
				    	list.addAll(L[i+1][j]);
				    	list.addAll(L[i][j-1]);
				    	L[i][j] = list;
				    }
				}
			}
			
			for (List<Integer> buffer : L[0][string.length()-1]) {
				for (int i = 0; i < buffer.size(); i++) {
					System.out.print(str[buffer.get(i)]);
				}
				System.out.println();
			}
		}
	}
	
	//Time: n^2, Space: n^2 
	public static class PLS {
		public int solve(String string) {
			int[][] L = new int[string.length()][string.length()];
			char[] str = string.toCharArray();
			for (int i = 0; i < str.length; i++)
			      L[i][i] = 1;
			
			//better for cache locality
			for (int i = str.length - 2; i >= 0; i--) {
				for (int j = i+1; j < str.length; j++) {
					if (str[j] == str[i] && j == i-1)
				      L[i][j] = 2;
				    else if (str[j] == str[i])
				      L[i][j] = L[i+1][j-1] + 2;
				    else
				      L[i][j] = Math.max(L[i][j-1], L[i+1][j]);
				}
			}
			//alternative
			/*for (int j=1; j<str.length; j++)
			{
			  for (int i=j-1; i>=0; i--)
			  {
			    if (str[i] == str[j] && i == j-1)
			      L[i][j] = 2;
			    else if (str[i] == str[j])
			      L[i][j] = L[i+1][j-1] + 2;
			    else
			      L[i][j] = Math.max(L[i][j-1], L[i+1][j]);
			  }
			}*/
			return L[0][string.length()-1];
		}
	}
}
