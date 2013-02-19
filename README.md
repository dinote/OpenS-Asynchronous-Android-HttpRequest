OpenS Ð Asynchronous Android HttpRequest 
=================

OpenS HttpRequest is an asynchronous http client for android, connection throttling and a simple to use callback mechanism. Requests are performed outside the main UI thread, but callbacks are invoked in the same thread that the request was created on. Instead of using the anonymous callbacks, it uses a simpler target-action callback mechanism.

- All requests are made outside of your app's main UI thread, but any callback logic will be executed on the same thread as the callback was created using Android's Handler message passing.

For example, you have to download many files, just create an array with the response what you want like the BitmapResponse and perform the queue.

No More Magic!

Just copy this jar file to your /libs folder and start the work.

Features:
-----
- Make asynchronous HTTP requests, handle responses in anonymous callbacks
- HTTP requests happen outside the UI thread
- Requests use a threadpool to cap concurrent resource usage
- Perform many requests and recive the results individualy such as JSON, Bitmap, InputStream and String
- Requests are unique, so you can have diferents responses from diferents sites

To-do:
-----
- Memory Caching
- Parameters (files) - I thing this have no utilitie by the class idea but if any of kind soul implement this will be merged
- Website
- Jar file with build.xml
- Cookies support
