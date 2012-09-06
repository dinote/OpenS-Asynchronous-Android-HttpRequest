package opens.components.cache.serializers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public final class BitmapSerializer {
	
	private BitmapSerializer() { }
	
	private static final int MAX_BUFFER_SIZE = 8192;
	
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
	
	public static Bitmap deserialize() {
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
		catch(OutOfMemoryError e) { }
	}
	
	public static Bitmap display(ImageView image, String file) {
		return new BitmapLoader(image).execute(file);
	}
}
