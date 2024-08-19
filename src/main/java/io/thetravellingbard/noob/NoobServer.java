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
public class NoobServer implements Runnable{

    private final ExecutorService clientSocketThreadPool;
    private ServerSocket serverSocket;
    private Boolean isRunning = false;
    protected Thread runningThread= null;

    /**
     * Create the server on port 8000 and start it on a new Thread
     * @param args
     */
    public static void main(String[] args) {
        NoobServer noobServer = new NoobServer();
        new Thread(noobServer).start();
    }

    /**
     * Create a Thread pool to handle up to 5 requests at a time
     */
    public NoobServer() {
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
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                clientSocketThreadPool.submit(new SocketConnectionTask(clientSocket));
            }
            clientSocketThreadPool.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void stop() {
        isRunning = false;
    }
}
