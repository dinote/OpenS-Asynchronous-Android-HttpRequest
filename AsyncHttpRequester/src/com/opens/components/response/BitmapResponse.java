package com.opens.components.response;

import java.io.InputStream;

import com.opens.components.util.Util;

import android.graphics.Bitmap;

/**
 * Bitmap response most common for cache methods
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public class BitmapResponse extends BynaryResponse {

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