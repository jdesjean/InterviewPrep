package org.ip.string;

import org.ip.array.Utils;

public class WordOrder {
	public static void main(String[] s) {
		/*
		 * “I will do it.”
		 * “it. do will I”.
		 */
		char[] buffer = "I will do it.".toCharArray();
		reverse(buffer);
		System.out.println(buffer);
		
		buffer = "  will do ".toCharArray();
		reverse(buffer);
		System.out.println(buffer);
	}
	public static void reverse(char[] buffer) {
		Utils.reverse(buffer);
		for (int i = 0, j = 0; i < buffer.length; i++) {
			if (buffer[i] == ' ' || i == buffer.length-1) {
				Utils.reverse(buffer,j,i-1);
				j = i+1;
			}
		}
	}
}
