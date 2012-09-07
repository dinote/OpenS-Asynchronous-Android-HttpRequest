package opens.components.cache;

import java.io.InputStream;
import java.io.File;

import opens.components.cache.serializers.BitmapSerializer;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * New FileCache class less code
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since 07/09/2012
 */
public class FileCache {
			
	/** The cache dir */
	private String rootDir;
	/** The dir of cache */
	private static final String TAG = "/opens/";
	
	/**
	 * Constructor
	 * @param context context to get cache default directory
	 */
	public FileCache(Context context) {				
		this.rootDir = "/" + createRootDir(context);		
	}
	
	/**
	 * Create the and define the cache directory
	 * @param context
	 * @return
	 */
	protected final String createRootDir(Context context) {		
		String rootDir = context.getCacheDir().getAbsolutePath() + FileCache.TAG; 
		File file = new File(rootDir);		
		file.mkdirs();
		return rootDir;
	}
	
	/**
	 * Delete file from cache
	 * @param key the file name (just the file name not the absolute path)
	 */
	public synchronized boolean remove(String key) {
		return new File(this.rootDir+key).delete();
	}
	
	/**
	 * Retrive the bitmap from cache
	 * @param key the file name (just the file name not the absolute path)
	 * @return the bitmap from cache in success null in other wise
	 */
	public synchronized Bitmap get(String key) {
		File file = new File(this.rootDir+key);
		if(file.exists())
			BitmapSerializer.deserialize(file.getAbsolutePath());
		return null;
	}

	/**
	 * Add the file to cache
	 * @param key the file name (just the file name not the absolute path)
	 * @param content the file bytes
	 */
	public synchronized void put(String key, InputStream content) {
		File file = new File(this.rootDir+key);
		if(!file.exists()) {
			file.deleteOnExit();
			BitmapSerializer.serialize(content, file.getAbsolutePath());
		}
	}

}
