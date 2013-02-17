package com.opens.components.response;

import java.io.InputStream;

import org.apache.http.HttpResponse;

import com.opens.components.core.HttpBaseRequest;
import com.opens.components.core.Parameters;
import com.opens.components.core.RequestMethods;

/**
 * Bynary response for requests like images
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public class BynaryResponse extends HttpBaseRequest {
	
	/**
	 * Full construct
	 * @param url URL associated with the request
	 * @param params parameters of request
	 * @param method the request method {@link RequestMethods}
	 */
	public BynaryResponse(String url, Parameters params, RequestMethods method) {
		super(url, params, method);
	}
	
	/**
	 * Construct
	 * @param url the url to execute
	 */
	public BynaryResponse(String url) {
		this(url, null, RequestMethods.GET);
	}
	
	/**
	 * Construct
	 * @param url the URL to execute and the raw data of parameters
	 * @param params the raw data parameters of request
	 */
	public BynaryResponse(String url, Parameters params) {
		this(url, params, RequestMethods.GET);
	}

	@Override
	protected void onSuccess(HttpResponse response) {
		try {
			this.onSuccess(response.getEntity().getContent());
		}
		catch(Exception e) {
			this.onError(e);
		}
	}
	
	/**
	 * the sucess of the HttpResponse
	 * @param response The InputStream of content ready to use 
	 */
	protected void onSuccess(InputStream response) {
		
	}
	
}