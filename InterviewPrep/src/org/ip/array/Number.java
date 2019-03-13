package org.ip.array;

import java.util.ArrayList;
import java.util.List;

// EPI: 6.2, 6.3
public class Number {
	public static void main(String[] s) {
		Number number1 = new Number(new ArrayList<>(List.of(1, 3, 9)));
		System.out.println(number1.increment().toString());
		
		Number number2 = new Number(new ArrayList<>(List.of(9, 9, 9)));
		System.out.println(number2.increment().toString());
		
		System.out.println(number1.add(number2).toString());
		System.out.println(number1.multiply(number2).toString());
	}
	private List<Integer> digits;
	public Number(List<Integer> digits) {
		this.digits = digits;
	}
	public Number increment() {
		int carry = 1;
		Number result = new Number(new ArrayList<>());
		for (int i = digits.size() - 1; i >= 0; i--) {
			int value = digits.get(i) + carry;
			carry = value / 10;
			value = value % 10;
			result.digits.add(0, value);
		}
		if (carry > 0) {
			result.digits.add(0, carry);
		}
		return result;
	}
	public Number add(Number number) {
		int carry = 0;
		Number result = new Number(new ArrayList<>());
		for (int i1 = number.digits.size() - 1, i2 = digits.size() - 1; i1 >= 0 || i2 >= 0; i1--, i2--) {
			int d1 = i1 >= 0 ? number.digits.get(i1) : 0;
			int d2 = i2 >= 0 ? digits.get(i2) : 0;
			int d3 = carry + d1 + d2;
			int digit = d3 % 10;
			carry = d3 / 10;
			result.digits.add(0, digit);
		}
		if (carry > 0) {
			result.digits.add(0, carry);
		}
		return result;
	}
	public Number multiply(Number number) {
		List<Integer> current;
		List<Integer> other;
		if (digits.size() <= number.digits.size()) {
			current = digits;
			other = number.digits;
		} else {
			current = number.digits;
			other = digits;
		}
		Number result = new Number(new ArrayList<>());
		for (int i = current.size() - 1; i >= 0; i--) {
			int carry = 0;
			Number intermediate = new Number(new ArrayList<>());
			for (int j = i; j < current.size() - 1; j++) {
				intermediate.digits.add(0, 0);
			}
			for (int j = other.size() - 1; j >= 0; j--) {
				int digit = other.get(j) * current.get(i) + carry;
				intermediate.digits.add(0, digit % 10);
				carry = digit / 10;
			}
			if (carry > 0) {
				intermediate.digits.add(0, carry);
			}
			result = result.add(intermediate);
		}
		return result;
	}
	public String toString() {
		return digits.toString();
	}
}
