package org.ip.string;

// EPI: 7.8
public class LookAndSay {
	public static void main(String[] s) {
		LookAndSay look = new LookAndSay();
		int[] current = new int[10];
		int[] next = new int[10];
		int len = 0;
		for (int i = 0; i < 6; i++) {
			len = look.generate(current, len, next);
			int[] tmp = current;
			current = next;
			next = tmp;
		}
		org.ip.array.Utils.println(current, len);
	}
	public int generate(int[] current, int len, int[] next) {
		if (len == 0) {
			next[0] = 1;
			return 1;
		}
		int count = 0;
		int write = 0;
		for (int read = 0; read < len; read++) {
			if (read > 0 && current[read] != current[read - 1]) {
				next[write++] = count;
				next[write++] = current[read - 1];
				count = 1;
			} else {
				count++;
			}
		}
		next[write++] = count;
		next[write++] = current[len - 1];
		len = write;
		return len;
	}
}
