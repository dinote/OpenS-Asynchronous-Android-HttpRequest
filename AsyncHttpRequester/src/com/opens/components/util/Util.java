package com.opens.components.util;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Common class to utilitaries
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public final class Util {
	
	/** Use just the static methods of this class */
	private Util() { }
	
	/**
	 * Decode a simple bitmap from inputstream
	 * @param is input containing the bytes
	 * @return the bitmap in success null in other wise
	 */
	public static Bitmap decodeBitmap(InputStream is) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(is, null, options);
		options.inSampleSize = Util.findSimpleSize(options.outWidth, options.outHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(is, null, options);
	}
	
	/**
	 * Find the simple size to image decode
	 * @param width outWidth of image
	 * @param height outHeight of image
	 * @return the simple size
	 */
	private static int findSimpleSize(int width, int height) {
		final int target = 250;
		return 0;
	}
	
	/**
	 * Decode the stream to a valid string
	 * @param is the input to decode
	 * @return the string decoded
	 */
	public static String decodeString(InputStream is) {
		return null;
	}
	
}