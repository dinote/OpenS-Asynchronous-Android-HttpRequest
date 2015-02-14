package opens.components.http.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.os.Message;

import opens.components.cache.Cache;
import opens.components.cache.serializers.CacheSerializer;
import opens.components.http.ImageRequest;

/**
 * HttpResponse base class. Should be subclassed only if for some reason
 * HttpObjectRequest is not suitable for subclassing
 * Use HttpObjectRequest as a super class when building custom requests
 * 
 * @author Vatroslav Dino Matijas
 *
 */
public abstract class HttpRequest implements Runnable {

	
	public static final int METHOD_GET = 0;
	
	public static final int METHOD_POST = 1;
	
	public static final int REQUEST_STARTED = 0;
	public static final int REQUEST_FINISHED = 1;
	public static final int REQUEST_SUCCESS = 2;
	public static final int REQUEST_ERROR = 3;
	 
	public static final int DoNotWriteToCacheCachePolicy = 1;
	public static final int DoNotReadFromCacheCachePolicy = 2;
	/**
	 * If the request fails check in cache and return cached object instead
	 */
	public static final int FallbackToCacheIfLoadFailsCachePolicy = 4;
	
	public static final int DefaultCachePolicy = FallbackToCacheIfLoadFailsCachePolicy | DoNotReadFromCacheCachePolicy;
	
	private HttpRequestHandler	handler; 	
	
	private int method = METHOD_GET;
	
	private String url;
	
	private int timeout = 25000;
	
	private boolean error;
	
	private boolean finished;
	
	private boolean canceled;
	
	private Cache cache;
	
	private boolean responseFromCache = false;
	
	private HttpResponse response = null;
	
	private HttpRequestParams params;
	
	private int cachePolicy;

    private String userAgent = null;

	public HttpRequest() {
		super();		
		this.handler = new HttpRequestHandler();
		cachePolicy = DefaultCachePolicy;
		params = new HttpRequestParams();

	}
	
	public HttpResponse getResponse() {
		return response;
	}
		
	public boolean isCachePolicySet(int cachePolicy) {
		return (this.cachePolicy & cachePolicy) != 0;
	}
	
	public int getCachePolicy() {
		return cachePolicy;
	}

	public void setCachePolicy(int cachePolicy) {
		this.cachePolicy = cachePolicy;
	}	
	
	public boolean isError() {
		return error;
	}

	public boolean isFinished() {
		return finished;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}
	
	public HttpRequest get(String url) {
		this.url = url;
		this.params.clear();
		return this;
	}
	
	public void onSuccess(Object target, String action) {
		handler.setOnSuccess(new HttpRequestHandler.TargetAction(target, action));
	}
	
	public void onError(Object target, String action) {
		handler.setOnError(new HttpRequestHandler.TargetAction(target, action));
	}
	
	public void onStart(Object target, String action) {
		handler.setOnStart(new HttpRequestHandler.TargetAction(target, action));
	}
	
	public void onFinish(Object target, String action) {
		handler.setOnStart(new HttpRequestHandler.TargetAction(target, action));
	}



	public String getUrl() {
		return url;
	}
	
	public boolean isResponseFromCache() {
		return responseFromCache;
	}
	
	public String getRequestCacheKey() {
		return url.replaceAll("[^0-9^a-z^A-Z]", "");
	}
	
	public Cache getCache () {
		return cache;
	}
	
	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	public String getString(HttpResponse response) throws IllegalStateException, IOException {
		String line = "";
	    StringBuilder total = new StringBuilder();	    
	    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	    while ((line = rd.readLine()) != null) { 
	        total.append(line); 
	    }	    
	    // Return full string
	    return total.toString();
	}
	
	public JSONArray getJSONArray(HttpResponse response) throws IllegalStateException, IOException, JSONException {
		JSONTokener tokener = new JSONTokener(getString(response));
		return new JSONArray(tokener);
	}	

	/**
	 * CacheSupport
	 * Override this method in subclasses to provide custom caching support
	 * @see ImageRequest for example implementation
	 */
	abstract protected CacheSerializer getCacheSerializer();
	
	/**
	 * CacheSupport
	 * Override this method in subclasses to provide custom caching support
	 * @see ImageRequest for example implementation
	 */
	abstract protected Object getObjectToCache();
	
	/**
	 * CacheSupport
	 * Override this method in subclasses to provide custom caching support
	 * @see ImageRequest for example implementation
	 */
	abstract protected void loadFromCachedObject(Object state);
	
	/**
	 * Override this method to support custom request processing. This method is called on 
	 * the worker thread, and not on the UI thread.
	 * @param response
	 * @throws Exception
	 */
	abstract protected void onHttpResponseReceived(HttpResponse response)  throws Exception;	
	
	private void sendMessageToHandler(int what, Object obj) {
		if (this.handler == null) {
			return;
		}
		
		Message msg = Message.obtain(handler, what, obj);
		handler.sendMessage(msg);
	}
	
	private boolean tryToLoadFromCache(Cache cache, CacheSerializer cacheSerializer) {
		Object cachedState = null;
		if (cache != null && cacheSerializer != null) { //try to get memento from cache
			cachedState = cache.get(getRequestCacheKey(), cacheSerializer);			
		}
		
		if (cachedState != null) { //Get response from cache
			this.responseFromCache = true;
			this.loadFromCachedObject(cachedState);
			this.finished = true;
			sendMessageToHandler(REQUEST_SUCCESS, this);			
			return true;
		}
		return false;
	}
	
	public void run() {
		if (canceled) {
			return;
		}
		
		CacheSerializer cacheSerializer = getCacheSerializer();
		Cache cache = getCache();
				
		if (isCachePolicySet(DoNotReadFromCacheCachePolicy) ==false) {
			if (tryToLoadFromCache(cache, cacheSerializer)) {
				return;
			}
		}
		
		HttpClient client = new DefaultHttpClient(); //TODO implement a client pool

        if(userAgent!=null) {client.getParams().setParameter( "User-Agent",getUserAgent());}

        HttpConnectionParams.setSoTimeout(client.getParams(), timeout);

        sendMessageToHandler(REQUEST_STARTED, this);
		try {
			switch(method) {
			case METHOD_GET:			
				response = client.execute(new HttpGet(url + params.toURLParametersString()));

				this.onHttpResponseReceived(response);				
				break;
			}
			
			if (isCachePolicySet(DoNotWriteToCacheCachePolicy) == false &&
					cache != null && cacheSerializer != null) {
				cache.put(getRequestCacheKey(), getObjectToCache(), cacheSerializer);
			}
			sendMessageToHandler(REQUEST_SUCCESS, this);
			
		} catch (Exception e) {
			e.printStackTrace();
			if (isCachePolicySet(FallbackToCacheIfLoadFailsCachePolicy)) {
				if (tryToLoadFromCache(cache, cacheSerializer)) {
					sendMessageToHandler(REQUEST_FINISHED, this);
					return;
				}
			}			
			finished = true;
			error = true;
			sendMessageToHandler(REQUEST_ERROR, this);
		}
		sendMessageToHandler(REQUEST_FINISHED, this);
	}

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
