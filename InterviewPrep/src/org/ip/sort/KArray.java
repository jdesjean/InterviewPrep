package org.ip.sort;

import java.util.Arrays;
import java.util.Iterator;

public class KArray {
	public static void main(String[] s) {
		//Output: 0 1 2 3 4 5 6 7 8 9 10 11
		System.out.println(Arrays.toString(merge(new int[][]{ {1, 3, 5, 7},
			{2, 4, 6, 8},
			{0, 9, 10, 11}})));
	}
	private static Heap<KArrayReference> init(int[][] ka) {
		Heap<KArrayReference> heap = Heap.createMin(ka.length);
		for (int i = 0; i < ka.length; i++) {
			KArrayReference reference = new KArrayReference(ka,i);
			if (reference.hasNext()) heap.add(reference.next());
		}
		return heap;
	}
	private static int size(int[][] ka) {
		int size = 0;
		for (int i = 0; i < ka.length; i++) {
			size+=ka[i].length;
		}
		return size;
	}
	public static int[] merge(int[][] ka) {
		Heap<KArrayReference> heap = init(ka);
		int[] merged = new int[size(ka)];
		for (int i = 0; !heap.isEmpty(); i++) {
			KArrayReference current = heap.remove();
			merged[i] = current.current();
			if (current.hasNext()) {
				heap.add(current.next());
			}
		}
		return merged;
	}
	public static class KArrayReference implements Comparable<KArrayReference>, Iterator<KArrayReference>{
		public final int[][] ka;
		public final int i;
		public int j = -1;
		public KArrayReference(int[][] ka, int i) {
			this.ka = ka;
			this.i=i;
		}
		public boolean hasNext() {
			return j < ka[i].length - 1;
		}
		public KArrayReference next() {
			j++;
			return this; 
		}
		public int current() {return ka[i][j];}
		@Override
		public int compareTo(KArrayReference o) {
			if (o == null) return -1;
			return current() - o.current();
		}
	}
}
