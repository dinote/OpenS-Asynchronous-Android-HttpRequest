package com.opens.components.response;

import java.io.InputStream;

import com.opens.components.core.Parameters;
import com.opens.components.core.RequestMethods;
import com.opens.components.util.Util;

import android.graphics.Bitmap;

/**
 * Bitmap response most common for cache methods
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public class BitmapResponse extends BynaryResponse {
	
	/**
	 * Full construct
	 * @param url URL associated with the request
	 * @param params parameters of request
	 * @param method the request method {@link RequestMethods}
	 */
	public BitmapResponse(String url, Parameters params, RequestMethods method) {
		super(url, params, method);
	}
	
	/**
	 * Construct
	 * @param url the url to execute
	 */
	public BitmapResponse(String url) {
		this(url, null, RequestMethods.GET);
	}
	
	/**
	 * Construct
	 * @param url the URL to execute and the raw data of parameters
	 * @param params the raw data parameters of request
	 */
	public BitmapResponse(String url, Parameters params) {
		this(url, params, RequestMethods.GET);
	}

	@Override
	protected void onSuccess(InputStream response) {
		Bitmap bitmap = Util.decodeBitmap(response);
		if(bitmap == null)
			this.onError(new Exception("Bitmap response is null"));
		else
			this.onSuccess(bitmap);
	}
	
	/**
	 * Return the bitmap response, if the bitmap is null onError will be called
	 * @param response the bitmap response
	 */
	protected void onSuccess(Bitmap response) {
		
	}
	
}