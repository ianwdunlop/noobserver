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
import java.util.ArrayList;

/**
 * Creates and starts a NoobServer
 */
public class RunServer {

    public static void main(String[] args) {
        ArrayList<HttpVerb> allowedHttpVerbsForHome = new ArrayList<HttpVerb>();
        allowedHttpVerbsForHome.add(HttpVerb.GET);
        NoobRoute homeRoute = new NoobRoute("/", allowedHttpVerbsForHome) {
            @Override
            public String getHTML() {
                return "Hello from your home page";
            }
        };
        ArrayList<HttpVerb> allowedHttpVerbsForInfo = new ArrayList<HttpVerb>();
        allowedHttpVerbsForInfo.add(HttpVerb.GET);
        NoobRoute infoRoute = new NoobRoute("/info", allowedHttpVerbsForInfo) {
            @Override
            public String getHTML() {
                return "This page contains some info";
            }
        };
        NoobRouteRegistry.getRouteRegistry().put(homeRoute.getRoute(), homeRoute);
        NoobRouteRegistry.getRouteRegistry().put(infoRoute.getRoute(), infoRoute);
        NoobServer noobServer = new NoobServer(NoobRouteRegistry.getRouteRegistry());
        noobServer.startServer();
    }
}
