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
        noobRouteRegistry.addRoute(noobRoute);
        noobRouteRegistry.addRoute(noobRoute2);
        noobRouteRegistry.addRoute(noobRoute3);
        assertTrue(noobRouteRegistry.getRoutes().containsKey("/a/page"));
        assertTrue(noobRouteRegistry.getRoutes().containsKey("/another/page"));
        assertTrue(noobRouteRegistry.getRoutes().containsKey("/yet/another/page"));
        assertEquals(noobRouteRegistry.getRoutes().get("/a/page"), noobRoute);
        assertEquals(noobRouteRegistry.getRoutes().get("/another/page"), noobRoute2);
        assertEquals(noobRouteRegistry.getRoutes().get("/yet/another/page"), noobRoute3);
    }
}
