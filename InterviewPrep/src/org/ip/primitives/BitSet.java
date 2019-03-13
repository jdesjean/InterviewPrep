package org.ip.primitives;

public class BitSet {
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
