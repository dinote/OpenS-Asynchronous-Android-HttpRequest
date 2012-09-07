package opens.components.http.core;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

/**
 * Implements a target and action delegation (call-back) mechanism.
 * Target is the object instance, and action is the string name of
 * object method to be called. 
 * 
 * Target method can be private, protected or public
 * 
 * For concrete HttpRequest like ImageRequest the target method ("onSuccess") may have the following 
 * signatures:
 * 
 * onSuccess() 	//onSuccess will be called but no parameters will be passed. It has sense to use this method
 * 				//signature on onStart and onFinish targets
 * 
 * onSuccess(ImageRequest request) //Probably best to use this signature in the onSuccess method
 * 
 * onSuccess(HttpRequest request) 	//Instead of HttpRequest, any super-class of ImageRequest can be used. 
 * 									//Makes sense to use this signature in the onError handler 
 * 									//when consolidating error handling for multiple concrete requests. 
 * 
 * 
 * @author Vatroslav Dino Matijas
 */
public class HttpRequestHandler extends Handler {
	
	public static class TargetAction {
		
		private WeakReference<Object> target;
		
		private String action;
		
		public TargetAction(Object target, String action) {
			this.target = new WeakReference<Object>(target);
			this.action = action;
		}
				
	}
	
	private TargetAction onSuccess;
	
	private TargetAction onError;
	
	private TargetAction onStart;
	
	private TargetAction onFinish;
	
	@Override
	public void handleMessage(Message message) {
		int what = message.what;
		TargetAction selectedAction = null;
		switch (what) {
			case HttpRequest.REQUEST_FINISHED:
				selectedAction = onFinish;
				break;
			case HttpRequest.REQUEST_STARTED:
				selectedAction = onStart;
				break;
			case HttpRequest.REQUEST_ERROR:
				selectedAction = onError;
				break;
			case HttpRequest.REQUEST_SUCCESS:
				selectedAction = onSuccess;
				break;
		}
	}
	
}
