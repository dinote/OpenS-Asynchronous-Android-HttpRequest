package com.opens.components.core;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;

/**
 * The Queue class to manage the requests start and finish
 * manage the threading concurrence the default limiter of requests is 4
 * @see #setMaxConcurrent(int)
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 * @since API Version: 1.0
 */
public final class RequestQueue {
	
	/** For performance use only one instance of queue */
	private static RequestQueue instance;
	/** The active connections */
	private final LinkedList<HttpBaseRequest> active;
	/** The remaining connections */
	private final LinkedList<HttpBaseRequest> pending;
	/** The service executor */
	private final ExecutorService pool;
	/** Number of max connections */
	private int maxConcurrent;
	/** Indicate the all downloads are finished */
	private RequestQueue.OnRequestFinish finish;
	/** Enable the log messages */
	private boolean debug;
	/**
	 * Indicate the default request limit
	 * <br>Constant value: {@value}
	 */
	public static final int DEFAULT_REQUEST_LIMIT = 4;
	
	/**
	 * Construct 
	 * @see {@link #getInstance()}
	 */
	public RequestQueue() {
		this(RequestQueue.DEFAULT_REQUEST_LIMIT);
	}
	
	/**
	 * Construct
	 * @param maxConcurrent the maxConcurrent threads
	 * @see {@link #getInstance()}
	 */
	public RequestQueue(int maxConcurrent) {
		this.active = new LinkedList<HttpBaseRequest>();
		this.pending = new LinkedList<HttpBaseRequest>();
		this.pool = Executors.newCachedThreadPool();
		this.maxConcurrent = maxConcurrent;
	}
	
	/**
	 * Return the instance of queue to perform the requests
	 * @return the unique instance to perform the requests
	 */
	public static RequestQueue getInstance() {
		if(RequestQueue.instance == null)
			RequestQueue.instance = new RequestQueue();
		return RequestQueue.instance;
	}
	
	/**
	 * Start the next action if and only if is necessary 
	 * @param action the action to start
	 */
	public synchronized void push(HttpBaseRequest action) {
		this.pending.add(action);
		if(this.active.size() < this.maxConcurrent)
			this.startNext();
	}
	
	/**
	 * Start the next actions if and only if is necessary 
	 * @param actions All actions to start
	 */
	public synchronized void push(Collection<? extends HttpBaseRequest> actions) {
		this.pending.addAll(actions);
		if(this.active.size() < this.maxConcurrent)
			this.startNext();
	}
	
	/**
	 * Start the actions previously added
	 * @see #addToQueue()
	 */
	public synchronized void push() {
		if(this.active.size() < this.maxConcurrent)
			this.startNext();
	}
	
	/**
	 * Add one request to the queue
	 */
	public void addToQueue(HttpBaseRequest action) {
		this.pending.add(action);
	}
	
	/**
	 * Return the active size in the stack
	 * @return the active size in the stack
	 */
	public int getActiveSize() {
		return this.active.size();
	}
	
	/**
	 * Return the pending size in the stack
	 * @return the pending size in the stack
	 */
	public int getPendingSize() {
		return this.pending.size();
	}
	
	/**
	 * Start the next connection in the stack
	 */
	private synchronized void startNext() {
		this.log();
		if(!this.pending.isEmpty()) {
			HttpBaseRequest newActive = this.pending.remove(0);
			this.active.add(newActive);
			this.pool.execute(new OperationTask(newActive, this));
		}
		else {
			this.active.clear();
			if(this.finish != null)
				this.finish.onFinish();
		}
	}
	
	/**
	 * The response method, to indicate this task has completed
	 * @param completed the completed task
	 */
	private synchronized void operationComplete(Runnable completed) {
		this.active.remove(completed);
		this.startNext();
	}
	
	/**
	 * Return the number of max connections to run
	 * @return the max connections permitted
	 */
	public int getMaxConcurrent() {
		return this.maxConcurrent;
	}
	
	/**
	 * Set the max concurrent connections to run
	 * @param maxConcurrent the number of connections
	 */
	public void setMaxConcurrent(int maxConcurrent) {
		this.maxConcurrent = maxConcurrent;
	}
	
	/**
	 * Enable the log on ADT
	 */
	public void enableDebug() {
		this.debug = true;
	}
	
	/**
	 * Set the callback to response when all connections are finished
	 * @param finishListener the listener to trigger when all connections are finished
	 */
	public void setAllRequestsFinishListener(RequestQueue.OnRequestFinish finishListener) {
		this.finish = finishListener;
	}
	
	/**
	 * Interface to indicate all downloads are finished
	 * @author Leonardo Rossetto <leonardoxh@gmail.com>
	 * @since API Version: 1.0
	 */
	public static interface OnRequestFinish {
		
		/**
		 * Finish all the downloads
		 */
		public abstract void onFinish();
	
	}
	
	/**
	 * Run espesific task and return to delegated task completed method
	 * @author Leonardo Rossetto <leonardoxh@gmail.com>
	 * @since API Version: 1.0
	 */
	private static class OperationTask implements Runnable {
		
		/** Task to run */
		private final HttpBaseRequest task;
		/** The handle to recive the response */
		private final RequestQueue delegate;
		
		/**
		 * Construct
		 * @param task runnable to run
		 * @param delegate class to triger the response method 
		 */
		public OperationTask(HttpBaseRequest task, RequestQueue delegate) {
			this.task = task;
			this.delegate = delegate;
		}
		
		public void run() {
			this.task.run();
			this.delegate.operationComplete(this.task);
		}
		
	}
	
	/**
	 * Simple log method
	 */
	private void log() {
		if(this.debug) {
			Log.d("RequestQueue", "Pending threads: " + this.pending.size());
			Log.d("RequestQueue", "Active threads: " + this.active.size());
			Log.d("RequestQueue", "Max connections: " + this.maxConcurrent);
		}
	}
	
}