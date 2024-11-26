package com.cache.service.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cache.service.CacheService.CacheServiceImpl;
import com.cache.service.Dao.Employees;
import com.cache.service.Service.EmployeesService;

class ControllerApiServiceTest {

    @Mock
    private EmployeesService employeesService;

    @Mock
    private CacheServiceImpl cachingService;

    private ControllerApiService controllerApiService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controllerApiService = new ControllerApiService(cachingService);
        controllerApiService.employeesService = employeesService;
    }

    @Test
    void testAddEntity() {
    	Employees employee = new Employees("1", "Eshita", "Delhi", "1221");

        doNothing().when(cachingService).add(employee);

        String result = controllerApiService.addEntity(employee);
        assertEquals("Entity added successfully!", result);
    }

    @Test
    void testRemoveEntity() {
        doNothing().when(cachingService).remove("1");

        controllerApiService.removeEntity("1");
        verify(cachingService, times(1)).remove("1");
    }

    @Test
    void testGetEntity() {
    	Employees employee = new Employees("1", "Eshita", "Delhi", "1221");

        when(cachingService.get("1")).thenReturn(employee);
        when(employeesService.getEmployees("1")).thenReturn(employee);

        Employees result = controllerApiService.getEntity("1");
        assertNotNull(result);
        assertEquals("1", result.getEmployeeID());
    }

    @Test
    void testClearCache() {
        doNothing().when(cachingService).clear();

        controllerApiService.clearCache();
        verify(cachingService, times(1)).clear();
    }
}
