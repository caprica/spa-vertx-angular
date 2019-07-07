/*
 * This file is part of Spa.
 *
 * Copyright (C) 2019
 * Caprica Software Limited (capricasoftware.co.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
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

package uk.co.caprica.spa.handler;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

/**
 * Handler implementations for dealing with Users.
 */
public class UsersHandler {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private static final String JSON_CONTENT_TYPE = "application/json; charset=UTF-8";

    public static void users(RoutingContext routingContext) {
        // Simulate a delay
        try {
            Thread.currentThread().sleep(1000);
        }
        catch (Exception e) {
        }

        routingContext.vertx().eventBus().send("users.all", null, result -> {
            if (result.succeeded()) {
                routingContext.response()
                    .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
                    .setStatusCode(200)
                    .end(Json.encode(result.result().body()));
            } else {
                routingContext.response().setStatusCode(500).end();
            }
        });
    }

    public static void user(RoutingContext routingContext) {
        // Simulate a delay
        try {
            Thread.currentThread().sleep(1000);
        }
        catch (Exception e) {
        }

        String username = routingContext.request().getParam("username");
        routingContext.vertx().eventBus().send("users.one", username, result -> {
            if (result.succeeded()) {
                routingContext.response()
                    .putHeader(CONTENT_TYPE_HEADER, JSON_CONTENT_TYPE)
                    .setStatusCode(200)
                    .end(Json.encode(result.result().body()));
            } else {
                routingContext.response()
                    .setStatusCode(404)
                    .end();
            }
        });
    }

}
