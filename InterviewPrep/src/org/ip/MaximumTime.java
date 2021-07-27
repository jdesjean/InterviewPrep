package org.ip;

/**
 * <a href="https://leetcode.com/discuss/interview-question/396769/">OA 2019</a>
 */
public class MaximumTime {
	public static void main(String[] s) {
		MaximumTime mt = new MaximumTime();
		tc1(mt);
		tc2(mt);
		tc3(mt);
		tc4(mt);
		tc5(mt);
	}
	public static void tc1(MaximumTime mt) {
		System.out.println(mt.solve("?4:5?"));
	}
	public static void tc2(MaximumTime mt) {
		System.out.println(mt.solve("23:5?"));
	}
	public static void tc3(MaximumTime mt) {
		System.out.println(mt.solve("2?:22"));
	}
	public static void tc4(MaximumTime mt) {
		System.out.println(mt.solve("0?:??"));
	}
	public static void tc5(MaximumTime mt) {
		System.out.println(mt.solve("??:??"));
	}
	public String solve(String s) {
		// 0: hour, 1: minute, 2: tens of seconds, 3: seconds
		char[] res = new char[5];
		res[2] = ':';
		res[0] = s.charAt(0) == '?' ? s.charAt(1) == '?' || s.charAt(1) <= '3' ? '2' : '1' : s.charAt(0);
		res[1] = s.charAt(1) == '?' ? s.charAt(0) == '?' || s.charAt(0) == '2' ? '3' : '9' : s.charAt(1);
		res[3] = s.charAt(3) == '?' ? '5' : s.charAt(3);
		res[4] = s.charAt(4) == '?' ? '9' : s.charAt(4);
		return String.copyValueOf(res);
	}
}
