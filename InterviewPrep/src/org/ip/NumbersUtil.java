package org.ip;

import java.util.Deque;
import java.util.LinkedList;

public class NumbersUtil {
	public static Deque<Integer> convertToBase(int value, int base, int digits) {
		Deque<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < digits; i++) {
			list.push(value % base);
			value /= base;
		}
		return list;
	}
}
