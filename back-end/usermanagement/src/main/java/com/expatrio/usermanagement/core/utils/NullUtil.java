package com.expatrio.usermanagement.core.utils;

/**
 * Utility class for null checking and handling
 */
public final class NullUtil {

	/**
	 * Private constructor
	 */
	private NullUtil(){}

	/**
	 * Method to check if object is null 
	 * @param obj - Object
	 * @return
	 */
	public static boolean isNull(Object obj){
		return null == obj;
	}

	/**
	 * Method to check if object is not null 
	 * @param obj - Object
	 * @return
	 */
	public static boolean isNotNull(Object obj){
		return !isNull(obj);
	}

}
