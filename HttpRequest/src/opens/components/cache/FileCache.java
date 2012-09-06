package opens.components.cache;

import java.io.InputStream;
import java.io.File;

import opens.components.cache.serializers.BitmapSerializer;

import android.content.Context;

public class FileCache {
			
	private String rootDir; 	
	private static final String TAG = "/opens/"
	
	public FileCache(Context context) {				
		this.rootDir = "/" + createRootDir(context);		
	}
	
	protected final String createRootDir(Context context) {		
		String rootDir = context.getCacheDir().getAbsolutePath() + FileCache.TAG; 
		File file = new File(rootDir);		
		file.mkdirs();
		return rootDir;
	}
	
	public synchronized boolean remove(String key) {
		return new File(this.rootDir+key).delete();
	}
	
	public synchronized Object get(String key) {
		File file = new File(this.rootDir+key);
		if(file.exists())
			BitmapSerializer.deserialize(file.getAbsolutePath());
		return null;
	}

	public synchronized void put(String key, InputStream content) {
		File file = new File(this.rootDir+key);
		if(!file.exists()) {
			file.deleteOnExit();
			BitmapSerializer.serialize(content, file.getAbsolutePath());
		}
	}

	
}
