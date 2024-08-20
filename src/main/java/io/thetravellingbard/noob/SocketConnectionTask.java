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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.*;
import java.util.stream.Collectors;

/**
 * Reads the input from a client request and sends a response.
 */
class SocketConnectionTask implements Runnable {

    private final Socket clientSocket;
    private static final Logger logger = Logger.getLogger("io.thetravellingbard.noob.SocketConnectionTask");
    private final NoobRouteRegistry registry;

    SocketConnectionTask(Socket clientSocket, NoobRouteRegistry registry) {
        this.clientSocket = clientSocket;
        this.registry = registry;
    }

    /**
     * Reads the input stream from a client request. Sends it a simple response and then
     * closes the socket.
     */
    @Override
    public void run() {
        ArrayList<String> requestParams = new ArrayList<String>();
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
            requestParams.add(inputString);
            System.out.println(inputString);
            if (inputString.isEmpty()) {
                break;
            }
        }
        List<String> matches = requestParams.stream().filter(it -> it.contains("GET")).collect(Collectors.toList());
        String requestRoute = matches.get(0).replace("GET", "").replace("HTTP/1.1", "").trim();
        try {
        OutputStream outputStream = clientSocket.getOutputStream();
            if (!registry.getRoutes().isEmpty() && registry.getRoutes().containsKey(requestRoute)) {
                String requestedPage = registry.getRoutes().get(requestRoute).getHTML();
                outputStream.write(("HTTP/1.1 200 OK\n\n" + requestedPage).getBytes());
            } else {
                outputStream.write(("HTTP/1.1 404 OK\n\n404 Could not find the requested path on the server").getBytes());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            logger.severe(Arrays.toString(e.getStackTrace()));
        }
    }
}