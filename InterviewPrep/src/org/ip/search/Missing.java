package org.ip.search;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;

// EPI 2018: 11.9
public class Missing {
	public static void main(String[] s) {
		//10^9 * 4B: 4GB + overhead to store all integers
		//IPv4 address: 2^32/8: ~0.5GB to store using 1b per number
		//Bloom filter ~10-15b * 10^9: 1-2GB
		//Sort nlog(n)
		//We can reduce the number to store by doing multiple pass over data.
		//Look at high bits and find numbers that are missing there.
		//Then we store all low bits.
		//Storage
		
		//by splitting at 16 bits
		//high bits 2^16 * 4B ~262KB
		//low bits 2^16/8 ~8KB
		
		//by splitting at 8 bits
		//high bits 2^8 * 4B ~1KB
		//low bits 2^24/8 ~2MB
		System.out.println(new Missing().sort(Arrays.asList(new Integer[] {0,3,2,1,5,4})));
	}
	public int sort(Iterable<Integer> iterable) {
		int hbits = 16;
		int lbits = (32 - hbits);
		int NUM_BUCKET = 1 << hbits;
		int[] counter = new int[NUM_BUCKET];
		for (Iterator<Integer> it = iterable.iterator(); it.hasNext();) {
			int value = it.next() >>> lbits;
			counter[value]++;
		}
		for (int i = 0; i < counter.length; i++) {
			if (counter[i] < NUM_BUCKET) {
				BitSet set = new BitSet(NUM_BUCKET);
				int mask = (1 << lbits) - 1;
				for (Iterator<Integer> it = iterable.iterator(); it.hasNext(); ) {
					int value = it.next();
					int hvalue = value >>> lbits;
					int lvalue = value & mask;
					if (hvalue == i) {
						set.set(lvalue);
					}
				}
				for (int j = 0; j < NUM_BUCKET; j++) {
					if (!set.get(j)) {
						return i << 16 | j;
					}
				}
			}
		}
		return -1;
	}
}
