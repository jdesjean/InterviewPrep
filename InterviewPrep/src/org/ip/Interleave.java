package org.ip;

public class Interleave {
	public static void main(String[] s) {
		String[][] tests = new String[6][4];
		tests[0] = new String[]{"1234","123","123","0"};
		tests[1] = new String[]{"112233","123","123","1"};
		tests[2] = new String[]{"123456","123456","","1"};
		tests[3] = new String[]{"123456","","123456","1"};
		tests[4] = new String[]{"12345678","1234","5678", "1"};
		tests[5] = new String[]{"12345678","1233","5678", "0"};
		Interleaver[] interleavers = new Interleaver[]{new RecursiveInterleaver(), new DPInterleaver()};
		for (Interleaver interleaver : interleavers) {
			for (int i = 0; i < tests.length; i++) {
				System.out.println(interleaver.isInterleave(tests[i][0], tests[i][1], tests[i][2]) + "==" + tests[i][3]);
			}
			System.out.println("**");
		}
	}
	public interface Interleaver {
		public boolean isInterleave(String interleave, String s1, String s2);
	}
	public static class RecursiveInterleaver implements Interleaver {

		@Override
		public boolean isInterleave(String interleave, String s1, String s2) {
			return isInterleave(interleave,s1,s2,0,0,0);
		}
		private static boolean isInterleave(String interleave,  String s1, String s2, int nInterleave, int nS1, int nS2) {
			if (nInterleave == interleave.length()) {
				return true;
			}
			if (nS1 < s1.length() && s1.charAt(nS1) == interleave.charAt(nInterleave) && nS2 < s2.length() && s2.charAt(nS2) == interleave.charAt(nInterleave)) {
				return isInterleave(interleave,s1,s2,nInterleave+1,nS1+1,nS2) || isInterleave(interleave,s1,s2,nInterleave+1,nS1,nS2+1);
			} else if (nS1 < s1.length() && s1.charAt(nS1) == interleave.charAt(nInterleave)) {
				return isInterleave(interleave,s1,s2,nInterleave+1,nS1+1,nS2);
			} else if (nS2 < s2.length() && s2.charAt(nS2) == interleave.charAt(nInterleave)) {
				return isInterleave(interleave,s1,s2,nInterleave+1,nS1,nS2+1);
			} else {
				return false;
			}
		}
	}
	public static class DPInterleaver implements Interleaver {

		@Override
		public boolean isInterleave(String interleave, String s1, String s2) {
			boolean[][] cache = new boolean[s1.length()+1][s2.length()+1];
			for (int i = 0; i < s1.length(); i++) {
				cache[i+1][0] = s1.charAt(i) == interleave.charAt(i);
			}
			for (int i = 0; i < s2.length(); i++) {
				cache[0][i+1] = s2.charAt(i) == interleave.charAt(i);
			}
			for (int i = 1; i < s1.length(); i++) {
				for (int j = 1; j < s2.length(); j++) {
					int len = i+j;
					char target = interleave.charAt(len-1);
					cache[i][j] = (cache[i-1][j] || cache[i][j-1]) && (s1.charAt(i) == target || s2.charAt(j) == target);
				}
			}
			return cache[s1.length()][s2.length()];
		}
		
	}
}
