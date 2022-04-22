package org.ip.graph;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiFunction;

import org.ip.Test;

/**
 * <a href="https://leetcode.com/problems/web-crawler-multithreaded/">LC: 1242</a>
 */
public class WebCrawlerMultithreaded {
	public static void main(String[] s) {
		Object[] tc1 = new Object[] {Arrays.asList(new String[] {"http://news.yahoo.com",
				  "http://news.yahoo.com/news",
				  "http://news.yahoo.com/news/topics/",
				  "http://news.yahoo.com/us"}), "http://news.yahoo.com/news/topics/", new TestCase1()};
		Object[] tc2 = new Object[] {Arrays.asList(new String[] {"http://news.google.com"}), "http://news.google.com", new TestCase2()};
		Object[][] tcs = new Object[][] {tc1, tc2};
		Problem[] solvers = new Problem[] { new Solver() };
		Test.apply(solvers, tcs);
	}
	static class Solver implements Problem {

		ReentrantLock lock = new ReentrantLock();
		Condition empty = lock.newCondition();
		ExecutorService executor = Executors.newFixedThreadPool(4);
		Set<String> visited = ConcurrentHashMap.newKeySet();
		
		@Override
		public List<String> apply(String t, HtmlParser u) {
			Count count = new Count(1);
			visited.add(t);
			executor.execute(new ProcessLinks(t, domain(t), u, count));
			try {
				count.awaitEmpty();
				executor.shutdown();
			} catch (InterruptedException e) {
				throw new RuntimeException("Failed to awaitEmpty", e);
			}
			return visited.stream().toList();
		}
		String domain(String t) {
			try {
				URI uri = new URI(t);
				return uri.getHost();
			} catch (URISyntaxException e) {
				throw new RuntimeException("Failed to parse domain " + t, e);
			}
		}
		
		class ProcessLinks implements Runnable {
			private HtmlParser parser;
			private String url;
			private String domain;
			private Count count;
			public ProcessLinks(String t, String domain, HtmlParser u, Count count) {
				this.url = t;
				this.domain = domain;
				this.parser = u;
				this.count = count;
			}
			@Override
			public void run() {
				List<String> nexts = parser.getUrls(url);
				int size = 0;
				if (nexts != null) {
					List<String> n = nexts.stream()
							.filter(next -> domain.compareToIgnoreCase(domain(next)) == 0)
							.filter(next -> visited.add(next))
							.toList();
					size += n.size();
					n.stream()
						.map(next -> new ProcessLinks(next, domain, parser, count))
						.forEach(executor::execute);
				}
				count.incr(size - 1);
			}
			
		}
		static class Count {
			ReentrantLock lock = new ReentrantLock();
			Condition empty = lock.newCondition();
			int count;
			public Count(int count) {
				this.count = count;
			}
			public void incr(int size) {
				try {
					lock.lock();
					count += size;
					if (count == 0) {
						empty.signalAll();
					}
				} finally {
					lock.unlock();
				}
			}
			public void awaitEmpty() throws InterruptedException {
				try {
					lock.lock();
					empty.await();
				} finally {
					lock.unlock();
				}
			}
		}
		
	}
	interface Problem extends BiFunction<String, HtmlParser, List<String>> {
		
	}
	interface HtmlParser {
		List<String> getUrls(String url);
	}
	static class TestCase1 implements HtmlParser {

		private final Map<String, List<String>> map = new HashMap<>();
		public TestCase1() {
			map.put("http://news.yahoo.com/news/topics/", Arrays.asList(new String[] {"http://news.yahoo.com/", "http://news.yahoo.com/news/"}));
			map.put("http://news.yahoo.com/", Arrays.asList(new String[] {"http://news.yahoo.com/us"}));
			map.put("http://news.google.com/", Arrays.asList(new String[] {"http://news.yahoo.com/news/topics", "http://news.yahoo.com/news"}));
		}

		@Override
		public List<String> getUrls(String url) {
			return map.get(url);
		}
		
	}
	static class TestCase2 implements HtmlParser {

		private final Map<String, List<String>> map = new HashMap<>();
		public TestCase2() {
			map.put("http://news.yahoo.com/news/topics/", Arrays.asList(new String[] {"http://news.yahoo.com/news/"}));
			map.put("http://news.yahoo.com/", Arrays.asList(new String[] {"http://news.yahoo.com/topics"}));
			map.put("http://news.google.com/", Arrays.asList(new String[] {"http://news.yahoo.com/news/topics", "http://news.yahoo.com/news", "http://news.yahoo.com/news"}));
		}

		@Override
		public List<String> getUrls(String url) {
			return map.get(url);
		}
		
	}
}
