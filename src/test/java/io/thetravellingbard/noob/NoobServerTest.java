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

import java.util.ArrayList;
import java.util.EnumSet;

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
}
