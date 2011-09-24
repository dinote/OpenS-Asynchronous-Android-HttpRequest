package opens.components.view;


import opens.components.cache.Cache;
import opens.components.http.ImageRequest;
import opens.components.http.core.HttpRequest;
import opens.components.http.core.RequestQueue;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * Use the loadFromUrl method to asynchronously load the image from URL.
 * 
 * When the image is loaded, it can be animated into the view by setting
 * the appearAnimation. 
 * 
 * @author Vatroslav Dino Matijas
 *
 */
public class RemoteImageView extends ImageView {

	private Animation appearAnimation;
	
	private HttpRequest request;
		
	public RemoteImageView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}
	
	public RemoteImageView(Context context) {
		super(context);
	}	

	public void setAppearAnimation(Animation appearAnimation) {
		this.appearAnimation = appearAnimation;
	}
	
	public void onImageRequestSuccess(ImageRequest request) {
		Bitmap bitmap = request.getResponseObject();			
		setImageBitmap(bitmap);		    	
	    if (appearAnimation != null) {
	    	startAnimation(appearAnimation);
	    }		
		this.request = null;
	}
	
	public void loadFromUrl(String url, Cache cache) {
		ImageRequest imageRequest = new ImageRequest();
		imageRequest.onSuccess(this, "onImageRequestSuccess");
		imageRequest.setCache(cache);
		this.request = imageRequest;
		RequestQueue.instance().push(request.get(url));
	}	
	
}
