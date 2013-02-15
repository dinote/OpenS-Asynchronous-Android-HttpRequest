OpenS Ð Asynchronous Android HttpRequest 
=================

OpenS HttpRequest is an asynchronous http client for android, connection throttling and a simple to use callback mechanism. Requests are performed outside the main UI thread, but callbacks are invoked in the same thread that the request was created on. Instead of using the anonymous callbacks, it uses a simpler target-action callback mechanism.

For example, you have to download many files, just create an array with the response wath you want like the BitmapResponse and perform the queue, the examples will be provided soon as the class was finished.

Features
-----
- Memory Caching
- Parameters
