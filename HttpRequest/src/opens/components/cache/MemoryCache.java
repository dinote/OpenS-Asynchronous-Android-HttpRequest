package opens.components.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import opens.components.cache.serializers.CacheSerializer;

/**
 * Soft reference memory cache.
 * Garbage collector will automatically clear this cache if memory is required.
 * 
 * Fast cache write and read, but not permanent
 * 
 * For best performance use Composite Cache with fast memory cache as the first
 * cache and slower but permanent file cache as the second cache
 * 
 * 
 * @author Vatroslav Dino Matijas
 *
 */
public class MemoryCache extends Cache {
	private Map<String, SoftReference<Object>>	cache;

	private static MemoryCache defaultCache;
	
	public static MemoryCache defaultCache() {
		if (defaultCache == null) {
			defaultCache = new MemoryCache();
		}
		return defaultCache;
	}
	
	public MemoryCache() {
		this.cache = new HashMap<String, SoftReference<Object>>();
	}
	
	@Override
	public Object get(String key, CacheSerializer serializer) {
		SoftReference<Object> val = cache.get(key);
		if (val != null) {
			return val.get();
		}
		return null;
	}

	@Override
	public void put(String key, Object value, CacheSerializer serializer) {
		cache.put(key, new SoftReference<Object>(value));
		
	}

	@Override
	public void remove(String key) {
		cache.remove(key);		
	}
	
	
}
