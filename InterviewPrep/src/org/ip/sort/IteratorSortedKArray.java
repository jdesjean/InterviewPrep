package org.ip.sort;

import java.util.Iterator;

//EPI: 11.1
//Time: O(nlog(k)), Space: O(k)
public class IteratorSortedKArray implements IteratorInt{
	public static void main(String[] s) {
		//Output: 0 1 2 3 4 5 6 7 8 9 10 11
		for (IteratorInt iterator = new IteratorSortedKArray(new int[][]{ {1, 3, 5, 7},
			{2, 4, 6, 8},
			{0, 9, 10, 11}}); iterator.hasNext();) {
			System.out.print(iterator.next());
			if (iterator.hasNext()) System.out.print(",");
		}
		
	}
	private Heap<KArrayReference> heap;
	private static Heap<KArrayReference> init(int[][] ka) {
		Heap<KArrayReference> heap = Heap.createMin(ka.length);
		for (int i = 0; i < ka.length; i++) {
			KArrayReference reference = new KArrayReference(ka,i);
			if (reference.hasNext()) heap.add(reference.next());
		}
		return heap;
	}
	public IteratorSortedKArray(int[][] ka) {
		this.heap = init(ka);
	}

	@Override
	public boolean hasNext() {
		return !heap.isEmpty();
	}

	@Override
	public int next() {
		KArrayReference current = heap.remove();
		int nCurrent = current.current();
		if (current.hasNext()) {
			heap.add(current.next());
		}
		return nCurrent;
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
