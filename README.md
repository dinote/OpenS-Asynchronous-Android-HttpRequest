OpenS Ð Asynchronous Android HttpRequest 
=================

OpenS HttpRequest is an asynchronous http client for android with caching, connection throttling and a simple to use callback mechanism. Requests are performed outside the main UI thread, but callbacks are invoked in the same thread that the request was created on. Instead of using the anonymous callbacks, it uses a simpler target-action callback mechanism that allows you to use any method as the callback.

Removed the simple app from project, check the repository of example here:
https://github.com/leonardoxh/Asyncrounous-Http-Requests-example

Other methods will be removed the class HttpRequestHandler don't work more it will be removed soon,
Check the callbacks onErrorCallBack, onStartCallBack, onFinishCallBack and the others.

The JSONRequests is not stable yet

Features
-----
+ A new mechanism to use the callbacks now they can be used on HttpRequest class
+ **Asynchronous** http request invoked **outside the UI thread**
+ Handle the **response in plain methods** (no anonymous callbacks)
+ Transparent **in memory, to file, or multi-level caching**
+ **Image** and **JSON** requests
+ **Connection throttling** with max concurrent requests</li>
+ Thread pool to **minimize resource usage**
+ Includes a **lazy loaded image view** (RemoteImageView)
