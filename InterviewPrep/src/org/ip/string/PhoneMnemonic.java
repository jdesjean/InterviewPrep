package org.ip.string;

import org.ip.array.Utils;

// EPI 2018: 6.7
public class PhoneMnemonic {
	public static final char[][] PHONE_MAP = new char[][] {{' '}, {' '}, {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m','n','o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}};
	public static void main(String[] s) {
		//char[] a = "2276696".toCharArray();
		int[] a = new int[] {9,9,9,9,9,9,9,9};
		Visitor visitor = new NoopVisitor();
		//Visitor visitor = new PrintVisitor();
		long t1 = System.nanoTime();
		new Recursive().solve(visitor, a);
		long t2 = System.nanoTime();
		new Iterative().solve(visitor, a); // About 50% slower
		long t3 = System.nanoTime();
		new Iterative2().solve(visitor, a); // About 15% slower
		long t4 = System.nanoTime();
		new Iterative3().solve(visitor, a); // About 35% faster
		long t5 = System.nanoTime();
		long t3t2 = t3 - t2;
		long t2t1 = t2 - t1;
		long t4t3 = t4 - t3;
		long t5t4 = t5 - t4;
		System.out.println(t5t4 + ":" + t4t3 + ":" + t3t2 + ":" + t2t1);
	}
	public static class Iterative3 {
		public void solve(Visitor visitor, int[] a) {
			int[] write = new int[a.length];
			char[] mnemonic = new char[a.length];
			for (int i = 0; i < mnemonic.length; i++) {
				mnemonic[i] = PHONE_MAP[a[i]][0];
			}
			while (true) {
				int carry = 1;
				int i = a.length - 1;
				for (; carry > 0 && i >= 0; i--) {
					write[i] += carry;
					if (write[i] < PHONE_MAP[a[i]].length) {
						carry = 0;
					} else {
						carry = 1;
						write[i] = 0;
					}
				}
				if (i < 0) break;
				for (int j = i + 1; j < a.length; j++) {
					mnemonic[j] = PHONE_MAP[a[j]][write[j]];
				}
				visitor.visit(mnemonic);
			}
		}
	}
	public static class Iterative2 {
		public void solve(Visitor visitor, int[] a) {
			int[] write = new int[a.length];
			char[] mnemonic = new char[a.length];
			for (int depth = 0; depth >= 0;) {
				if (write[depth] < PHONE_MAP[a[depth]].length) {
					mnemonic[depth] = PHONE_MAP[a[depth]][write[depth]];
					if (depth < a.length - 1) {
						depth++;
					} else {
						visitor.visit(mnemonic);
						write[depth]++;
					}
				} else {
					while (depth >= 0 && write[depth] >= PHONE_MAP[a[write[depth]]].length) {
						write[depth--] = 0;
					}
					if (depth >= 0) {
						write[depth]++;
					}
				}
			}
		}
	}
	public static class Iterative {
		public void solve(Visitor visitor, int[] a) {
			char[] buffer = new char[a.length];
			int n = (int)Math.pow(4, a.length);
			for (int i = 0; i < n; i++) {
				int j = i;
				int l = a.length-1;
				boolean skip = false;
				while (j > 0) {
					int mod = j % 4;
					if (mod < PHONE_MAP[a[l]].length) {
						buffer[l] = PHONE_MAP[a[l]][mod];
						l--;
					} else {
						skip = true;
						break;
					}
					j /= 4;
				}
				if (skip) continue;
				for (; l >= 0; l--) {
					buffer[l] = PHONE_MAP[a[l]][0];
				}
				visitor.visit(buffer);
			}
		}
	}
	public static class Recursive {
		public void solve(Visitor visitor, int[] a) {
			char[] buffer = new char[a.length];
			solve(visitor,a,buffer,0);
		}
		public void solve(Visitor visitor, int[] a, char[] buffer, int i) {
			if (i == a.length) {
				visitor.visit(buffer);
				return;
			}
			for (int j = 0; j < PHONE_MAP[a[i]].length; j++) {
				buffer[i] = PHONE_MAP[a[i]][j];
				solve(visitor,a,buffer,i + 1);
			}
		}
	}
	public static class PrintVisitor implements Visitor {
		@Override
		public void visit(char[] a) {
			Utils.println(a);
		}
	}
	public static class NoopVisitor implements Visitor {

		@Override
		public void visit(char[] a) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public interface Visitor {
		public void visit(char[] a);
	}
}
