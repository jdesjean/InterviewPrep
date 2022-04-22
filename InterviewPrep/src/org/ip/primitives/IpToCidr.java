package org.ip.primitives;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/ip-to-cidr/">LC: 751</a>
 */
public class IpToCidr {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {new String[] {"255.0.0.7/32","255.0.0.8/29","255.0.0.16/32"}, "255.0.0.7", 10};
		Object[] tc2 = new Object[] {new String[] {"117.145.102.62/31","117.145.102.64/30","117.145.102.68/31"}, "117.145.102.62", 8};
		Object[] tc3 = new Object[] {new String[] {"0.0.0.0/28"}, "0.0.0.0", 16};
		Object[] tc4 = new Object[] {new String[] {"0.0.0.0/28"}, "255.255.255.255", 16};
		Object[][] tcs = new Object[][] {tc1, tc2, tc3, tc4};
		Problem[] solvers = new Problem[] {new Solver()};
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		@Override
		public List<String> apply(String t, Integer u) {
			int range = u;
			long x = toLong(t);
			List<String> res = new ArrayList<>();
			for (long step = range; range > 0; range -= step, x += step) {
				step = x & -x;
				if (step == 0) step = (1 << 62);
				while (step > range) step >>= 1;
				int log2 = log2(step);
				res.add(toString(x) + "/" + (32 - log2));
			}
			return res;
		}
		int log2(long n){
		    return 63 - Long.numberOfLeadingZeros(n);
		}
		long toLong(String t) {
			long v = 0;
			for (String s : t.split("[.]")) {
				v = v << 8 | Integer.parseInt(s);
			}
			return v;
		}
		String toString(long t) {
			StringBuilder sb = new StringBuilder(16);
			for (int i = 3; i >= 0; i--){
	            sb.append(Long.toString(((t >> (i * 8)) & 255)));
	            sb.append(".");
	        }
			sb.setLength(sb.length() - 1);
			return sb.toString();
		}
	}
	interface Problem extends BiFunction<String, Integer, List<String>> {
		
	}
}
