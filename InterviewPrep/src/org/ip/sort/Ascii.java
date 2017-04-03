package org.ip.sort;

public class Ascii {
	public static void main(String[] s) {
		char[] array = "qporuienbbbjh787345tqwrecvnm".toCharArray();
		sort(array);
		System.out.println(array);
	}
	private static int[] buckets(char[] a) {
		int[] bucket = new int[256];
		for (int i = 0; i < a.length; i++) {
			bucket[a[i]]++;
		}
		return bucket;
	}
	public static void sort(char[] a) {
		int[] buckets = buckets(a);
		for (int i = 0, j = 0; i < a.length; ) {
			if (buckets[j] <= 0) j++;
			else {
				a[i++] = (char)j;
				buckets[j]--;
			}
		}
	}
}
