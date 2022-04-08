package org.ip.sort;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

import org.ip.Test;
import org.ip.array.Utils;

/**
 * <a href=
 * "https://blog.reverberate.org/2020/05/29/hoares-rebuttal-bubble-sorts-comeback.html">Hoare’s
 * Rebuttal and Bubble Sort’s Comeback</a>
 */
public class QuickSort {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] { new int[] { -9, -7, -6, -5, -4, -1, 1, 2, 3 },
				new int[] { 2, 3, -4, -9, -1, -7, 1, -5, -6 } };
		Object[][] tcs = new Object[][] { tc1 };
		Problem[] solvers = new Problem[] { new Sort(new Hoare()), new Sort(new Lomuto()),
				new Sort(new LomutoBranchless()), new Sort(new LomutoBranchlessParallelizable(9)) };
		Test.apply(solvers, tcs);
	}

	static class LomutoBranchlessParallelizable implements Partition {
		private int scratchSize;
		private ArrayPtr scratchPtr;

		public LomutoBranchlessParallelizable(int scratchSize) {
			this.scratchSize = scratchSize;
			scratchPtr = new ArrayPtr(new int[scratchSize], new IntegerHolder(scratchSize - 1));
		}

		@Override
		public int accept(int[] a, int left, int right, int pivot) {
			int p = distributeForward(a, left, right, pivot);
			// To complete the partition we need to copy the elements in scratch
			// to the end of the array.
			System.arraycopy(scratchPtr.a, scratchSize - p, a, right - p + 1, p);
			return right - p + 1;
		}

		int distributeForward(int[] a, int left, int right, int pivot) {
			int p = a[pivot];
			int offset = 0;
			Utils.swap(a, pivot, right);
			IntegerHolder i = new IntegerHolder(left);
			for (ArrayPtr aPtr = new ArrayPtr(a, i); i.v < right; i.v++) {
				int val = aPtr.get();
				boolean is_larger = val > p;
				ArrayPtr dst = is_larger ? scratchPtr : aPtr;
				dst.set(offset, val);
				offset -= is_larger ? 1 : 0;
			}
			Utils.swap(a, right + offset, right);
			return -offset;
		}
		static class IntegerHolder {
			int v;
			public IntegerHolder(int v) {
				this.v = v;
			}
		}
		static class ArrayPtr {
			private int[] a;
			private IntegerHolder index;

			public ArrayPtr(int[] a, IntegerHolder index) {
				this.index = index;
				this.a = a;
			}
			
			public int get() {
				return a[index.v];
			}
			
			public void set(int offset, int value) {
				a[index.v + offset] = value;
			}
		}
	}

	static class LomutoBranchless implements Partition {

		@Override
		public int accept(int[] a, int left, int right, int pivot) {
			int j = left;
			int p = a[pivot];
			Utils.swap(a, pivot, right);
			for (int i = left; i < right; i++) {
				Utils.swap(a, j, i);
				j += a[j] < p ? 1 : 0;
			}
			Utils.swap(a, j, right);
			return j;
		}

	}

	static class Lomuto implements Partition {

		@Override
		public int accept(int[] a, int left, int right, int pivot) {
			int j = left;
			int p = a[pivot];
			Utils.swap(a, pivot, right);
			for (int i = left; i < right; i++) {
				if (a[i] < p) {
					Utils.swap(a, j, i);
					j++;
				}
			}
			Utils.swap(a, j, right);
			return j;
		}

	}

	static class Hoare implements Partition {

		@Override
		public int accept(int[] a, int left, int right, int pivot) {
			int p = a[pivot];
			while (left < right) {
				while (left < right && a[left] < p)
					left++;
				if (left == right)
					break;
				while (left < right && a[right] > p)
					right--;
				if (left == right)
					break;
				Utils.swap(a, left, right);
			}
			return left;
		}

	}

	static class Sort implements Problem {
		private Partition partition;

		public Sort(Partition partition) {
			this.partition = partition;
		}

		@Override
		public void accept(int[] t) {
			quickSort(t, 0, t.length - 1);
		}

		void quickSort(int[] a, int left, int right) {
			if (left >= right)
				return;
			int pivot = left + (right - left) / 2; // Important but not focus here
			int p = partition.accept(a, left, right, pivot); // The main work loop
			quickSort(a, left, p - 1);
			quickSort(a, p + 1, right); // Tail call, ideally the largest sub-interval
		}

	}

	interface Partition {
		int accept(int[] a, int left, int right, int pivot);
	}

	interface Problem extends Consumer<int[]> {

	}
}
