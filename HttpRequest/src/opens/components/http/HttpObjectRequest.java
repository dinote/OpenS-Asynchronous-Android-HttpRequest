package opens.components.http;


import opens.components.cache.serializers.CacheSerializer;
import opens.components.cache.serializers.ObjectSerializer;
import opens.components.http.core.HttpRequest;

/**
 * This is the base class for custom HttpRequests.
 * 
 * For the responseObject to be permanently cacheable it must implement Serializable interface.
 * Note that most of the standard objects like ArrayList, LinkedList, String.... are serializable by
 * default.
 * 
 * Subclasses should implement void onHttpResponseReceived(HttpResponse response)
 * to create the response object. Response should be set in the onHttpResponseReceived 
 * using setResponseObject method.
 *  
 * @author Vatroslav Dino Matijas
 *
 * @param <C> determines the responseObject type
 */
public abstract class HttpObjectRequest<C> extends HttpRequest {

	C responseObject;
	
	final public C getResponseObject() {
		return responseObject;
	}
	
	final protected void setResponseObject(C responseObject) {
		this.responseObject = responseObject;
	}
	
	@Override
	protected CacheSerializer getCacheSerializer() {		
		return ObjectSerializer.instance();
	}

	@Override
	protected Object getObjectToCache() {
		return getResponseObject();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void loadFromCachedObject(Object state) {
		setResponseObject((C)state);
	}

}
