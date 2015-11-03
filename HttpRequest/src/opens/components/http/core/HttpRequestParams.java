package opens.components.http.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

/**
 * Container for request parameters 
 * with export method for URL parameters (?key=value&key2=value2&....) 
 * and //TODO add post parameters export
 * 
 * @author Vatroslav Dino Matijas
 *
 */
public class HttpRequestParams {
	
	private Map<String, String> params;
	
	public HttpRequestParams() {
		params = new TreeMap<String, String>();
	}
	
	public HttpRequestParams param(String key, Object param) {
		if (param != null) {
			return this.stringParam(key, param.toString());
		}		
		return this;
	}
	
	public HttpRequestParams stringParam(String key, String val) {
		if (val != null) {
			params.put(key, val);
		}
		return this;
	}
	
	public void clear() {
		params.clear();
	}
	
	public String toURLParametersString() {
		StringBuilder out = new StringBuilder();
		boolean firstEntry = true;
		for (Map.Entry<String, String> entry : params.entrySet()) {
			if (firstEntry) {
				firstEntry = false;
				out.append('?');
			} else {
				out.append('&');
			}
			out.append(entry.getKey());
			out.append('=');
			out.append(URLEncoder.encode(entry.getValue()));
		}
		return out.toString();
	}
	
	public UrlEncodedFormEntity toUrlEncodedFormEntry() throws UnsupportedEncodingException {
		List<NameValuePair> p = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			p.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		return new UrlEncodedFormEntity(p, "UTF-8");
	}
}
