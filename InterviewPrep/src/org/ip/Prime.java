package org.ip;

import java.util.Arrays;

public class Prime {

    public static void main(String[] args) {
        long first = 0;
        long last = Integer.MAX_VALUE;
        // your code goes here
        System.out.println(generatePrimes(last,first,last));
    }
    public static class BitSet {
        public long value;
        public long[] bits;
        public int wlen;
        public long numBits;
        public BitSet(long numBits) {
            this.numBits = numBits;
            bits = new long[bits2words(numBits)];
            wlen = bits.length;
        }
        public boolean get(long index) {
            int i = (int)(index >> 6);             // div 64
            if (i>=bits.length) return false;
            long bitmask = 1L << index;
            return (bits[i] & bitmask) != 0;
        }
        public long[] getBits() { return bits; }
        public void setAll() {
        	for (int i = 0; i < bits.length; i++) {
        		bits[i] |= ~0L;
        	}
        }
        public void set(long index) {
            int wordNum = expandingWordNum(index);
            long bitmask = 1L << index;
            bits[wordNum] |= bitmask;
        }
        public void clear(long index) {
            int wordNum = (int)(index >> 6); // div 64
            if (wordNum>=wlen) return;
            long bitmask = 1L << index;
            bits[wordNum] &= ~bitmask;
        }
        protected int expandingWordNum(long index) {
            return (int)(index >> 6);
        }
        public static int bits2words(long numBits) {
            return (int)(((numBits-1)>>>6)+1);
        }
    }
    public static BitSet generatePrimes(int n) {
    	long size = (long)Math.floor(0.5 * (n-3))+1;
        BitSet isPrime = new BitSet(size);
        isPrime.setAll();
        for (long i = 0; i < size; i++) {
            if (!isPrime.get(i)) continue;
            long p = ((i*2)+3);
            for (long j = ((i*i) * 2) + 6*i+3; j < size; j+=p) {
                isPrime.clear(j);
            }
        }
        return isPrime;
    }
    public static int generatePrimes(long n, long first, long last) {
        long size = (long)Math.floor(0.5 * (n-3))+1;
        int primes = 0;
        if (first <= 2 && 2 <= last) primes++;
        BitSet isPrime = new BitSet(last);
        for (long i = 0; i < size; i++) {
            isPrime.set(i);
        }
        for (long i = 0; i < size; i++) {
            if (!isPrime.get(i)) continue;
            long p = ((i*2)+3);
            if (first <= p && p <= last && isMegaprime(p)) primes++;
            for (long j = ((i*i) * 2) + 6*i+3; j < size; j+=p) {
                isPrime.clear(j);
            }
        }
        ArrayUtils.toFile(isPrime.getBits(), isPrime.getBits().length);
        return primes;
    }
    public static boolean isMegaprime(long value) {
        for (long i = value; i > 0; i= i / 10L) {
            long digit = i % 10L;
            if (digit != 2 && digit != 3 && digit != 5 && digit != 7) return false;
        }
        return true;
    }
}
