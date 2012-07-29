package opens.components.http.core;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;


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
 *
 */
public class HttpRequestHandler extends Handler {
	
	public static class TargetAction {
		
		public TargetAction(Object target, String action) {
			super();
			this.target = new WeakReference<Object>(target);
			this.action = action;
		}
		
		private WeakReference<Object> target;
		
		private String action;
		
		@SuppressWarnings("all")		
		public void invokeWithParam(Object param) {
			Object target = this.target.get();
			Class targetClass = target.getClass();
			try {
				Method[] methods = targetClass.getDeclaredMethods();
				for (Method method : methods) {
					if (method.getName().equals(action) == false) {
						continue;
					}
					Class[] parameterTypes = method.getParameterTypes();
					if (parameterTypes.length == 0) {
						method.setAccessible(true);
						method.invoke(target);
						break;
					} 
					else if (parameterTypes.length > 1) {
						continue;
					}
					Class parameterType = parameterTypes[0];
					if (parameterType.isAssignableFrom(param.getClass())) {
						method.setAccessible(true);
						method.invoke(target, parameterType.cast(param));
						break;
					}					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				//TODO log
				throw new IllegalArgumentException();
			}
		}
				
	}
	
	private TargetAction onSuccess;
	
	private TargetAction onError;
	
	private TargetAction onStart;
	
	private TargetAction onFinish;
	
	public HttpRequestHandler() {
		
	}
		
	
	
	public void setOnSuccess(TargetAction onSuccess) {
		this.onSuccess = onSuccess;
		onSuccess.invokeWithParam(this.onSuccess);
	}

	public void setOnError(TargetAction onError) { //isso não funciona
		this.onError = onError;
		//this.onError.invokeWithParam(this.onError);
	}



	public void setOnStart(TargetAction onStart) {
		this.onStart = onStart;
	}



	public void setOnFinish(TargetAction onFinish) {
		this.onFinish = onFinish;
		onFinish.invokeWithParam(onFinish);
	}


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
		
		if (selectedAction != null) {
			selectedAction.invokeWithParam(message.obj);
		}
	}
	
}
