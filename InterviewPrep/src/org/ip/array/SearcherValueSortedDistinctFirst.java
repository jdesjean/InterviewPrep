package org.ip.array;

//EPI: 12.1
//Time: O(log(n)), Space: O(1)
public class SearcherValueSortedDistinctFirst implements SearcherValue{
	public static void main(String[] s) {
		testSearchSortedDistinct();
	}
	public static void testSearchSortedDistinct() {
		int[] array = new int[]{-14,-10,2,108,108,243,285,285,285,401};
		int left = 0;
		int right = array.length-1;
		SearcherValue searcher = new SearcherValueSortedDistinctFirst();
		System.out.println(searcher.search(array,left,right,108));
		System.out.println(searcher.search(array,left,right,285));
		System.out.println(searcher.search(array,left,right,243));
		System.out.println(searcher.search(array,left,right,0));
		System.out.println(searcher.search(array,left,right,244));
	}
	@Override
	public int search(int[] array, int left, int right, int value) {
		int index = -1;
		for (; left <= right; ) {
			int mid = (right-left)/2 + left;
			if (array[mid] == value) { // try left side
				index = mid;
				right = mid - 1;
			} else if (array[mid] < value) {
				left = mid+1;
			} else {
				right = mid -1;
			}
		}
		return index < 0 ? -left : index;
	}

}
