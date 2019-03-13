package org.ip.array;

// EPI 6.6
public class SearcherBuySellTwice implements Searcher{
	public static void main(String[] s) {
		int[] array = new int[] {12,11,13,9,12,8,14,13,15};
		Searcher search = new SearcherBuySellTwice();
		System.out.println(search.search(array, 0, array.length - 1));
		
		int[] array2 = new int[] {1,2,3,4,5,6};
		System.out.println(search.search(array2, 0, array2.length - 1));
	}
	@Override
	public int search(int[] array, int left, int right) {
		int[] forward = new int[right - left + 1];
		int totalMax = 0;
		for (int i = left + 1, min = array[left]; i <= right; i++) {
			totalMax = Math.max(array[i] - min, totalMax);
			min = Math.min(array[i], min);
			forward[i] = totalMax;
		}
		for (int i = right - 1, maxDiff = 0, max = array[right]; i >= 1; i--) {
			maxDiff = Math.max(max - array[i], maxDiff);
			max = Math.max(array[i], max);
			totalMax = Math.max(maxDiff + forward[i - 1], totalMax);
		}
		return totalMax;
	}

}
