package io.thetravellingbard.noob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoobServer {

    public static void main(String[] args) {
        try {
            NoobServer noobServer = new NoobServer();
            noobServer.startNoobServer();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void startNoobServer() throws IOException {
        final ExecutorService clientSocketThreadPool = Executors.newFixedThreadPool(5);
        Runnable serverTask = new Runnable() {

            @Override
            public void run() {
                try (ServerSocket serverSocket = new ServerSocket(8000)) {
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        clientSocketThreadPool.submit(new SocketConnectionTask(clientSocket));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(8000)) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String inputString;
                while ((inputString = inputReader.readLine()) != null) {
                    System.out.println(inputString);
                    if (inputString.isEmpty()) {
                        break;
                    }
                }
                clientSocket.close();
            }
        }
    }

    /**
     *
     */
    private class SocketConnectionTask implements Runnable {

        private final Socket clientSocket;

        private SocketConnectionTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader inputReader = null;
            try {
                inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String inputString;
            while (true) {
                try {
                    if ((inputString = inputReader.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(inputString);
                if (inputString.isEmpty()) {
                    break;
                }
            }
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
