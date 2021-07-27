package org.ip.primitives;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * <a href="https://leetcode.com/problems/valid-number/">LC: 65</a>
 */
public class ValidNumber {
	public static void main(String[] s) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<Object[]> tcs = new ArrayList<>();
		//List<Object[]> tcs = Arrays.asList(tc1, tc2, tc3, tc4);
		String[] valid = new String[] {"2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"};
		String[] invalid = new String[] {"0e", "abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"};
		for (String tc : invalid) {
			tcs.add(new Object[] {false + " " + tc, tc});
		}
		for (String tc : valid) {
			tcs.add(new Object[] {true + " " + tc, tc});
		}
		
		Class<? extends Problem>[] solvers = new Class[] {Solver2.class, Solver.class};
		for (Object[] tc : tcs) {
			System.out.print(tc[0]);
			System.out.println();
			for (Class<? extends Problem> solver : solvers) {
				Problem function = solver.getConstructor(String.class).newInstance(tc[1]);
				System.out.print(function.get());
				System.out.println();
			}
			System.out.println();
		}
	}
	public static class Solver2 implements Problem {
		private String s;
		public Solver2(String s) {
			this.s = s;
		}

		//[\decimal\integer]([eE][\integer])?
	    //\decimal [\+\-]?[\opt1\opt2\opt3]
	    //\opt1 [digit+\.]
	    //\opt2 [digit+\.digit+]
	    //\opt3 [\.digit+]
	    //\integer [\+\-]?digit+
		@Override
		public Boolean get() {
			AtomicInteger i = new AtomicInteger();
			return number(i) && i.get() == s.length();
		}
		
		boolean number(AtomicInteger i) {
			if (!test(this::decimal, i) && !test(this::integerSigned, i)) {
				return false;
			}
			if (eat(i, c -> Character.toLowerCase(c) == 'e')) {
				return integerSigned(i);
			}
			return true;
		}
		
		boolean integerSigned(AtomicInteger i) {
			sign(i);
			return integerUnsigned(i);
		}
		
		boolean integerUnsigned(AtomicInteger i) {
			int j = i.get();
			while (eat(i, c -> Character.isDigit(c))) {
				
			}
			return i.get() > j;
		}
		
		boolean sign(AtomicInteger i) {
			return eat(i, c -> c == '-') || eat(i, c -> c == '+');  
		}
		
		boolean decimal(AtomicInteger i) {
			sign(i); //optional
			boolean hasFirst = integerSigned(i); // optional
			if (!eat(i, c -> c == '.')) {
				return false;
			}
			boolean hasSecond = integerUnsigned(i);
			if (!hasFirst && !hasSecond) {
				return false;
			}
			return true;
		}
		boolean eat(AtomicInteger i, Function<Character, Boolean> acceptor) {
			if (i.get() < s.length()) {
				if (acceptor.apply(s.charAt(i.get()))) {
					i.incrementAndGet();
					return true;
				}
			}
			return false;
		}
		boolean test(Function<AtomicInteger, Boolean> function, AtomicInteger i) {
			AtomicInteger j = new AtomicInteger(i.get());
			if (function.apply(j)) {
				i.set(j.get());
				return true;
			}
			return false;
		}
	}
	public static class Solver implements Problem {
		private String s;
		public Solver(String s) {
			this.s = s;
		}

		//[\decimal\integer]([eE][\integer])?
	    //\decimal [\+\-]?[\opt1\opt2\opt3]
	    //\opt1 [digit+\.]
	    //\opt2 [digit+\.digit+]
	    //\opt3 [\.digit+]
	    //\integer [\+\-]?digit+
		@Override
		public Boolean get() {
			int len = number(0);
			return len == s.length();
		}
		
		int number(int i) {
			int len = decimal(i);
			if (len == 0) {
				len = integer(i);
			}
			if (len == 0) return 0;
			if (Character.toLowerCase(eat(i + len)) == 'e') {
				len++;
				int len2 = integer(i + len);
				if (len2 == 0) return 0;
				len += len2;
			}
			return len;
		}
		
		int integer(int i) {
			int len = 0;
			if (eat(i) == '-') {
				len++;
				i++;
			} else if (eat(i) == '+') {
				len++;
				i++;
			}
			int len2 = _integer(i); 
			return len2 > 0 ? len + len2 : 0;
		}
		
		int _integer(int i) {
			int j = i;
			for (; j < s.length(); j++) {
				if (!Character.isDigit(eat(j))) {
					break;
				}
			}
			return j - i; 
		}
		
		int decimal(int i) {
			int len = 0;
			if (eat(i) == '-') {
				len++;
			} else if (eat(i) == '+') {
				len++;
			}
			if (eat(i + len) == '.') {
				len++;
				int len2 = integer(i + len);
				return len2 > 0 ? len2 + len : 0;
			}
			int len2 = integer(i + len); 
			if (len2 == 0) return 0;
			len += len2;
			if (eat(i + len) != '.') { 
				return 0;
			}
			len++;
			len2 = integer(i + len);
			return len + len2;
		}
		char eat(int i) {
			if (i < s.length()) {
				return s.charAt(i);
			}
			return '\0';
		}
	}
	public interface Problem extends Supplier<Boolean> {
		
	}
}
