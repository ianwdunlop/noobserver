package io.thetravellingbard.noob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class NoobServer {

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws IOException {
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
}
