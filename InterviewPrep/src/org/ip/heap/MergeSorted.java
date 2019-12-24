package org.ip.heap;

import java.util.Comparator;
import java.util.PriorityQueue;

// EPI 2018: 10.1
public class MergeSorted {
	public static void main(String[] s) {
		int[][] sorted = new int[][] {{3,5,7},{0,6},{0,6,28}};
		new MergeSorted().merge(new PrintVisitor(), sorted);
	}
	public void merge(PrintVisitor visitor, int[][] sorted) {
		PriorityQueue<Pair> heap = new PriorityQueue<Pair>(4, new PairComparator(sorted));
		for (int i = 0; i < sorted.length; i++) {
			heap.add(new Pair(i, 0));
		}
		while (!heap.isEmpty()) {
			Pair pair = heap.remove();
			visitor.visit(sorted[pair.file][pair.index++]);
			if (pair.index < sorted[pair.file].length) {
				heap.add(pair);
			}
		}
	}
	public static class PairComparator implements Comparator<Pair> {
		private int[][] sorted;
		public PairComparator(int[][] sorted) {
			this.sorted=sorted;
		}
		@Override
		public int compare(Pair o1, Pair o2) {
			return sorted[o1.file][o1.index] - sorted[o2.file][o2.index];
		}
	}
	public static class Pair {
		int index;
		int file;
		public Pair(int file, int index) {this.file=file;this.index=index;}
	}
	public static class PrintVisitor {
		public void visit(int value) {
			System.out.print(String.valueOf(value) + ",");
		}
	}
}
