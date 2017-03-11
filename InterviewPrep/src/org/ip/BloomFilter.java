package org.ip;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class BloomFilter<K> {
	public static void main(String[] s) {
		List<String> list = Arrays.asList(new String[]{"cabal", "cabala", "cabaletta", "cabalism", "cabalist", "cabalistic"});
		BloomFilter<String> filter;
		try {
			filter = new BloomFilter<String>(list.size(),0.005, MessageDigest.getInstance("MD5"));
			for (String string : list) {
				filter.put(string);
			}
			System.out.println(filter.get("cat"));
			System.out.println(filter.get("bat"));
			System.out.println(filter.get("test"));
			System.out.println(filter.get("dog"));
			System.out.println(filter.get("cabal"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	public enum BloomFilterResult {
		NotPresent, PossiblyPresent
	}
	private class PutVisitor implements Visitor{
		@Override
		public boolean visit(int k) {
			bitset.set(k);
			return true;
		}
	}
	private class GetVisitor implements Visitor {
		@Override
		public boolean visit(int k) {
			return bitset.get(k);
		}
		
	}
	private final int k;
	private final int m;
	private final BitSet bitset;
	private final Visitor PUT_VISITOR = new PutVisitor();
	private final Visitor GET_VISITOR = new GetVisitor();
	private MessageDigest digest;
	public interface Hashable<K> {
		public int hash(K k);
	}
	public BloomFilter(int n, double p, MessageDigest digest) {
		double log2 = Math.log(2);
		this.m = (int)-Math.ceil(n*Math.log(p)/Math.pow(log2, 2));
		this.k = (int)Math.ceil(log2*this.m/n);
		bitset = new BitSet(this.m);
		this.digest = digest;
	}
	public static final byte[] intToByteArray(int value) {
	    return new byte[] {
	            (byte)(value >>> 24),
	            (byte)(value >>> 16),
	            (byte)(value >>> 8),
	            (byte)value};
	}
	public interface Visitor {
		public boolean visit(int k);
	}
	public boolean hash(K key, Visitor visitor) {
		int h = key.hashCode();
		for (int i = 0; i < k; i++) {
			digest.update(intToByteArray(i));
			ByteBuffer wrapped = ByteBuffer.wrap(digest.digest(intToByteArray(h)));
			int value = Math.abs(wrapped.getInt()) % m;
			if (!visitor.visit(value)) return false; 
		}
		return true;
	}
	public void put(K key) {
		hash(key,PUT_VISITOR);
	}
	public BloomFilterResult get(K key) {
		return hash(key,GET_VISITOR) ? BloomFilterResult.PossiblyPresent : BloomFilterResult.NotPresent;
	}
}

