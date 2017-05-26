package org.ip.array;

//EPI: 12.3
//Time: O(log(n)), Space: O(1)
public class SearcherMinimumSortedRotated implements Searcher{
	public static void main(String[] s) {
		testMinimumRotated();
	}
	public static void testMinimumRotated() {
		Searcher search = new SearcherMinimumSortedRotated();
		int left = 0;
		int right = 5;
		System.out.println(search.search(new int[]{5,6,1,2,3,4},left,right));
		System.out.println(search.search(new int[]{1,2,3,4,5,6},left,right));
		System.out.println(search.search(new int[]{2,3,4,5,6,1},left,right));
		System.out.println(search.search(new int[]{4,4,1,2,4,4},left,right));
		System.out.println(search.search(new int[]{4,1,2,3,4,4},left,right));
	}
	
	@Override
	public int search(int[] array, int left, int right) {
		return minimumRotated(array,left,right);
	}
	
	public static int minimumRotated(int[] rotated, int i, int j) {
		for (;i < j;) {
			int mid = (j-i)/2+i;
			if (rotated[mid] > rotated[j]) {
				i = mid + 1;
			} else if (rotated[mid] < rotated[j]) {
				j = mid;
			} else {
				int k1 = minimumRotated(rotated,i,mid);
				int k2 = minimumRotated(rotated,mid+1,j);
				if (k1 == -1) return k2;
				else if (k2 == -1) return k1;
				else if (rotated[k1] <= rotated[k2]) return k1;
				else return k2;
			}
		}
		return i;
	}

}
