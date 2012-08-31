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
- Don't need evoke outside the UI thread Thread controlling
- Stable asynchronous JSON requests
- More simple usage **less classes**
- More eficient memory controll
