package org.ip.sort;

import java.util.Comparator;

public class SelectorMid<T> implements Selector<T>{

	@Override
	public int select(T[] array, int left, int right, Comparator<T> comparator) {
		return (right - left)/2 + left;
	}

}
