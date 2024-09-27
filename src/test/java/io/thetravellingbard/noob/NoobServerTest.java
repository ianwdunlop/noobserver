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

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoobServerTest {

    @Test
    void testRequest() {
        NoobRoute noobRoute = new NoobRoute("/a/page") {
            @Override
            public String getHTML() {
                return "";
            }
        };
        assertEquals(noobRoute.getRoute(), "/a/page");
    }

    @Test
    void testRouteHTML() {
        NoobRoute noobRoute = new NoobRoute("/another/page") {
            @Override
            public String getHTML() {
                return "<html><head></head><body><p>A paragraph</p></body></html>";
            }
        };
        assertEquals(noobRoute.getHTML(), "<html><head></head><body><p>A paragraph</p></body></html>");
    }

    @Test
    void testRegistry() {
        NoobRoute noobRoute = new NoobRoute("/a/page") {
            @Override
            public String getHTML() {
                return "<html><head></head><body><p>A page</p></body></html>";
            }
        };
        NoobRoute noobRoute2 = new NoobRoute("/another/page") {
            @Override
            public String getHTML() {
                return "<html><head></head><body><p>Another page</p></body></html>";
            }
        };
        NoobRoute noobRoute3 = new NoobRoute("/yet/another/page") {
            @Override
            public String getHTML() {
                return "<html><head></head><body><p>Yet another page</p></body></html>";
            }
        };
        NoobRouteRegistry noobRouteRegistry = NoobRouteRegistry.getRouteRegistry();
        noobRouteRegistry.put(noobRoute.getRoute(), noobRoute);
        noobRouteRegistry.put(noobRoute2.getRoute(), noobRoute2);
        noobRouteRegistry.put(noobRoute3.getRoute(), noobRoute3);
        assertTrue(noobRouteRegistry.containsKey("/a/page"));
        assertTrue(noobRouteRegistry.containsKey("/another/page"));
        assertTrue(noobRouteRegistry.containsKey("/yet/another/page"));
        assertEquals(noobRouteRegistry.get("/a/page"), noobRoute);
        assertEquals(noobRouteRegistry.get("/another/page"), noobRoute2);
        assertEquals(noobRouteRegistry.get("/yet/another/page"), noobRoute3);
    }

    @Test
    void testHttpVerbs() {
        ArrayList<HttpVerb> httpVerbs = new ArrayList<HttpVerb>();
        httpVerbs.add(HttpVerb.GET);
        httpVerbs.add(HttpVerb.POST);
        NoobRoute noobRoute = new NoobRoute("/a/page", httpVerbs) {
            @Override
            public String getHTML() {
                return "<html><head></head><body><p>A page</p></body></html>";
            }
        };
        NoobRouteRegistry noobRouteRegistry = NoobRouteRegistry.getRouteRegistry();
        noobRouteRegistry.put(noobRoute.getRoute(), noobRoute);
        assertTrue(noobRouteRegistry.get("/a/page").allowsVerb(HttpVerb.GET));
        assertTrue(noobRouteRegistry.get("/a/page").allowsVerb(HttpVerb.POST));
    }

    @Test
    void testNoobRequest() throws IOException {
        String requestDetails = "GET /info HTTP/1.1\n" +
                "Host: localhost:8000\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:129.0) Gecko/20100101 Firefox/129.0\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/png,image/svg+xml,*/*;q=0.8\n" +
                "Accept-Language: en-GB,en;q=0.5\n" +
                "Accept-Encoding: gzip, deflate, br, zstd\n" +
                "Connection: keep-alive\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Sec-Fetch-Dest: document\n" +
                "Sec-Fetch-Mode: navigate\n" +
                "Sec-Fetch-Site: none\n" +
                "Sec-Fetch-User: ?1\n" +
                "Priority: u=0, i";
        InputStream stream = new ByteArrayInputStream(requestDetails.getBytes(StandardCharsets.UTF_8));
        NoobRequest noobRequest = new NoobRequest(stream);
        stream.close();
        assertEquals(noobRequest.httpVerb, HttpVerb.GET);
        assertEquals(noobRequest.requestPath, "/info");
        assertEquals(noobRequest.protocol, "HTTP/1.1");
        assertEquals(String.join("\n", noobRequest.requestParams), requestDetails);
    }
}
