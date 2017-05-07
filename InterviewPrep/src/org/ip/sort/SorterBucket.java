package org.ip.sort;

import java.util.Iterator;
import java.util.LinkedList;

import org.ip.array.Utils;

public class SorterBucket<T> {
	private LinkedList<T>[] buckets;
	public static void main(String[] s) {
		int[] array = new int[]{1,6,8,2,7,3,7,9,4,2,7,0,1,4,7};
		Object[] objs = new Integer[]{1,6,8,2,7,3,7,9,4,2,7,0,1,4,7};
		SorterBucket<Object> sorter = new SorterBucket<Object>(10);
		sorter.sort(array, objs, 0, array.length-1);
		Utils.println(objs, 0, objs.length-1);
	}
	public SorterBucket(int size) {
		buckets = new LinkedList[size];
	}
	public void sort(int[] array, T[] objs, int left, int right) {
		for (int i = left; i <= right; i++) {
			if (buckets[array[i]] == null) buckets[array[i]] = new LinkedList<T>();
			buckets[array[i]].add(objs[i]);
		}
		for (int i = left, j = 0; i <= right; j++) {
			if (buckets[j] == null) continue;
			for (Iterator<T> iterator = buckets[j].iterator(); iterator.hasNext();) {
				objs[i++] = iterator.next(); 
			}
		}
	}
}
