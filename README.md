## NoobServer

### The Why?
We spend all our lives making and fixing code but sometimes it's nice to stop and think about how it all
really works. This is my attempt to create a super simple web server with routing using only the standard libs
that ship with Java.

### The What?
A very simple Java Socket based WebServer. Runs on port 8000. When it receives a request from a client
it reads the request from the input stream and sends a simple response back. Uses a thread pool and processes
each request on its own thread.

### The When?
It's a work in progress. I'm doing it for fun and re-education. Is anything every finished. It's not recommended
for any sort of production system but only for play and learning.

### Licence
Apache 2. licence.txt included in the root dir.