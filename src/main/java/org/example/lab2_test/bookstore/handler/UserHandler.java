package org.example.lab2_test.bookstore.handler;

import lombok.RequiredArgsConstructor;
import org.example.lab2_test.bookstore.entity.Enum.Role;
import org.example.lab2_test.bookstore.entity.User;
import org.example.lab2_test.bookstore.repository.UserRepository;
import org.example.lab2_test.bookstore.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserRepository userRepository;
    private final UserService userService;

    public Mono<ServerResponse> getUsers(ServerRequest request) {
        return userRepository.findAll()
                .collectList()
                .flatMap(users -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(users))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> registerUser(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(dto -> {
                    // Assign default role if not provided
                    if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
                        dto.setRoles(Set.of(Role.USER));
                    }
                    return userService.createUser(dto);
                })
                .flatMap(savedUser -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("message", "User registered successfully")))
                .onErrorResume(e -> ServerResponse
                        .status(HttpStatus.BAD_REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(Map.of("errors", e.getMessage())));
    }

}
