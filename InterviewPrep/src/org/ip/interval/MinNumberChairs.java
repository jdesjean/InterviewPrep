package org.ip.interval;

import java.util.Arrays;

/**
 * OA 2019
 * <a href="https://leetcode.com/discuss/interview-question/356520">OA 2019</a>
 * <br>Related: <a href="https://leetcode.com/problems/meeting-rooms-ii/">LC: 253</a>
 * 
 */
public class MinNumberChairs {
/**
S 1, 2, 6, 5, 3
E 5, 5, 7, 6, 8
  0, 1, 2, 3, 4
1 1    1          -1        -1         -1         -1
1 2    3          2         2          1          0
0 0,1  0,1,4      0,1,3,4   0,1,2,3,4  0,1,2,3,4  0,1,2,3,4
                  0,1       0,1,3      0,1,2,3    0,1,2,3,4
1, 2,  3,      4, 5,        6,         7,         8


1) Naive - O(n^2), O(1)
Compare every interval to check for overlap
keep track of max overlap

2) L = max(S, E), O(nlog(n)), O(1)
sort both arrays
for each k in S
while E(k_e) >= k_s++
max = max(max, k_e - k_s)
1, 2, 3, 5, 6
5, 5, 6, 7, 8
1, 2, 3, [-1, -2, 5], [-3], 6

3) counting sort - O(n + k), O(k) where k is max(a) - min(a)
set +1 for each k on S
set -1 for each k on S
sum and keep track of max

4) Sort concat - L = S + E, O(Llog(L)), O(L)
Make S as (S, 1) & E as (E, -1)
Concat S & E

 */
	public static void main(String[] s) {
		tc1();
		tc2();
	}
	public static void tc1() {
		//S = [1, 2, 6, 5, 3], E = [5, 5, 7, 6, 8]
		//3
		System.out.println(new MinNumberChairs().solve(new int[] {1, 2, 6, 5, 3}, new int[] {5, 5, 7, 6, 8}));
	}
	public static void tc2() {
		//[[0,30],[5,10],[15,20]]
		//2
		System.out.println(new MinNumberChairs().solve(new int[] {0, 5, 15}, new int[] {30, 10, 20}));
	}
	public int solve(int[] s, int[] e) {
		Arrays.sort(s);
		Arrays.sort(e);
		int max = 0;
		for (int i = 0, j = 0; i < s.length; i++) {
			while (s[i] >= e[j]) {
				j++;
			}
			max = Math.max(max, i - j + 1);
		}
		return max;
	}
}
