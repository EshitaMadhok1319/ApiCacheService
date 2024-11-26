package com.cache.service.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.cache.service.Dao.Employees;

class MyConfigValuesTest {

	private MyConfigValues myConfigValues;


	@BeforeEach
	void setUp() {
		myConfigValues = new MyConfigValues();

	}

	@Test
	void testGetMaxCacheSize() {
		myConfigValues.setMaxCacheSize(5);
		assertEquals(5, myConfigValues.getMaxCacheSize(), "Max cache size should be 5");
	}

	@Test
	void testSetMaxCacheSize() {
		myConfigValues.setMaxCacheSize(10);
		assertEquals(10, myConfigValues.getMaxCacheSize(), "Max cache size should be 10");
	}

	@Test
	void testGetInternalCache() {
		Map<String, Employees> cache = new LinkedHashMap<>();
		cache.put("E1", new Employees());
		myConfigValues.setInternalCache(cache);
		assertNotNull(myConfigValues.getInternalCache(), "Internal cache should not be null");
		assertEquals(1, myConfigValues.getInternalCache().size(), "Internal cache should contain 1 entry");
	}

	@Test
	void testSetInternalCache() {
		Map<String, Employees> cache = new LinkedHashMap<>();
		cache.put("E1", new Employees());
		myConfigValues.setInternalCache(cache);
		assertNotNull(myConfigValues.getInternalCache(), "Internal cache should not be null");
		assertEquals(1, myConfigValues.getInternalCache().size(), "Internal cache should contain 1 entry");
	}

	@Test
	void testInternalCacheInitiallyEmpty() {
		assertTrue(myConfigValues.getInternalCache().isEmpty(), "Internal cache should be empty initially");
	}

}
