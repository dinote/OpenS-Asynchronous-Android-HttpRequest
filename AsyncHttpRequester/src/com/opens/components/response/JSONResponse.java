package com.opens.components.response;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opens.components.core.Parameters;
import com.opens.components.core.RequestMethods;

/**
 * Perform a JSON request on server
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public class JSONResponse extends StringResponse {
	
	/**
	 * Full construct
	 * @param url URL associated with the request
	 * @param params parameters of request
	 * @param method the request method {@link RequestMethods}
	 */
	public JSONResponse(String url, Parameters params, RequestMethods method) {
		super(url, params, method);
	}
	
	/**
	 * Construct
	 * @param url the url to execute
	 */
	public JSONResponse(String url) {
		this(url, null, RequestMethods.GET);
	}
	
	/**
	 * Construct
	 * @param url the URL to execute and the raw data of parameters
	 * @param params the raw data parameters of request
	 */
	public JSONResponse(String url, Parameters params) {
		this(url, params, RequestMethods.GET);
	}

	@Override
	protected void onSuccess(String response) {
		try {
			if(response.startsWith("{"))
				this.onSuccess(new JSONObject(response));
			else
				this.onSuccess(new JSONArray(response)); //TODO - Use a thread for parse
		}
		catch(Exception e) {
			this.onError(e);
		}
	}
	
	/**
	 * Return the JSON response
	 * @param response the object parsed
	 */
	protected void onSuccess(JSONObject response) {
		
	}
	
	/**
	 * Return the JSON response
	 * @param response the object parsed
	 */
	protected void onSuccess(JSONArray response) {
		
	}
	
}