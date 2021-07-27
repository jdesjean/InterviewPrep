package org.ip;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiPredicate;

/**
 * <a href="https://leetcode.com/problems/logger-rate-limiter/">LC: 359</a>
 */
public class LoggerRateLimiter {
	public static void main(String[] s) {
		Problem logger = new Solver();
		logger.test(1, "foo");  // return true, next allowed timestamp for "foo" is 1 + 10 = 11
		logger.test(2, "bar");  // return true, next allowed timestamp for "bar" is 2 + 10 = 12
		logger.test(3, "foo");  // 3 < 11, return false
		logger.test(8, "bar");  // 8 < 12, return false
		logger.test(10, "foo"); // 10 < 11, return false
		logger.test(11, "foo"); // 11 >= 11, return true, next
		logger.test(12, "foobar"); // 11 >= 11, return true, next
	}
	static class Solver implements Problem {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>(8, 0.75f, true) {
			@Override
		    protected boolean removeEldestEntry(Map.Entry<String, Integer> eldest) {
		        return latest - eldest.getValue() >= 10;
		    }
		};
		Integer latest;
		@Override
		public boolean test(Integer timestamp, String message) {
			Integer prev = map.get(message);
			latest = timestamp;
			boolean canPrint = prev == null || timestamp - prev >= 10;
			if (canPrint) {
				map.put(message, timestamp);
			}
			return canPrint;
		}
		
	}
	interface Problem extends BiPredicate<Integer, String> {
		
	}
}
