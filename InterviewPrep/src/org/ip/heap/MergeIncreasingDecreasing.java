package org.ip.heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

// EPI 2018: 10.2
public class MergeIncreasingDecreasing {
	public static void main(String[] s) {
		int[] array = new int[] {57,131,493,294,221,339,418,452,442,190};
		new MergeIncreasingDecreasing().merge(new PrintVisitor(), array);
	}
	public void merge(PrintVisitor visitor, int[] array) {
		List<Pair> list = new ArrayList<Pair>();
		for (int i = 1, prev = 0; i < array.length; i++) {
			boolean isLast = i + 1 == array.length;
			boolean isMaximum = array[i] > array[i - 1] && (isLast || array[i] > array[i + 1]);
			boolean isMinimum = array[i] < array[i - 1] && (isLast || array[i] < array[i + 1]);
			if (isMaximum) {
				list.add(new Pair(prev, i));
				prev = i + 1;
			} else if (isMinimum) {
				list.add(new Pair(i, prev));
				prev = i + 1;
			}
		}
		PriorityQueue<Pair2> heap = new PriorityQueue<Pair2>(4, new PairComparator(array, list));
		for (int i = 0; i < list.size(); i++) {
			heap.add(new Pair2(i, 0));
		}
		while (!heap.isEmpty()) {
			Pair2 pair = heap.remove();
			int inc = list.get(pair.list).start < list.get(pair.list).end ? 1 : -1;
			int index = list.get(pair.list).start + pair.index;
			visitor.visit(array[index]);
			pair.index+=inc;
			index+=inc;
			if (inc > 0 && index <= list.get(pair.list).end) {
				heap.add(pair);
			} else if (inc < 0 && index >= list.get(pair.list).end) {
				heap.add(pair);
			}
		}
	}
	public static class PairComparator implements Comparator<Pair2> {
		private int[] array;
		private List<Pair> list;

		public PairComparator(int[] array, List<Pair> list) {
			this.array=array;
			this.list=list;
		}

		@Override
		public int compare(Pair2 o1, Pair2 o2) {
			int i1 = list.get(o1.list).start + o1.index;
			int i2 = list.get(o2.list).start + o2.index;
			return array[i1] - array[i2];
		} 
		
	}
	public static class Pair {
		int start;
		int end;
		public Pair(int start, int end) {this.start=start;this.end=end;}
	}
	public static class Pair2 {
		int list;
		int index;
		public Pair2(int list, int index) {this.list=list;this.index=index;}
	}
	public static class PrintVisitor { 
		public void visit(int value) {
			System.out.print(String.valueOf(value) + ",");
		}
	}
}
