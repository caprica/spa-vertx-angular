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

package uk.co.caprica.spa.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import uk.co.caprica.spa.domain.User;
import uk.co.caprica.spa.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business service dealing with Users.
 */
public class UserService extends AbstractVerticle {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public void start() {
        vertx.eventBus().consumer("users.all").handler(this::users);
        vertx.eventBus().consumer("users.one").handler(this::user);
    }

    private void users(Message<Object> message) {
        List<User> users = userRepository.readUsers();
        // We manually construct an array of JSON objects from the list since Json.encode would do some escaping of
        // quote characters that we don't want - an alternative would be to create a wrapper object the for list (but at
        // least with streams this is not so bad)
        message.reply(new JsonArray(users
            .stream()
            .map(JsonObject::mapFrom)
            .collect(Collectors.toList())
        ));
    }

    private void user(Message<Object> message) {
        String username = message.body().toString();
        Optional<User> optionalUser = userRepository.readUser(username);
        if (optionalUser.isPresent()) {
            message.reply(JsonObject.mapFrom(optionalUser.get()));
        } else {
            message.fail(404, "No such user");
        }
    }

}
