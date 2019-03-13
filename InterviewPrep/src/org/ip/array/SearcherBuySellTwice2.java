package org.ip.array;

// EPI 6.6
public class SearcherBuySellTwice2 implements Searcher{
	public static void main(String[] s) {
		int[] array = new int[] {12,11,13,9,12,8,14,13,15};
		Searcher search = new SearcherBuySellTwice2();
		System.out.println(search.search(array, 0, array.length - 1));
		
		int[] array2 = new int[] {1,2,3,4,5,6};
		System.out.println(search.search(array2, 0, array2.length - 1));
	}
	@Override
	public int search(int[] array, int left, int right) {
		int maxDiff = 0;
		int prevMaxDiff = 0;
		boolean swap = false;
		for (int i = left + 1, min = array[left]; i <= right; i++) {
			int diff = array[i] - min;
			if (diff > maxDiff) {
				if (swap) {
					prevMaxDiff = maxDiff;
					swap = false;
				}
				maxDiff = diff;
			}
			if (array[i] < min) {
				min = array[i];
				swap = true;
			}
		}
		
		return maxDiff + prevMaxDiff;
	}

}
