package com.cache.service.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * This class have the Exception Handling
 * 
 * Developer: Eshita Madhok
 * 
 */

@ControllerAdvice
public class CacheServiceExceptionHandler {

	private static Logger LOGGER = LoggerFactory.getLogger(CacheServiceExceptionHandler.class);

	@ExceptionHandler
	public static void handleException(String methodName, Exception ex) {
		LOGGER.info("Error in method " + methodName + ": " + ex.getMessage());
		ex.printStackTrace();
	}
}
