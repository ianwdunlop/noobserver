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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

public class NoobRequest {

    private static final Logger logger = Logger.getLogger("io.thetravellingbard.noob.NoobRequest");

    ArrayList<String> requestParams = new ArrayList<String>();
    HttpVerb httpVerb;
    String requestPath;
    String protocol;
    public NoobRequest (InputStream clientInputStream) {
        try {
            readRequestStream(clientInputStream);
            String[] routeInfo = requestParams.get(0).split(" ");
            httpVerb = HttpVerb.valueOf(routeInfo[0]);
            requestPath = routeInfo[1];
            protocol = routeInfo[2];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readRequestStream(InputStream clientInputStream) throws IOException {
        BufferedReader inputReader = null;
        inputReader = new BufferedReader(new InputStreamReader(clientInputStream));
        String inputString;
        while (true) {
            try {
                if ((inputString = inputReader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            requestParams.add(inputString);
            if (inputString.isEmpty()) {
                break;
            }
        }
        logger.info(String.join("\n", requestParams));
    }
}
