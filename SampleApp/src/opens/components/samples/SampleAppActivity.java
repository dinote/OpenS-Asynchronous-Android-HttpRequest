package opens.components.samples;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import opens.components.cache.FileCache;
import opens.components.http.HttpObjectRequest;
import opens.components.http.core.RequestQueue;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SampleAppActivity extends Activity {
    
	public class TwitterTimelineRequest extends HttpObjectRequest<List<String>> {

		@Override
		protected void onHttpResponseReceived(HttpResponse response)
				throws Exception {
			JSONArray jTimeline = getJSONArray(response);
			List<String> timeline = new LinkedList<String>();
			for(int i = 0; i < jTimeline.length(); i++) {
				JSONObject element = jTimeline.getJSONObject(i);
				timeline.add(element.getString("text"));
			}
			setResponseObject(timeline);			
		}		
	}
		
	@SuppressWarnings("unused")
	private void onTimelineRequestSuccess(TwitterTimelineRequest request) {
		List<String> timeline = request.getResponseObject();
		TextView text = (TextView)findViewById(R.id.text);		
		text.setText(timeline.toString());
		Log.d("HTTP_REQUSEST",timeline.toString());
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TwitterTimelineRequest request = new TwitterTimelineRequest();
        request.get("http://api.twitter.com/1/statuses/public_timeline.json");
        FileCache cache = new FileCache(this, "twitter");
        request.setCache(cache);
        request.onSuccess(this, "onTimelineRequestSuccess");
        RequestQueue.instance().push(request);
     
    }
}