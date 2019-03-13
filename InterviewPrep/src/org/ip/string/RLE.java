package org.ip.string;

import org.ip.array.Utils;

// EPI: 7.12
public class RLE {
	public static void main(String[] s) {
		RLE rle = new RLE();
		char[] current = rle.encode("aaaabcccaa");
		Utils.println(current, current.length);
		char[] output = rle.decode(current);
		Utils.println(output, output.length);
		System.out.println();
	}
	private int countDigit(int value) {
		int len = 0;
		while (value > 0) {
			len++;
			value /= 10;
		}
		return len;
	}
	private int countEncoded(String current) {
		int count = 0;
		int len = 0;
		for (int i = 0; i < current.length() - 1; i++) {
			count++;
			if (current.charAt(i) != current.charAt(i + 1)) {
				len += countDigit(count) + 1;
				count = 0;
			}
		}
		count++;
		len += countDigit(count) + 1;
		return len;
	}
	private int countDecoded(char[] buffer) {
		int len = 0;
		if (buffer.length == 0) return len;
		int count = 0;
		for (int i = 0; i < buffer.length; i++) {
			if (Character.isDigit(buffer[i])) {
				count *= 10;
				count += Character.digit(buffer[i], 10);
			} else {
				len += count;
				count = 0;
			}
		}
		return len;
	}
	private int output(char[] buffer, int pos, int value) {
		int end= pos;
		for (int i = 0; value > 0; i++) {
			end = pos + i;
			buffer[end] = Character.forDigit(value % 10, 10);
			value /= 10;
		}
		org.ip.array.Utils.reverse(buffer, pos, end);
		return end - pos + 1;
	}
	public char[] encode(String current) {
		if (current.length() == 0) return new char[0];
		char[] buffer = new char[countEncoded(current)];
		int count = 0;
		int j = 0;
		for (int i = 0; i < current.length() - 1; i++) {
			count++;
			if (current.charAt(i) != current.charAt(i + 1)) {
				j += output(buffer, j, count);
				buffer[j++] = current.charAt(i);
				count = 0;
			}
		}
		count++;
		j += output(buffer, j, count);
		buffer[j++] = current.charAt(current.length() - 1);
		return buffer;
	}
	public char[] decode(char[] current) {
		char[] target = new char[countDecoded(current)];
		int count = 0;
		for (int i = 0, k = 0; i < current.length; i++) {
			if (Character.isDigit(current[i])) {
				count *= 10;
				count += Character.digit(current[i], 10);
			} else {
				for (int j = 0; j < count; j++) {
					target[k++] = current[i];
				}
				count = 0;
			}
		}
		return target;
	}
}
