package com.opens.components.response;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Perform a JSON request on server
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public class JSONResponse extends StringResponse {

	@Override
	protected void onSuccess(String response) {
		try {
			if(response.startsWith("{"))
				this.onSuccess(new JSONObject(response));
			else
				this.onSuccess(new JSONArray(response)); //TODO - Use a thread for parse
		}
		catch(Exception e) {
			this.onError(e);
		}
	}
	
	/**
	 * Return the JSON response
	 * @param response the object parsed
	 */
	protected void onSuccess(JSONObject response) {
		
	}
	
	/**
	 * Return the JSON response
	 * @param response the object parsed
	 */
	protected void onSuccess(JSONArray response) {
		
	}
	
}