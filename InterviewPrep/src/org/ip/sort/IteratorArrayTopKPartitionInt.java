package org.ip.sort;

public class IteratorArrayTopKPartitionInt implements IteratorInt{
	private final static PartitionInt partition = new PartitionDutchFlagInt();
	private final static SelectorInt selector = new SelectorMidInt();
	private int k;
	private int[] array;

	public IteratorArrayTopKPartitionInt(int[] array, int k) {
		for (int left = 0, right = array.length-1; left < right;) {
			int pivotIndex = partition.partition(array, left, right, selector.select(array, left, right));
			int size = array.length-pivotIndex;
			
			if (size < k) right = pivotIndex-1;
			else if (size > k) left = pivotIndex+1;
			else break;
		}
		this.k = array.length-k;
		this.array = array;
	}
	
	@Override
	public boolean hasNext() {
		return k < array.length;
	}

	@Override
	public int next() {
		return array[k++];
	}
}
