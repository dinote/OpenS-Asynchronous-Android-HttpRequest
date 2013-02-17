package com.opens.components.response;

import org.apache.http.HttpResponse;

import com.opens.components.core.HttpBaseRequest;
import com.opens.components.core.Parameters;
import com.opens.components.core.RequestMethods;
import com.opens.components.util.Util;

/**
 * Perform a string request on server
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public class StringResponse extends HttpBaseRequest {
	
	/**
	 * Full construct
	 * @param url URL associated with the request
	 * @param params parameters of request
	 * @param method the request method {@link RequestMethods}
	 */
	public StringResponse(String url, Parameters params, RequestMethods method) {
		super(url, params, method);
	}
	
	/**
	 * Construct
	 * @param url the url to execute
	 */
	public StringResponse(String url) {
		this(url, null, RequestMethods.GET);
	}
	
	/**
	 * Construct
	 * @param url the URL to execute and the raw data of parameters
	 * @param params the raw data parameters of request
	 */
	public StringResponse(String url, Parameters params) {
		this(url, params, RequestMethods.GET);
	}

	@Override
	protected void onSuccess(HttpResponse response) {
		try {
			this.onSuccess(Util.decodeString(response.getEntity().getContent()));
		}
		catch(Exception e) {
			this.onError(e);
		}
	}
	
	/**
	 * Return the string response to caller, if the response can't be decoded
	 * in a valid string the onError(Exception) will be called
	 * @param response the response
	 */
	protected void onSuccess(String response) {
		
	}
	
}