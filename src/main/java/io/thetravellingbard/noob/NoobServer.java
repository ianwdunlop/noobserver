/*
 * Copyright 2024 Ian Dunlop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.thetravellingbard.noob;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple webserver that listens on port 8000 using a ServerSocket
 * and uses a thread pool to handle socket requests.
 */
public class NoobServer implements Runnable {

    private final ExecutorService clientSocketThreadPool;
    private NoobRouteRegistry registry = null;
    private ServerSocket serverSocket;
    private Boolean isRunning = false;

    /**
     * Start the server in a new thread
     */
    public void startServer() {
        new Thread(this).start();
    }

    /**
     * Create a Thread pool to handle up to 5 requests at a time. Add the routes that
     * the server can process
     */
    public NoobServer(NoobRouteRegistry routeRegistry) {
        this.registry = routeRegistry;
        clientSocketThreadPool = Executors.newFixedThreadPool(5);
    }

    /**
     * Creates a ServerSocket that listens on port 8080 and blocks until a
     * new request is received. When a request is received it submits it as
     * a new SocketConnectionTask to the thread pool
     */
    @Override
    public void run() {
        isRunning = true;
        try {
            serverSocket = new ServerSocket(8000);
            while (isServerRunning()) {
                Socket clientSocket = serverSocket.accept();
                clientSocketThreadPool.submit(new SocketConnectionTask(clientSocket, this.registry));
            }
            clientSocketThreadPool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stop the server by setting the isRunning var to false
     * which will cause the threadpool to shut down
     */
    public void stop() {
        isRunning = false;
    }

    public Boolean isServerRunning() {
        return isRunning;
    }
}
