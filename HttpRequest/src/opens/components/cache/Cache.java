package opens.components.cache;

import opens.components.cache.serializers.CacheSerializer;

/**
 * 
 * Base class for Cache.
 * 
 * Cache is a container providing the service of storing, removing 
 * and retrieving data by key. 
 * 
 * To store a concrete data type in cache provide a CacheSerializer which 
 * is a strategy pattern used to extend the Cache support for different objects placed in cache.
 * 
 * Example of CacheSerializers are: POJO, Image, String.
 * 
 * @author Vatroslav Dino Matijas
 *
 */
public abstract class Cache {
	
	/**
	 * Returns serialized object from cache or null if there is no object for the 
	 * given key
	 * @param key
	 * @param serializer
	 * @return
	 */
	public abstract Object get(String key, CacheSerializer serializer);	
	
	/**
	 * Serializes the value and places it in cache
	 * @param key
	 * @param value
	 * @param serializer
	 */
	public abstract void put(String key, Object value, CacheSerializer serializer);
	
	/**
	 * Removes the key and the object associated with the key from cache
	 * @param key
	 */
	public abstract void remove(String key);
		
}
