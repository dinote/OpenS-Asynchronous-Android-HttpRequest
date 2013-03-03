package com.opens.components.core;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.net.http.AndroidHttpClient;

/**
 * The base class to perform the HTTPRequests 
 * All requests are GET by default, to set another one use 
 * {@link #setRequestMethod(RequestMethods)} instead
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public abstract class HttpBaseRequest implements Runnable {
	
	/** The default user-agent to report requests */
	private String userAgent = "AsyncHttpRequester-1.0";
	/** The method to execute */
	private RequestMethods method;
	/** The target url to execute */
	private String targetURL;
	/** The parameters of request */
	private Parameters params;
	/** The header data */
	private HttpUriRequest selfRequest;
	/** Represents the socket time out of requests */
	private int socketTimeout;
	/** Represents the connection timeout of the requests */
	private int connectionTimeout;
	/** The default time out of the request */
	public static final int DEFAULT_TIMEOUT = 20000;
	
	/**
	 * The default request codes of requests
	 * @author Leonardo Rossetto <leonardoxh@gmail.com>
	 * @since API Version: 1.0
	 */
	protected static enum REQUEST_CODES {
		REQUEST_STARTED,
		REQUEST_SUCCESS,
		REQUEST_ERROR,
		REQUEST_FINISH
	}
	
	/**
	 * Full construct
	 * @param url URL associated with the request
	 * @param params parameters of request
	 * @param method the request method {@link RequestMethods}
	 */
	public HttpBaseRequest(String url, Parameters params, RequestMethods method) {
		this.targetURL = url;
		this.params = params;
		this.method = method;
		this.socketTimeout = HttpBaseRequest.DEFAULT_TIMEOUT;
		this.connectionTimeout = HttpBaseRequest.DEFAULT_TIMEOUT;
		this.selfRequest = new HttpGet();
	}
	
	/**
	 * Construct
	 * @param url the url to execute
	 */
	public HttpBaseRequest(String url) {
		this(url, null, RequestMethods.GET);
	}
	
	/**
	 * Construct
	 * @param url the URL to execute and the raw data of parameters
	 * @param params the raw data parameters of request
	 */
	public HttpBaseRequest(String url, Parameters params) {
		this(url, params, RequestMethods.GET);
	}
	
	/**
	 * Set the parameters for the request
	 * @param params the params
	 */
	public void setParameters(Parameters params) {
		this.params = params;
	}
	
	/**
	 * Return the method associated with this response
	 * @return the method associated with this response
	 */
	public RequestMethods getRequestMethod() {
		return this.method;
	}
	
	/**
	 * Set the request method for this request
	 * @param method the method
	 * @see {@link RequestMethods}
	 */
	public void setRequestMethod(RequestMethods method) {
		this.method = method;
	}
	
	/**
	 * Add the headers to request
	 * @param headerName the header name
	 * @param value the value of this header
	 */
	public void addHeader(String headerName, String value) {
		this.selfRequest.setHeader(headerName, value);
	}
	
	/**
	 * Add a header to this request
	 * @param header the header to add
	 */
	public void addHeader(Header header) {
		this.selfRequest.setHeader(header);
	}
	
	/**
	 * Set all headers of this request
	 * @param headers the headers of the request
	 */
	public void setHeaders(Header[] headers) {
		this.selfRequest.setHeaders(headers);
	}
	
	/**
	 * Return the headers associate with this object
	 * @return the heades of this object
	 */
	public Header[] getHeaders() {
		return this.selfRequest.getAllHeaders();
	}
	
	/**
	 * Set the User-Agent header of requests
	 * @param userAgent the new User-Agent value
	 */
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	/**
	 * Set the socket timeout in milliseconds
	 * @param socketTimeout the new sockettimeout value
	 */
	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
	/**
	 * Set the connection timeout of this request
	 * @param connectionTimeout the connection timeout im milliseconds
	 */
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	/**
	 * Handle the possible message to correct method
	 * @param what the status of caller
	 * @param response the response object
	 */
	private void sendMessageToHandler(REQUEST_CODES what, Object response) {
		switch(what) {
			case REQUEST_STARTED:
				this.onStart();
				break;
			case REQUEST_SUCCESS:
				this.onSuccess((HttpResponse) response);
				break;
			case REQUEST_ERROR:
				this.onError((Exception) response);
				break;
			case REQUEST_FINISH:
				this.onFinish();
				break;
		}
	}
	
	/**
	 * Get the target URL of this request
	 * @return the URL of this request
	 */
	public String getUrl() {
		return this.targetURL;
	}
	
	/**
	 * Set the URL of this request
	 */
	public void setUrl(String url) {
		this.targetURL = url;
	}
	
	/**
	 * The response was a success 
	 * @param response the response of request
	 */
	protected void onSuccess(HttpResponse response) {
		
	}
	
	/**
	 * Request was started
	 */
	protected void onStart() {
		
	}
	
	/**
	 * Response have returned a error
	 * @param e the erro trace
	 */
	protected void onError(Exception e) {
		
	}
	
	/**
	 * The request are finished
	 */
	protected void onFinish() {
		
	}
	
	public void run() {
		switch(this.method) {
			case DELETE:
				this.selfRequest = this.delete();
				break;
			case GET:
				this.selfRequest = this.get();
				break;
			case POST:
				this.selfRequest = this.post();
				break;
			case PUT:
				this.selfRequest = this.put();
				break;
			default:
				this.selfRequest = this.get();
				break;
		}
		this.selfRequest.setHeader("User-Agent", this.userAgent);
		this.setParamsForRequest();
		this.execute(this.selfRequest);
	}
	
	/**
	 * Set the parameters for this request
	 */
	protected void setParamsForRequest() {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, this.connectionTimeout);
		HttpConnectionParams.setSoTimeout(httpParams, this.socketTimeout);
		this.selfRequest.setParams(httpParams);
	}
	
	/**
	 * Match the HttpUriRequest to GET method
	 * @return the get object to request
	 */
	private HttpGet get() {
		HttpGet request = null;
		if(this.params != null)
			request = new HttpGet(this.targetURL+"?"+this.params.toString());
		else
			request = new HttpGet(this.targetURL);
		request.setHeaders(this.selfRequest.getAllHeaders());
		return request;
	}
	
	/**
	 * Match the HttpUriRequest to post method
	 * @return the post object
	 */
	private HttpPost post() {
		HttpPost request = new HttpPost(this.targetURL);
		request.setHeaders(this.selfRequest.getAllHeaders());
		if(this.params != null)
			request.setEntity(this.params.getEntity());
		return request;
	}
	
	/**
	 * Match the HttpUriRequest to PUT method
	 * @return the put object
	 */
	private HttpPut put() {
		HttpPut request = new HttpPut(this.targetURL);
		request.setHeaders(this.selfRequest.getAllHeaders());
		if(this.params != null)
			request.setEntity(this.params.getEntity());
		return request;
	}
	
	/**
	 * Match the HttpUriRequest to delete method
	 * @return the delete object to use
	 */
	private HttpDelete delete() {
		HttpDelete request = new HttpDelete(this.targetURL);
		request.setHeaders(this.selfRequest.getAllHeaders());
		return request;
	}
	
	/**
	 * Execute the request
	 * @param request Request to execute
	 */
	protected void execute(HttpUriRequest request) {
		final AndroidHttpClient executor = AndroidHttpClient.newInstance(this.userAgent);
		try {
			this.sendMessageToHandler(REQUEST_CODES.REQUEST_STARTED, null);
			HttpResponse response = executor.execute(request);
			if(response.getStatusLine().getStatusCode() >= 300)
				this.sendMessageToHandler(REQUEST_CODES.REQUEST_ERROR, 
						new HttpResponseException(response.getStatusLine().getStatusCode(),
								response.getStatusLine().getReasonPhrase()));
			else
				this.sendMessageToHandler(REQUEST_CODES.REQUEST_SUCCESS, response);
		}
		catch(Exception e) {
			this.sendMessageToHandler(REQUEST_CODES.REQUEST_ERROR, e);
		}
		finally {
			executor.close();
			this.sendMessageToHandler(REQUEST_CODES.REQUEST_FINISH, null);
		}
	}

	@Override
	public String toString() {
		return this.targetURL;
	}
	
}