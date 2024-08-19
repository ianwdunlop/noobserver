package io.thetravellingbard.noob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Reads the input from a client request and sends a response.
 */
class SocketConnectionTask implements Runnable {

    private final Socket clientSocket;

    SocketConnectionTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Reads the input stream from a client request. Sends it a simple response and then
     * closes the socket.
     */
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
            OutputStream outputStream = clientSocket.getOutputStream();
            outputStream.write(("HTTP/1.1 200 OK\n\nHello from the Noob").getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
