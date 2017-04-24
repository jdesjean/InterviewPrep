package org.ip.sort;

import org.ip.primitives.MathUtils;

public class SorterIntro implements Sorter{
	private final static SelectorInt selection = new SelectorMidInt();
	private final static PartitionInt partition = new PartitionDutchFlagInt();
	private final static Sorter sorter = new SorterHeap();
	@Override
	public void sort(int[] array, int left, int right) {
		int length = right - left + 1;
		int maxDepth = MathUtils.log2(length)*2;
		sort(array,left,right,maxDepth);
	}
	private void sort(int[] array, int left, int right, int maxDepth) {
		if (left >= right) return;
		else if (maxDepth == 0) {
			sorter.sort(array, left, right);
		} else {
			int pivotIndex = selection.select(array, left, right);
			pivotIndex = partition.partition(array, left, right, pivotIndex);
			sort(array,left,pivotIndex-1,maxDepth-1);
			sort(array,pivotIndex+1,right,maxDepth-1);
		}
	}
	
	
}
