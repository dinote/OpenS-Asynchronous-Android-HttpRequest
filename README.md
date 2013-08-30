OpenS Asynchronous Android HttpRequest 
=================

OpenS HttpRequest is an asynchronous http client for android, connection throttling and a simple to use callback mechanism. Requests are performed outside the main UI thread, but callbacks are invoked in the same thread that the request was created on. using the anonymous callbacks.

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
- Maven
- Cookies support

=================
  Copyright [2011-2013] [Leonardo Rossetto]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
