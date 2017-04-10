package org.ip.sort;

public class SorterQuick implements Sorter{
	private final static SelectorInt selection = new SelectorMidInt();
	private final static PartitionInt partition = new PartitionDutchFlagInt();
	@Override
	public void sort(int[] array, int left, int right) {
		if (left >= right) return;
		int indexPivot = selection.select(array, left, right);
		int indexPartition = partition.partition(array,left,right,indexPivot);
		sort(array,left,indexPartition-1);
		sort(array,indexPartition+1,right);
	}
}
