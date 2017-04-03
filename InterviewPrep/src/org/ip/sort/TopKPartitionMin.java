package org.ip.sort;

import java.util.Comparator;

public class TopKPartitionMin<T> implements TopK<T>{
	private final Partition<T> partition = new PartitionDutchFlag<T>();
	private final Selector<T> selector = new SelectorMid<T>();

	@Override
	public void solve(T[] array, int k, Comparator<T> comparator) {
		for (int left = 0, right = array.length-1; left < right;) {
			int pivotIndex = partition.partition(array, left, right, selector.select(array, left, right, comparator), comparator);
			int size = pivotIndex+1;
			
			if (size < k) left = pivotIndex+1;
			else if (size > k) right = pivotIndex-1;
			else break;
		}
	}
}
