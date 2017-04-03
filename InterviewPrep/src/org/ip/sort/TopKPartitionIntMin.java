package org.ip.sort;

public class TopKPartitionIntMin implements TopKInt{
	private final static PartitionInt partition = new PartitionDutchFlagInt();
	private final static SelectorInt selector = new SelectorMidInt();

	@Override
	public void solve(int[] array, int k) {
		for (int left = 0, right = array.length-1; left < right;) {
			int pivotIndex = partition.partition(array, left, right, selector.select(array, left, right));
			int size = pivotIndex+1;
			
			if (size < k) left = pivotIndex+1;
			else if (size > k) right = pivotIndex-1;
			else break;
		}
	}
}
