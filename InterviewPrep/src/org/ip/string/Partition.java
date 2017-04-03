package org.ip.string;

import org.ip.ArrayUtils;

public class Partition {
	public static void main(String[] s) {
		/*
		 * [0,a,1,9,3,z,b,r,6],
		 * a,z,b,r,0,1,9,3,6
		 */
		char[] buffer = new char[]{'0','a','1','9','3','z','b','r','6'}; 
		partition(buffer);
		System.out.println(buffer);
		
		buffer = new char[]{'a','1','9','3','z','b','r','6'}; 
		partition(buffer);
		System.out.println(buffer);
	}
	public static void partition(char[] buffer) {
		for (int i = 1, j = 0; i < buffer.length; i++) {
			if (Character.isLetter(buffer[j])) i = ++j;
			else if (Character.isLetter(buffer[i])) {
				ArrayUtils.swap(buffer, i, j++);
			}
		}
	}
}
