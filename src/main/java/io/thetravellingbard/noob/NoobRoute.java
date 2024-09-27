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

import java.util.ArrayList;
import java.util.List;

/**
 * Defines an HTTP route that the server can respond to. Each route contains
 * a path eg '/a/path', the HTML that it can return and the type of HTTP request it can respond to
 * eg GET, POST
 */
public abstract class NoobRoute {
    private final String route;
    private final List<HttpVerb> httpVerbs;

    /**
     * A route handles GET requests by default
     * @param route String
     */
    public NoobRoute(String route) {
        this.route = route;
        ArrayList<HttpVerb> defaultHttpVerb = new ArrayList<HttpVerb>();
        defaultHttpVerb.add(HttpVerb.GET);
        this.httpVerbs = defaultHttpVerb;
    }

    public NoobRoute(String route, List<HttpVerb> httpVerbs) {
        this.route = route;
        this.httpVerbs = httpVerbs;
    }

    public String getRoute() {
        return this.route;
    }

    /**
     * Can the route handle this type of HTTP request
     * @param httpVerb The HTTP verb that the request is using
     * @return Boolean
     */
    public Boolean allowsVerb(HttpVerb httpVerb) {
        return httpVerbs.contains(httpVerb);
    }

    /**
     * the HTML that the route will return
     * @return String
     */
    public abstract String getHTML();
}
