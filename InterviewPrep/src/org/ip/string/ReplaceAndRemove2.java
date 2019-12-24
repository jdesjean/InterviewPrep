package org.ip.string;

import org.ip.array.Utils;

// EPI 2018: 6.4
public class ReplaceAndRemove2 {
	public static void main(String[] s) {
		char[] a = new char[] {'a','c','d','b','b','c','a'};
		new ReplaceAndRemove2().transform(a);
		Utils.println(a);
	}
	public void transform(char[] a) {
		int l = remove(a, 'b');
		replace(a, l, 'a', new char[] {'d','d'});
	}
	public int remove(char[] a, char c) {
		int l = 0;
		for (int i = 0; i < a.length; i++) {
			a[l] = a[i];
			if (a[i] != c) {
				l++;
			}
		}
		return l;
	}
	public void replace(char[] a, int l, char c, char[] cc) {
		for (int i = a.length - 1, j = l - 1; j >= 0; j--) {
			if (a[j] == c) {
				for (int k = 0; k < cc.length; k++) {
					a[i--] = cc[k];
				}
			} else {
				a[i--] = a[j];
			}
		}
	}
}
