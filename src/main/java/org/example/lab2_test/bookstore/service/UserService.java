package org.example.lab2_test.bookstore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lab2_test.bookstore.entity.Enum.Role;
import org.example.lab2_test.bookstore.entity.User;
import org.example.lab2_test.bookstore.repository.UserRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DatabaseClient databaseClient;

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Mono<User> createUser(User user) {
        return userRepository.findByUsername(user.getUsername())
                .flatMap(existingUser -> Mono.error(new RuntimeException("Username already exists!")))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            user.setPassword(passwordEncoder.encode(user.getPassword()));
                            return userRepository.save(user)
                                    .flatMap(savedUser -> userRepository.findByUsername(savedUser.getUsername())
                                            .flatMap(fetchedUser -> {
                                                if (fetchedUser.getId() == null) {
                                                    return Mono.error(new RuntimeException("User ID still not found after fetch!"));
                                                }
                                                return assignRolesToUser(fetchedUser).then(Mono.just(fetchedUser));
                                            })
                                    );
                        })
                ).cast(User.class);
    }


    private Mono<Void> assignRolesToUser(User user) {
        return Flux.fromIterable(user.getRoles())
                .flatMap(role -> assignRoleToUser(user.getId(), role))
                .then();
    }

    private Mono<Void> assignRoleToUser(Long userId, Role role) {
        String query = """
        INSERT INTO user_roles (user_id, role_id)
        SELECT $1, r.id
        FROM roles r
        WHERE r.name = $2
    """;
        return databaseClient.sql(query)
                .bind("$1", userId) // Ensure userId is not null
                .bind("$2", role.name())
                .then();
    }

    public Mono<User> updateUser(Long id, User user) {
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setUsername(user.getUsername());
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setPassword(user.getPassword());
                    return userRepository.save(existingUser);
                });
    }

    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }
}
