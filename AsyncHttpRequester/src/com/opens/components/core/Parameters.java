package com.opens.components.core;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

/**
 * Class to add the parameters to request
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public final class Parameters {
	
	/** The parameters :-| */
	private final ConcurrentHashMap<String, String> urlParams;
	
	/** Construct */
	public Parameters() {
		this.urlParams = new ConcurrentHashMap<String, String>();
	}
	
	/**
	 * Add or replace the parameter
	 * @param name parameter name
	 * @param value parameter value
	 */
	public void add(String name, String value) {
		this.urlParams.put(name, value);
	}
	
	/**
	 * Add or replace the parameter
	 * @param param the value-pair parameter
	 */
	public void add(NameValuePair param) {
		this.add(param.getName(), param.getValue());
	}
	
	/**
	 * Add or replace the new parameter
	 * @param copyFrom any with implement Collection interface to copy the parameters
	 */
	public void add(Collection<NameValuePair> copyFrom) {
		for(NameValuePair param : copyFrom)
			this.add(param);
	}
	
	/**
	 * Add all the paramters
	 * @param copyFrom the map with name-value parameters
	 */
	public void add(Map<String, String> copyFrom) {
		for(Map.Entry<String, String> set : copyFrom.entrySet())
			this.add(set.getKey(), set.getValue());
	}
	
	/**
	 * Return the parameters length
	 * @return the length of the parameters
	 */
	public int length() {
		return this.urlParams.size();
	}
	
	/**
	 * Get the httpEntity for the parameters
	 * @return the HttpEntity in sucess null in other wise
	 */
	protected HttpEntity getEntity() {
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		for(ConcurrentHashMap.Entry<String, String> entry : this.urlParams.entrySet())
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		try {
			return new UrlEncodedFormEntity(params, "UTF-8");
		}
		catch(UnsupportedEncodingException e) {
			return null;
		}
	}

	@Override
	/**
	 * Return the query representation of the url
	 * @return String the url query parameter
	 */
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(ConcurrentHashMap.Entry<String, String> entry : this.urlParams.entrySet()) {
			if(builder.length() > 0)
				builder.append("&");
			builder.append(entry.getKey());
			builder.append("=");
			builder.append(entry.getValue());
		}
		return builder.toString();
	}
	
}