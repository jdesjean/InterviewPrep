package org.ip.sort;

import java.util.Iterator;

//EPI: 11.2
//Time: O(nlog(k)), Space: O(k)
public class IteratorSortedIncreasingDecreasing implements IteratorInt{
	public static void main(String[] s) {
		//Output: 57,131,190,221,294,339,418,442,452,493
		for (IteratorInt iterator = new IteratorSortedIncreasingDecreasing(new int[]{57,131,493,294,221,339,418,452,442,190}, 3); iterator.hasNext();) {
			System.out.print(iterator.next());
			if (iterator.hasNext()) System.out.print(",");
		}
	}
	private Heap<ArrayReference> heap;
	private static int size(int[] ka, int k) {
		int count = 0;
		boolean increasing = true;
		for (int i = 0; i < ka.length;) {
			i += increasing ? k : k-1;
			count++;
			increasing = !increasing;
		}
		return count;
	}
	private static Heap<ArrayReference> init(int[] ka, int k) {
		Heap<ArrayReference> heap = Heap.createMin(size(ka,k));
		boolean increasing = true;
		for (int i = 0; i < ka.length;) {
			int start = increasing ? i : Math.min(i+k-2, ka.length-1);
			int end = increasing ? Math.min(i+k-1, ka.length-1) : i;
			ArrayReference reference = new ArrayReference(ka,start,end);
			if (reference.hasNext()) heap.add(reference.next());
			i += increasing ? k : k-1;
			increasing = !increasing;
		}
		return heap;
	}
	public IteratorSortedIncreasingDecreasing(int[] ka, int k) {
		this.heap = init(ka,k);
	}

	@Override
	public boolean hasNext() {
		return !heap.isEmpty();
	}

	@Override
	public int next() {
		ArrayReference current = heap.remove();
		int nCurrent = current.current();
		if (current.hasNext()) {
			heap.add(current.next());
		}
		return nCurrent;
	}
	public static class ArrayReference implements Comparable<ArrayReference>, Iterator<ArrayReference>{
		public final int[] ka;
		public final int start;
		public final int end;
		public final int direction;
		private int j;
		public ArrayReference(int[] ka, int start, int end) {
			this.ka = ka;
			this.start=start;
			this.end=end;
			this.direction = start <= end ? 1 : -1;
			j = start-direction;
		}
		public boolean hasNext() {
			return j != end;
		}
		public ArrayReference next() {
			j+=((hasNext() ? 1 : 0)*direction);
			return this; 
		}
		public int current() {return ka[j];}
		@Override
		public int compareTo(ArrayReference o) {
			if (o == null) return -1;
			return current() - o.current();
		}
	}
}
