package opens.components.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import opens.components.cache.serializers.CacheSerializer;

import android.content.Context;

/**
 * Permanent cache using the Android cache directory for storage.
 * Use this when you want permanent storage. It is an excellent 
 * image cache.
 * 
 * Slower read and write than MemoryCache, but permanently stores data.
 * 
 * @author Vatroslav Dino Matijas
 *
 */
public class FileCache  extends Cache{
			
	private String rootDir; 	
	
	public FileCache(Context context, String tag) {				
		this.rootDir = createRootDir(context, tag);		
	}
	
	protected String createRootDir(Context context, String tag) {		
		Context appContext = context.getApplicationContext();
		
		File cacheDir = appContext.getCacheDir();		
		String rootDir = cacheDir.getAbsolutePath();
		
		rootDir += "/opens/" + tag; 
		File file = new File(rootDir);		
		file.mkdirs();
		
		return rootDir;
	}
	
	synchronized public void remove(String key) {
		File file = new File(rootDir + "/" + key);
		if (file.exists()) {
			file.delete();
		}
	}

	@Override
	synchronized public Object get(String key, CacheSerializer serializer) {
		BufferedInputStream in = null;	
		File file = new File(rootDir + "/" + key);
		try {
			//TODO do expiration check. check file.lastModified()
			if (file.exists()) {
				in = new BufferedInputStream(new FileInputStream(file));
				return serializer.deserializeFromInputStream(in);
			}
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// OK to ignore						
				}
			}
		}
		return null;
	}

	@Override
	synchronized public void put(String key, Object value, CacheSerializer serializer) {
		File file = new File(rootDir + "/" + key);
		BufferedOutputStream out = null;
		try {
			file.createNewFile();
			file.deleteOnExit();
			out = new BufferedOutputStream(new FileOutputStream(file));
			serializer.serializeToOutputStream(out, value);
		} catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// OK to ignore						
				}
			}
        }
		
	}

	
}
