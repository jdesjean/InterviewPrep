package org.ip.honor;

import java.util.Map;

// EPI 2018: 24.8
public class Justify {
	public static void main(String[] s) {
		String ss = "The quick brown fox jumped over the lazy dogs.";
		System.out.println(new Justify().solve(ss, 11));
	}
	public String solve(String ss, int l) {
		String[] w = ss.split(" ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0, cl = 0, j = 0; i < w.length; i++) {
			if (cl == 0) {
				cl+=w[i].length();
				if (cl > l) {
					// error
				}
			} else {
				int ncl = cl + w[i].length() + 1;
				if (ncl > l) {
 					sb.append(toString(w, j, i - 1, cl, l));
					j = i;
					cl = w[i].length();
					if (cl > l) {
						//error
					}
				} else {
					cl = ncl;
				}
			}
			if (i == w.length - 1) {
				sb.append(toString(w, j, i, cl, l));
			}
		}
		return sb.toString();
	}
	public String toString(String[] w, int i, int j, int cl, int l) {
		StringBuilder sb = new StringBuilder();
		int spaces = j - i;
		if (spaces == 0) {
			sb.append(w[i]);
			appendSpace(sb, l - cl);
			return sb.toString();
		}
		int dl = l - cl;
		int dspace = dl / spaces;
		int espace = dl - (dspace * spaces);
		for (int left = i; left <= j; left++) {
			sb.append(w[left]);
			if (left < j || i == j) {
				appendSpace(sb, dspace + 1);
				if (espace > 0) {
					appendSpace(sb, 1);
					espace--;
				}
			}
		}
		sb.append('\n');
		return sb.toString();
	}
	public void appendSpace(StringBuilder sb, int spaces) {
		for (int i = 0;  i < spaces; i++) {
			sb.append(' ');
		}
	}
}
