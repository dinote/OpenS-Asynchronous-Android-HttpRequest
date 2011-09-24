package opens.components.http;

import org.apache.http.HttpResponse;

public class StringRequest extends HttpObjectRequest<String> {

	@Override
	protected void onHttpResponseReceived(HttpResponse response)
			throws Exception {
		String responseString = getString(response);
		setResponseObject(responseString);
	}

}
