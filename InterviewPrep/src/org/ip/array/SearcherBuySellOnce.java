package org.ip.array;

// EPI 6.6
public class SearcherBuySellOnce implements Searcher{
	public static void main(String[] s) {
		int[] array = new int[] {310,315,275,295,260,270,290,230,255,250};
		Searcher search = new SearcherBuySellOnce();
		System.out.println(search.search(array, 0, array.length - 1));
	}
	@Override
	public int search(int[] array, int left, int right) {
		int maxDiff = 0;
		int min = array[left];
		for (int i = left + 1; i <= right; i++) {
			maxDiff = Math.max(array[i] - min, maxDiff);
			min = Math.min(array[i], min);
		}
		return maxDiff;
	}

}
