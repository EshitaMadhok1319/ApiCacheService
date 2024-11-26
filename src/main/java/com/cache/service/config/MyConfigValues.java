package com.cache.service.config;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.cache.service.Dao.Employees;

/*
 * This class has the constant values
 * 
 * Developer: Eshita Madhok
 * 
 * Modified to implement Least Recently Used (LRU) cache eviction strategy.
 */

@Component
public class MyConfigValues {

	@Value("${cache.maxCacheSize}")
	private int maxCacheSize;

	private Logger LOGGER = LoggerFactory.getLogger(MyConfigValues.class);

	// Using internalcache to track the LRU order
	private Map<String, Employees> internalCache = new ConcurrentHashMap<>();

	// Using accessqueue to keep track of the insertion order
	private LinkedHashMap<String, Long> accessqueue = new LinkedHashMap<>(); // EmployeeId, accessTime

	public int getMaxCacheSize() {
		return maxCacheSize;
	}

	public void setMaxCacheSize(int maxCacheSize) {
		this.maxCacheSize = maxCacheSize;
	}

	public Map<String, Employees> getInternalCache() {
		return internalCache;
	}

	public void setInternalCache(Map<String, Employees> internalCache) {
		this.internalCache = internalCache;
	}

	public LinkedHashMap<String, Long> getAccessQueue() {
		return accessqueue;
	}

	public void setAccessQueue(LinkedHashMap<String, Long> accessqueue) {
		this.accessqueue = accessqueue;
	}

}
