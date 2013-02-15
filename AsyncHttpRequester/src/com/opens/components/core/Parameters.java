package com.opens.components.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import org.apache.http.NameValuePair;

/**
 * Class to add the parameters to request
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public final class Parameters { //XXX - Look form com.loopj class parameters
	
	/**
	 * Add or replace new parameter
	 * @param name parameter name 
	 * @param value parameter value
	 */
	public void add(String name, String value) {
		
	}
	
	public void add(NameValuePair param) {
		
	}
	
	public void add(String name, File value) throws FileNotFoundException {
		
	}
	
	public void add(Collection<NameValuePair> copyFrom) {
		
	}
	
	public int length() {
		return 0;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
}