package org.ip.array;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

//EPI 2018: 5.9
public class Prime {
	public static void main(String[] s) {
		List<Integer> list = new ArrayList<Integer>();
		Prime prime = new Prime();
		prime.generate(list, 30);
		System.out.println(list.toString());
	}
	public void generate(List<Integer> results, int size) {
		BitSet set = new BitSet();
		for (int i = 3; i < size; i+= 2) {
			if (!set.get(i)) {
				results.add(i);
			}
			for (int j = i * i; j < size; j+=i) {
				set.set(j);
			}
		}
	}
}
