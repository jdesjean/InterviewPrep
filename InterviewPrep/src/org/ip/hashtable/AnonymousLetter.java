package org.ip.hashtable;

// EPI 2018: 12.2
public class AnonymousLetter {
	public static void main(String[] s) {
		AnonymousLetter letter = new AnonymousLetter();
		System.out.println(letter.canMake("some letter", "some magazine"));
		System.out.println(letter.canMake("letter", "tel ret"));
	}
	private boolean canMake(String magazine, String letter) {
		int[] distribution1 = new int[26];
		int[] distribution2 = new int[26];
		distribution(magazine, distribution1);
		distribution(letter, distribution2);
		return contains(distribution1, distribution2);
	}
	private boolean contains(int[] a, int[] b) {
		for (int i = 0; i < b.length; i++) {
			if (a[i] < b[i]) return false;
		}
		return true;
	}
	private void distribution (String text, int[] distribution) {
		for (int i = 0; i < text.length(); i++) {
			int diff = text.charAt(i) - 'a';
			if (diff < 0) continue; // space
			distribution[diff]++;
		}
	}
}
