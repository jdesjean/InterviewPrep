package org.ip.sort;

import java.util.ArrayList;
import java.util.List;

// EPI 2018: 13.1
public class IntersectionSorted {
	public static void main(String[] s) {
		int[] a1 = new int[] {2,3,3,5,5,6,7,7,8,12};
		int[] a2 = new int[] {5,5,6,8,8,9,10,10};
		System.out.println(new IntersectionSorted().intersection(a1, a2));
	}
	public List<Integer> intersection(int[] a1, int[] a2) {
		List<Integer> intersection = new ArrayList<Integer>();
		for (int i1 = 0, i2 = 0; i1 < a1.length && i2 < a2.length;) {
			if (a1[i1] < a2[i2]) {
				i1++;
			} else if (a1[i1] > a2[i2]){
				i2++;
			} else {
				if (intersection.isEmpty() || intersection.get(intersection.size() - 1) != a1[i1]) {
					intersection.add(a1[i1]);
				}
				i1++;
				i2++;
			}
		}
		return intersection;
	}
}
