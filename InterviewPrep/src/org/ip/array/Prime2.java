package org.ip.array;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

//EPI 2018: 5.9
public class Prime2 {
	public static void main(String[] s) {
		List<Integer> list = new ArrayList<Integer>();
		Prime2 prime = new Prime2();
		prime.generate(list, 30);
		System.out.println(list.toString());
	}
	public void generate(List<Integer> results, int n) {
		BitSet set = new BitSet();
		int size = (n - 3) / 2; // 2i + 3
		for (int i = 0; i < size; i++) {
			int p = 2*i+3;
			if (!set.get(i)) {
				results.add(p);
			}
			// From Prime i * i is now p * p
			// p * p = 2i + 3
			// 4i^2 + 12i + 9 = 2i + 3
			for (int j = i * i * 2 + 6 * i + 3; j < size; j+=p) {
				set.set(j);
			}
		}
	}
}
