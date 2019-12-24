package org.ip;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

// Dropbox 0: max-1
// 
public class Allocator {
	public static void main(String[] s) {
		Allocator allocator = new Allocator(2);
		System.out.println(allocator.alloc());
		allocator.release(0);
		System.out.println(allocator.alloc());
		System.out.println(allocator.alloc());
	}
	Map<Integer,BitSet> released = new HashMap<Integer, BitSet>();
	private int max;
	private int current = -1;
	public Allocator(int max) {
		this.max=max;
	}
	public int alloc() {
		if (!released.isEmpty()) {
			Integer n = released.keySet().iterator().next();
			int value = remove(released.get(n)) + n * 64;
			if (released.get(n).isEmpty()) {
				released.remove(n);
			}
			return value;
		}
		if (current >= max - 1) {
			throw new RuntimeException("No ids to alloc");
		}
		return ++current;
	}
	public void release(int id) {
		if (id > current) {
			throw new RuntimeException("Releasing unreleased id");
		}
		BitSet set = released.get(id / 64);
		if (set == null) {
			set = new BitSet();
			released.put(id / 64, set);
		}
		set.set(id % 64);
	}
	private int remove(BitSet released) {
		int bit = released.nextSetBit(0);
		released.clear(bit);
		return bit;
	}
}
