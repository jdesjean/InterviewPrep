package org.ip.sort;

import java.util.Comparator;
import java.util.Iterator;

public class IteratorArrayTopKPartitionMin<T> implements Iterator<T>{
	private final Partition<T> partition = new PartitionDutchFlag<T>();
	private final Selector<T> selector = new SelectorMid<T>();
	private T[] array;
	private int k;
	
	public IteratorArrayTopKPartitionMin(T[] array, int k, Comparator<T> comparator) {
		this.array=array;
		this.k=k-1;
		for (int left = 0, right = array.length-1; left < right;) {
			int pivotIndex = partition.partition(array, left, right, selector.select(array, left, right, comparator), comparator);
			int size = pivotIndex+1;
			
			if (size < k) left = pivotIndex+1;
			else if (size > k) right = pivotIndex-1;
			else break;
		}
	}

	@Override
	public boolean hasNext() {
		return k >= 0;
	}

	@Override
	public T next() {
		return array[k--];
	}
}
