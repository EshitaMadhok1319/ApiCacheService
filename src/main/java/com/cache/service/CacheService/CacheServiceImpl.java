package com.cache.service.CacheService;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.service.Dao.Employees;
import com.cache.service.Exception.CacheServiceExceptionHandler;
import com.cache.service.Service.impl.EmployeesServiceImpl;
import com.cache.service.config.MyConfigValues;

/*
 * This class contains the caching logic.
 * 
 * Developer: Eshita Madhok
 * 
 * Modified to use LRU eviction strategy from MyConfigValues.
 */

@Service
public class CacheServiceImpl {

    @Autowired
    MyConfigValues myConfigValues;
    
    @Autowired
    EmployeesServiceImpl employeesServiceImpl;
    
    @Autowired
    CacheServiceExceptionHandler cacheServiceExceptionHandler;
    private long accessTime = 0; // For tracking access times to simulate LRU eviction

    private Logger LOGGER = LoggerFactory.getLogger(CacheServiceImpl.class);

    public CacheServiceImpl() {
    }

    public void add(Employees e) {
        try {
            myConfigValues.getInternalCache().put(e.getEmployeeID(), e);
            LOGGER.info("Added to cache: {}", e.getEmployeeID());
            updateAccessQueue(e.getEmployeeID());
            if (myConfigValues.getInternalCache().size() > myConfigValues.getMaxCacheSize()) {
            	LOGGER.info("Checking the EmployeeID that needs to be evicted ");
            evictIfNeeded();
            }
            employeesServiceImpl.add(e);
            LOGGER.info("Added to DB: {}", e.getEmployeeID());
        } catch (Exception ex) {
            cacheServiceExceptionHandler.handleException("add", ex);
        }
    }

    public void remove(String employeeId) {
        try {
            LOGGER.debug("Removing from cache: {}", employeeId);
            myConfigValues.getInternalCache().remove(employeeId);
            myConfigValues.getAccessQueue().remove(employeeId);

            LOGGER.debug("Removing from DB: {}", employeeId);
            employeesServiceImpl.remove(employeeId);

            LOGGER.info("Entity removed from cache and database: {}", employeeId);
        } catch (Exception ex) {
            cacheServiceExceptionHandler.handleException("Error removing entity from cache", ex);
        }
    }

    public void removeAll() {
        try {
            LOGGER.info("Removing all from cache");
            myConfigValues.getInternalCache().clear();
            myConfigValues.getAccessQueue().clear();
            LOGGER.info("Removing all from DB");
            employeesServiceImpl.removeAll();
        } catch (Exception ex) {
            cacheServiceExceptionHandler.handleException("removeAll from Cache and DB", ex);
        }
    }

    public Employees get(String employeeId) {
        try {
            if (myConfigValues.getInternalCache().containsKey(employeeId)) {
                LOGGER.info("Entity fetched from cache: {}", employeeId);
                updateAccessQueue(employeeId);
                return myConfigValues.getInternalCache().get(employeeId);
            }

            Employees fetchedEntity = employeesServiceImpl.getEmployees(employeeId);
            LOGGER.info("Fetched the data from DB: {}", fetchedEntity.getEmployeeID());
            if (fetchedEntity != null) {
                LOGGER.info("Caching the data: {}", fetchedEntity.getEmployeeID());
                myConfigValues.getInternalCache().put(employeeId, fetchedEntity);
                updateAccessQueue(employeeId);
                
                if (myConfigValues.getInternalCache().size() > myConfigValues.getMaxCacheSize()) {
                evictIfNeeded();
                }
                return fetchedEntity;
            }
            return null;
        } catch (Exception ex) {
            cacheServiceExceptionHandler.handleException("get", ex);
            return null;
        }
    }

    public void clear() {
        try {
            myConfigValues.getInternalCache().clear();
            myConfigValues.getAccessQueue().clear();
            LOGGER.info("Cache cleared, database unaffected.");
        } catch (Exception ex) {
            cacheServiceExceptionHandler.handleException("clear", ex);
        }
    }
    
    public synchronized void evictIfNeeded() {
        String lruKey = null;
        long oldestTime = Long.MAX_VALUE; //this holds the maximum value a Long can hold 9223372036854775807

        for (Map.Entry<String, Long> entry : myConfigValues.getAccessQueue().entrySet()) {
            if (entry.getValue() < oldestTime) {
                oldestTime = entry.getValue(); 
                lruKey = entry.getKey();
            }
        }

        if (lruKey != null) {
        	myConfigValues.getInternalCache().remove(lruKey);
        	myConfigValues.getAccessQueue().remove(lruKey);
            LOGGER.info("Evicted the LRU entry: {}", lruKey);
        }
}


public synchronized void updateAccessQueue(String key) {
    accessTime++; // Increment access time for every access
    myConfigValues.getAccessQueue().put(key, accessTime);
}
}
