package opens.components.cache.serializers;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import opens.components.cache.utils.BitmapLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Class to serialize bitmaps
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since 07/09/2012
 */
public final class BitmapSerializer {
	
	/** The buffer size */
	private static final int MAX_BUFFER_SIZE = 8192;
	
	/** 
	 * Private construct don't get a instance of this class use the static methods 
	 */
	private BitmapSerializer() { }
	
	/**
	 * Serialize the file to any destiny
	 * @param input the file input bytes
	 * @param destiny the destiny of the new file
	 */
	public static void serialize(InputStream input, String destiny) {
		try {
			BufferedInputStream buffer = new BufferedInputStream(input, BitmapSerializer.MAX_BUFFER_SIZE);
			FileOutputStream exitFile = new FileOutputStream(destiny);
			int currentByte = 0;
			byte[] buffered = new byte[BitmapSerializer.MAX_BUFFER_SIZE];
			while((currentByte = buffer.read(buffered, 0, BitmapSerializer.MAX_BUFFER_SIZE)) != -1)
				exitFile.write(buffered, 0, currentByte);
			input.close();
			input = null;
			exitFile.flush();
			exitFile.close();
			exitFile = null;
		}
		catch(IOException e) { }
		catch(OutOfMemoryError ex) { }
	}
	
	/**
	 * Retrive a bitmap from location
	 * @param input the file location (full path)
	 * @return the bitmap in success null in other wise
	 */
	public static Bitmap deserialize(String input) {
		try {
			BitmapFactory.Options opt = new BitmapFactory.Options();
			opt.inPurgeable = true;
			opt.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(input, opt);
			int scale = 1;
			final int requiredSize = 250;
			while(opt.outWidth/scale/2>=requiredSize && opt.outHeight/scale/2>=requiredSize)
				scale*=2;
			opt.inJustDecodeBounds = false;
			opt.inSampleSize = scale;
			return BitmapFactory.decodeFile(input, opt);
		}
		catch(OutOfMemoryError e) { 
			return null;
		}
	}
	
	/**
	 * Display the bitmap
	 * @param image the view to display
	 * @param file the file destiny (just the file name)
	 */
	public static void display(ImageView image, String file) {
		new BitmapLoader(image).execute(file);
	}
}
