package org.ip.array;

//EPI: 15.7
//Time: O(n), Space: O(1), Comparison: O(3/2n)
public class SearcherMinMaxCompareMin implements SearcherMinMax{
	public static void main(String[] s) {
		SearcherMinMax searcher = new SearcherMinMaxCompareMin();
		PairMinMax pair = new PairMinMax();
		
		int[][] array = new int[][]{
			{3,2,5,1,2,4},
			{3,3,2,2,2,2},
			{3,3,4,4,4,4},
			{3,3,4,4,4,2},
			{3,3,4,4,4,5}
		};
		for (int i = 0; i < array.length; i++) {
			searcher.search(array[i], 0, array[i].length-1, pair);
			System.out.println(pair);
		}
		
	}
	@Override
	public void search(int[] array, int left, int right, PairMinMax point) {
		int prevIsAscending = isAscending(array,left,left+1);
		int nextIsAscending;
		point.min = array[0];
		point.max = array[0];
		for (int i = left+1; i <= right; i++) {
			if (i < right) {
				nextIsAscending = isAscending(array,i,i+1); //1 comparison
				if (isLocalMinima(prevIsAscending,nextIsAscending)) {
					point.min = Math.min(array[i], point.min); //1 comparison
				} else if (isLocalMaxima(prevIsAscending,nextIsAscending)) {
					point.max = Math.max(array[i], point.max); //1 comparison
				}
				prevIsAscending = nextIsAscending;
			} else {
				if (prevIsAscending == 0) { //can be minimum or maximum
					point.min = Math.min(array[i], point.min); //1 comparison
					point.max = Math.max(array[i], point.max); //1 comparison
				} else if (prevIsAscending < 0) {
					point.max = Math.max(array[i], point.max); //1 comparison
				} else {
					point.min = Math.min(array[i], point.min); //1 comparison
				}
			}
		}
	}
	//1 comparison
	public int isAscending(int[] array, int current, int next) {
		return array[current] - array[next];
	}
	public boolean isLocalMinima(int prevIsAscending, int nextIsAscending) {
		return prevIsAscending > 0 && nextIsAscending < 0;
	}
	public boolean isLocalMaxima(int prevIsAscending, int nextIsAscending) {
		return prevIsAscending < 0 && nextIsAscending > 0;
	}

}
