package opens.components.cache;

import java.util.LinkedList;
import java.util.List;

import opens.components.cache.serializers.CacheSerializer;

/**
 * Use it to get the multilevel cache system
 * Item is loaded from the first cache in which it is available,
 * item is stored to all caches
 * 
 * @author Vatroslav Dino Matijas
 *
 */
public class CompositeCache extends Cache {
	
	List<Cache> caches;

	public CompositeCache() {
		this.caches = new LinkedList<Cache>();
	}
	
	public void addCache(Cache cache) {
		caches.add(cache);
	}
	
	@Override
	public Object get(String key, CacheSerializer serializer) {
		for (Cache cache : caches) {
			Object cachedObject = cache.get(key, serializer);
			if (cachedObject != null) {
				return cachedObject;
			}
		}
		return null;
	}

	@Override
	public void put(String key, Object value, CacheSerializer serializer) {
		for (Cache cache : caches) {
			cache.put(key, value, serializer);
		}
	}

	@Override
	public void remove(String key) {
		for (Cache cache : caches) {
			cache.remove(key);
		}		
	}
}
