package com.example.asynchttprequesterdemo;

import java.io.InputStream;
import java.util.ArrayList;

import com.opens.components.core.RequestQueue;
import com.opens.components.response.BynaryResponse;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.app.Activity;

/**
 * Show who the OpenSComponnets class works
 * @author Leonardo Rossetto <leonardoxh@gmail.com>
 */
public class MainActivity extends Activity implements RequestQueue.OnRequestFinish {

	private static final String TAG = "HttpRequesterSample";
	
	private TextView maxConnections;
	private TextView totalFiles;
	private TextView allDone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);
		this.totalFiles = (TextView) this.findViewById(R.id.total_files);
		this.maxConnections = (TextView) this.findViewById(R.id.max_conn);
		this.allDone = (TextView) this.findViewById(R.id.all_done);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		/* Not necessary do this in onPostCreate, but I like separate the onCreate
		 * only for init the view elements and the post create for the "actions" */
		
		//Set the max request
		RequestQueue.getInstance().setMaxConcurrent(20);
		this.maxConnections.setText("Max Connections: " + RequestQueue.getInstance().getMaxConcurrent());
		//Set the listener will be called when all requests are finished
		RequestQueue.getInstance().setAllRequestsFinishListener(this);
		//My request are simple InputStream return I can use it to serialize into files for example
		ArrayList<BynaryResponse> downloads = new ArrayList<BynaryResponse>();
		for(String url : Constant.TEST_FILES)
			//No parameters just a simple get request, all the request if not specified are get by default
			downloads.add(new BynaryResponse(url) {

				//Perform this OverrideOnTheFly
				
				@Override
				protected void onSuccess(InputStream response) {
					super.onSuccess(response);
					Log.d(MainActivity.TAG, "RESPONSE WAS RECIVED WITH SUCCESS");
				}

				@Override
				protected void onError(Exception e) {
					super.onError(e);
					Log.d(MainActivity.TAG, "Sorry we not conclude the request", e);
				}

				@Override
				protected void onFinish() {
					super.onFinish();
					Log.d(MainActivity.TAG, "Request finished tanks a lot");
				}

				@Override
				protected void onStart() {
					super.onStart();
					Log.d(MainActivity.TAG, "Started a request: " + this.getUrl());
				}});
		this.totalFiles.setText("Total files to download: " + downloads.size());
		//I wanna see the logs, the threads stack
		RequestQueue.getInstance().enableDebug();
		//OK all setted let's start
		RequestQueue.getInstance().push(downloads);
		/* Rememeber the onFinish() will called when all requests are finished. And the onFinish
		 * (HttpBaseRequest) will be called when a single request is finished, you can Override
		 * any of super class methods. */
	}

	@Override
	public void onFinish() {
		Log.d(MainActivity.TAG, "Well done all downloads are finished");
		//why not discomment the above line? - We have a bug here this call back works... But is called
		//on the worker thread and he must be called on UI thread realy I need see who this work
		//this.allDone.setText("ALL DOWNLOADS DONE :)");
	}

}
