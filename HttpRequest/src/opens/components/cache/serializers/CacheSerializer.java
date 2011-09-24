package opens.components.cache.serializers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Encapsulates the serialization of objects placed in cache.
 * This allows the extension of cache to support different objects, 
 * for example: Images, POJO, Strings 
 * 
 * This is the interface to a strategy pattern used to extend the
 * cache with different formats
 *  
 * @author Vatroslav Dino Matijas
 *
 */
public interface CacheSerializer {
	
	public Object deserializeFromInputStream(InputStream is)  throws IOException;	
	
	public void serializeToOutputStream(OutputStream os, Object object) throws IOException;
	
}
