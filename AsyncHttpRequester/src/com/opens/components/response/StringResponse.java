package com.opens.components.response;

import org.apache.http.HttpResponse;

import com.opens.components.core.HttpBaseRequest;
import com.opens.components.util.Util;

/**
 * Perform a string request on server
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public class StringResponse extends HttpBaseRequest {

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