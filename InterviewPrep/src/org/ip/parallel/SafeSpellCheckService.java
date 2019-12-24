package org.ip.parallel;

import java.util.LinkedHashMap;
import java.util.Map;

// EPI 2018: 19.1
public class SafeSpellCheckService {
	public static void main(String[] s) {
		
	}
	final public Object mutex = new Object();
	// No Collections.synchronizedLinkedHashMap
	private Map<String, ICacheEntry> cachedClosestStrings = new LinkedHashMap<String, ICacheEntry>(){
		protected boolean removeEldestEntry(Map.Entry<String, ICacheEntry> eldest) {
			synchronized (mutex) {
				return size() > 3;
			}
		}
	};
	public void service(ServiceRequest req, ServiceResponse resp) {
		String s = req.extract();
		ICacheEntry entry;
		synchronized(mutex) {
			entry = cachedClosestStrings.computeIfAbsent(s, k -> new CacheEntryEmpty(req));
		}
		if (entry instanceof CacheEntryFilled) {
			resp.reply(((CacheEntryFilled)entry).get());
		} else if (entry instanceof CacheEntryEmpty) {
			if (((CacheEntryEmpty) entry).req == req) { // we created CacheEntryEmpty, let's fill it.
				String[] closest = Spell.closest(s);
				synchronized(mutex) {
					cachedClosestStrings.put(s, new CacheEntryFilled(closest));
				}
			} // somebody else created CacheEntryEmpty, let them update the cache
		} // error
	}
	public static class Spell {
		public static String[] closest(String s) {return null;}
	}
	public interface ICacheEntry {
		
	}
	public static class CacheEntryFilled implements ICacheEntry {
		private String[] s;
		public CacheEntryFilled(String[] s) {this.s=s;}
		public String[] get() {return s;}
	}
	public static class CacheEntryEmpty implements ICacheEntry {
		private ServiceRequest req;
		public CacheEntryEmpty(ServiceRequest req) {
			this.req=req;
		}
	}
	public static class ServiceRequest {
		public String extract() {return null;}
	}
	public static class ServiceResponse {
		public void reply(String[] s) {}
	}
}
