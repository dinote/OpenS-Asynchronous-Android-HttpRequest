package opens.components.cache.serializers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapSerializer implements CacheSerializer {
	
	private static BitmapSerializer instance;
	
	public static BitmapSerializer instance() {
		if (instance == null) {
			instance = new BitmapSerializer();
		}
		return instance;
	}
	
	//CacheSerializer	
	public Object deserializeFromInputStream(InputStream is) throws IOException{
		return BitmapFactory.decodeStream(is);
	}
	
	public void serializeToOutputStream(OutputStream os, Object object) throws IOException{
		Bitmap bitmap = (Bitmap)object;
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);		
	}
}
