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

package uk.co.caprica.spa;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import uk.co.caprica.spa.handler.UsersHandler;
import uk.co.caprica.spa.repository.MemoryUserRepository;
import uk.co.caprica.spa.service.UserService;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {
        // Configure routes to handlers
        Router router = Router.router(vertx);

        // Handlers for our API services
        router.route("/api/users").handler(UsersHandler::users);
        router.route("/api/users/:username").handler(UsersHandler::user);

        // Catch-all for non-existent API routes to return a Bad Request status code
        router.route("/api/*").handler(routingContext -> routingContext.response().setStatusCode(400).end());

        // ReactJS initial artifacts, e.g. index.html, manifest, favicon etc, and also the ReactJS application's static
        // assets (in the ReactJS application there are in a "/static" sub-directory).
        router.route("/*").handler(StaticHandler.create("app"));

        // Catch-all route, anything unmatched is sent to the SPA main page for client-side routing (we can't just
        // redirect to "/" and use the prior static handler mapping as this would strip the request path required for
        // client-side routing)
        router.get().handler(routingContext -> routingContext.response().sendFile("app/index.html").end());

        // Register a business service
        vertx.deployVerticle(new UserService(new MemoryUserRepository()));

        // Create the server
        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

}

