package org.ip.leetcode;

// leetcode 4
public class MedianTwoSortedArrays {
	public static void main(String[] s) {
		MedianTwoSortedArrays MedianTwoSortedArrays = new MedianTwoSortedArrays();
		System.out.println(MedianTwoSortedArrays.findMedianSortedArrays(new int[] {1,3}, new int[] {2}));
		System.out.println(MedianTwoSortedArrays.findMedianSortedArrays(new int[] {1,3}, new int[] {2,4}));
		System.out.println(MedianTwoSortedArrays.findMedianSortedArrays(new int[] {1,2}, new int[] {3,4}));
		System.out.println(MedianTwoSortedArrays.findMedianSortedArrays(new int[] {1,4}, new int[] {2,3}));
		System.out.println(MedianTwoSortedArrays.findMedianSortedArrays(new int[] {}, new int[] {2,3}));
		System.out.println(MedianTwoSortedArrays.findMedianSortedArrays(new int[] {3}, new int[] {-2,-1}));
	}
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		if (nums1.length > nums2.length) return findMedianSortedArrays(nums2, nums1);
		if (nums1.length == 0) {
            if(nums2.length == 0) {
                return 0;
            }
            else {
	            if (nums2.length % 2 == 0)
	                return (nums2[nums2.length/2-1]+nums2[nums2.length/2]) / 2.0;
	            else return (nums2[(nums2.length - 1)/2]);
            }
        }
        int l1 = 0, r1 = nums1.length;
        int length = nums1.length + nums2.length + 1;
        int hl = length / 2;
        for (; l1 <= r1; ) {
            int m1 = l1 + (r1 - l1) / 2;
            int m2 = hl - m1;
            
            int min1 = m1 > 0 ? nums1[m1 - 1] : Integer.MIN_VALUE;
            int min2 = m2 > 0 ? nums2[m2 - 1] : Integer.MIN_VALUE;
            int max1 = m1 < nums1.length ? nums1[m1] : Integer.MAX_VALUE;
            int max2 = m2 < nums2.length ? nums2[m2] : Integer.MAX_VALUE;
            if (min1 > max2) {
            	r1 = m1 - 1;
            } else if (min2 > max1) {
            	l1 = m1 + 1;
            } else {
            	int m3 = min1 == Integer.MIN_VALUE ? min2 : min2 == Integer.MIN_VALUE ? min1 : Math.max(min1, min2);
            	if ((nums1.length + nums2.length) % 2 == 1) {
            		return m3;
            	}
            	int m4 = (max1 == Integer.MAX_VALUE) 
                        ? max2 
                        : (max2 == Integer.MAX_VALUE) 
                            ? max1 
                            : Math.min(max1, max2);
            	return (m3 + m4) / 2.0;
            }
        }
        throw new RuntimeException("illegal");
    }
}
