package org.ip.string;

// EPI: 7.6
public class WordReverser {
	public static void main(String[] s) {
		WordReverser reverser = new WordReverser();
		char[] array = "Alice likes Bob".toCharArray();
		reverser.reverse(array);
		org.ip.array.Utils.print(array, array.length);
	}
	public void reverse(char[] s) {
		org.ip.array.Utils.reverse(s, 0, s.length - 1);
		int j = 0;
		for (int i = 0; i < s.length; i++) {
			if (s[i] == ' ') {
				org.ip.array.Utils.reverse(s, j, i - 1);
				j = i + 1;
			}
		}
		org.ip.array.Utils.reverse(s, j, s.length - 1);
	}
}
