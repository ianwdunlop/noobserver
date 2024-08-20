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

import java.util.HashMap;

/**
 * Contains all the different routes that the server can route to
 */
public class NoobRouteRegistry {

    private static NoobRouteRegistry routeRegistry = null;

    private HashMap<String, NoobRoute> routes = new HashMap<String, NoobRoute>();

    /**
     * Private constructor so we only create one instance
     */
    private NoobRouteRegistry() {}

    /**
     * Gets a singleton instance of the registry
     * @return routeRegistry
     */
    public static NoobRouteRegistry getRouteRegistry() {
        if (routeRegistry == null) {
            synchronized (NoobRouteRegistry.class) {
                routeRegistry = new NoobRouteRegistry();
            }
        }
        return routeRegistry;
    }

    /**
     * Add a route to the registry
     * @param route NoobRoute
     */
    public void addRoute(NoobRoute route) {
        routes.put(route.getRoute(), route);
    }

    /**
     * Get all of the routes that the registry contains.
     * The key is the route path and the value the actual NoobRoute
     * @return routes
     */
    public HashMap<String, NoobRoute> getRoutes() {
        return routes;
    }
}
