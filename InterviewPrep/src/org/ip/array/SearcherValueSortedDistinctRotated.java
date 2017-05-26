package org.ip.array;

public class SearcherValueSortedDistinctRotated implements SearcherValue{

	public static void main(String[] s) {
		int[] array = new int[]{-4,-2,0,2,4,6};
		SearcherValue searcher = new SearcherValueSortedDistinctRotated();
		for (int i = 0; i < array.length; i++) {
			Utils.rotate(array,1);
			System.out.println(searcher.search(array, 0, array.length-1, 0));
		}
	}
	
	@Override
	public int search(int[] array, int left, int right, int value) {
		for (;left <= right;) {
			int mid = (right - left) / 2 + left;
			if (array[mid] == value) return mid;
			if (isNotRotated(array,mid,right)) {
				if (isWithin(value,array[mid],array[right])) left = mid + 1;
				else right = mid - 1;
			} else if (isNotRotated(array,left,mid)){
				if (isWithin(value,array[left],array[mid])) right = mid - 1;
				else left = mid + 1;
			} else { //Only runs with duplicates
				int index = search(array,mid+1,right,value);
				if (index >= 0) return index;
				return search(array,left,mid-1,value);
			}
		}
		return -1;
	}
	
	public static boolean isNotRotated(int[] array, int left, int right) {
		return array[left] <= array[right];
	}
	
	public static boolean isWithin(int value, int left, int right) {
		return value >= left && value <= right;
	}
}
