package opens.components.http.core;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Used to regulate the maximal number of concurrent operations.
 * Operation is any class that implements Runnable. 
 * Thread pool is used to cap the use of resources. 
 * 
 * Limiting the maximal number of concurrent operations is an important
 * performance feature. 
 * 
 * When request is pushed into the queue it is not immediately started.
 * Instead, it is placed into the pending list. When the active list count falls
 * below the maximal concurrent operations items from pending list are started 
 * and placed into the active list.
 * 
 * 
 * 
 * @author Vatroslav Dino Matijas
 *
 */
public class RequestQueue {
	
	private class OperationTask implements Runnable {
		
		private Runnable task;
		
		private RequestQueue delegate;
		
		
		public OperationTask(Runnable task, RequestQueue delegate) {		
			this.task = task;
			this.delegate = delegate;
		}


		public void run() {
			task.run();
			delegate.operationComplete(this.task);
		}
		
	}
	
	private ExecutorService pool;
	
	private LinkedList<Runnable> active;
	
	private LinkedList<Runnable> pending;
	
	private int maxConcurrent = 4;
	
	private Object targetClass;
	
	private String targetAction;
	
	public int getMaxConcurrent() {
		return maxConcurrent;
	}

	public void setMaxConcurrent(int maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}

	private static RequestQueue instance;
	
	public RequestQueue() {
		active = new LinkedList<Runnable>();
		pending = new LinkedList<Runnable>();
		this.pool = Executors.newCachedThreadPool();
	}
	
	public static RequestQueue instance() {
		if (instance == null) {
			instance = new RequestQueue();
		}
		return instance;
	}
	
	synchronized public void push(Runnable action) {
		pending.add(action);
		if (active.size() < maxConcurrent) {
			startNext();
			
		}
	}
	
	/**
	 * Set a callback to all downloads finished
	 * @param target object of atual class
	 * @param action method of atual class
	 */
	public void setOnFinishAllDownloadsCallBack(Object target, String action) {
		this.targetClass = target;
		this.targetAction = action;
	}
	
	/**
	 * Notify this call back to indicate the finish of all downloads
	 * TODO - Suport to parameters
	 * @throws Exception if any error has ocurred
	 * @author Leonardo Rossetto <leonardoxh@gmail.com>
	 */
	public synchronized void notifyHasFinish() throws Exception {
		if(this.targetClass != null) {
			Class<?> target = this.targetClass.getClass();
			Method action = target.getMethod(this.targetAction);
			action.setAccessible(true);
			action.invoke(this.targetClass);
		}
	}
	
	synchronized private void startNext() {
		//Log.d("Teste>", "Starting runnable with active count: " + active.size());
		if (!pending.isEmpty()) {
			Runnable newActive = pending.get(0);
			pending.remove(0);
			active.add(newActive);
			pool.execute(new OperationTask(newActive, this));
		}
		if(active.size() == 0) {
			/**
			 * Notify all downloads are done
			 */
			try {
				this.notifyHasFinish();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	synchronized public void operationComplete(Runnable complete) {
		active.remove(complete);
		startNext();
	}
}
