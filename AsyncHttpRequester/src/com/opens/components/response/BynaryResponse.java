package com.opens.components.response;

import java.io.InputStream;

import org.apache.http.HttpResponse;

import com.opens.components.core.HttpBaseRequest;

/**
 * Bynary response for requests like images
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public class BynaryResponse extends HttpBaseRequest {

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