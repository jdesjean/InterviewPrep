package org.ip.array;

//EPI: 12.2
//Time: O(log(n)), Space: O(1)
public class SearcherSortedDistinctIndex implements Searcher{
	public static void main(String[] s) {
		testSearchSortedDistinct();
	}
	public static void testSearchSortedDistinct() {
		Searcher searcher = new SearcherSortedDistinctIndex();
		int left = 0;
		int right = 6;
		System.out.println(searcher.search(new int[]{-2,0,2,3,5,6,7},left,right));
		System.out.println(searcher.search(new int[]{-2,0,1,2,5,6,7},left,right));
		System.out.println(searcher.search(new int[]{-2,0,2,2,4,6,7},left,right));
		System.out.println(searcher.search(new int[]{-2,0,2,4,5,6,7},left,right));
	}
	@Override
	public int search(int[] array, int left, int right) {
		for (; left <= right; ) {
			int mid = (right - left)/2 + left;
			if (array[mid] == mid) return mid;
			else if (array[mid] < mid) {
				left = mid+1;
			} else {
				right = mid-1;
			}
		}
		return -1;
	}

}
