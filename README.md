OpenS Ð Asynchronous Android HttpRequest 
=================

OpenS HttpRequest is an asynchronous http client for android with caching, connection throttling and a simple to use callback mechanism. Requests are performed outside the main UI thread, but callbacks are invoked in the same thread that the request was created on. Instead of using the anonymous callbacks, it uses a simpler target-action callback mechanism that allows you to use any method as the callback.

To get started -- checkout http://dinote.wordpress.com/opens-asynchronous-android-httprequest/


Features
-----
+ **Asynchronous** http request invoked **outside the UI thread**
+ Handle the **response in plain methods** (no anonymous callbacks)
+ Transparent **in memory, to file, or multi-level caching**
+ **Image** and **JSON** requests
+ **Connection throttling** with max concurrent requests</li>
+ Thread pool to **minimize resource usage**
+ Includes a **lazy loaded image view** (RemoteImageView)