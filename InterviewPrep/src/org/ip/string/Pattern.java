package org.ip.string;

public class Pattern {
	public static void main(String[] s) {
		System.out.println(new KMP("abababca").find("bacbabababca"));
		System.out.println(new KMP("abababca").find("bacbababaabcbab"));
		System.out.println(new RK("abababca").find("bacbabababca"));
		System.out.println(new RK("abababca").find("bacbababaabcbab"));
	}
	public interface Finder {
		public int find(String text);
	}
	public static class RK implements Finder {
		private String pattern;
		private int hash;
		private final static int P = 13;//small prime
		private final static int MOD = 8191;//large prime

		public RK(String pattern) {
			this.pattern = pattern;
			this.hash = hash(pattern,0,pattern.length(),MOD,P);
		}

		@Override
		public int find(String text) {
			if (text.length() < pattern.length()) return -1;
			int hash = hash(text,0,pattern.length(),MOD,P);
			int factor = pow(P,pattern.length()-1,MOD);
			for (int i = pattern.length(), j = 0; i <= text.length(); i++, j++) {
				if (this.hash == hash && equal(text,j,pattern.length(),pattern)) return j;
				if (i < text.length()) {
					hash = rollHash(hash,text,j,i,MOD,P,factor);
				}
			}
			return -1;
		}
		private static int rollHash(int hash, String string, int l, int r, int mod, int p, int factor) {
			hash = (p*(hash-string.charAt(l)*factor)+string.charAt(r))%mod;
			if (hash < 0) hash = (hash + mod);
			return hash;
		}
		private static int hash(String string, int offset, int count, int mod, int p) {
			int hash = 0;
			for(int i = 0, l = Math.min(string.length(), count); i < l; i++) {
				hash = ((hash*p)%mod+string.charAt(i))%mod;
			}
			return hash;
		}
		private static int pow(int root, int power, int mod) {
			int factor = 1;
			for (int i = 0; i < power; i++)
		        factor = (factor*root)%mod;
			
			return factor;
		}
		private boolean equal(String t1, int offset, int count, String t2) {
			for (int i = offset, j = 0; j < count && j < t2.length(); i++, j++) {
				if (t1.charAt(i) != t2.charAt(j)) return false;
			}
			return true;
		}
	}
	public static class KMP implements Finder{
		private String pattern;
		int[] table;
		public KMP(String pattern) {
			this.pattern=pattern;
			table = new int[pattern.length()];
			for (int i = 1; i < pattern.length(); i++) {
				char c1 = pattern.charAt(i);
				char c2 = pattern.charAt(table[i-1]);
				table[i] = c1 == c2 ? table[i-1]+1 : 0;
			}
		}
		@Override
		public int find(String text) {
			for (int i = 0, l = 0, j=0; i+pattern.length()-l <= text.length();) {
				if (pattern.charAt(l) == text.charAt(i)) {
					if (l == 0) j = i;
					l++;
					i++;
					if (l == pattern.length()) return j;
				} else {
					j = i = l > 0 ? j+Math.max(l-table[l-1], 1) : j+1;
					l = 0;
				}
			}
			return -1;
		}
		
	}
}
