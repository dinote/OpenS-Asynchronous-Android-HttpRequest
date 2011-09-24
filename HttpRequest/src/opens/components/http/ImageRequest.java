package opens.components.http;

import org.apache.http.HttpResponse;

import opens.components.cache.serializers.BitmapSerializer;
import opens.components.cache.serializers.CacheSerializer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRequest extends HttpObjectRequest<Bitmap> {
		
	protected void onHttpResponseReceived(HttpResponse response) throws Exception {		
		Bitmap responseBitmap = BitmapFactory.decodeStream(response.getEntity().getContent());
		setResponseObject(responseBitmap);
	}
	
	//Cache support
	protected CacheSerializer getCacheSerializer() {
		return BitmapSerializer.instance();
	}
	
		
}
