package org.ip.stack;

public class Path2 {
	public static void main(String[] s) {
		System.out.println(shortestEquivalent("/usr/lib/../bin/gcc"));
		System.out.println(shortestEquivalent("scripts//./../scripts/awkscripts/././"));
		System.out.println(shortestEquivalent("/usr/../../"));
	}
}
