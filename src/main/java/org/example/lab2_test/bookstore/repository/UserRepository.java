package org.example.lab2_test.bookstore.repository;

import org.example.lab2_test.bookstore.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    @Query("""
        SELECT u.id, u.username, u.first_name, u.last_name, u.password, r.name AS role
        FROM app_user u
        JOIN user_roles ur ON u.id = ur.user_id
        JOIN roles r ON ur.role_id = r.id
        WHERE u.username = $1
    """)
    Mono<User> findByUsername(String username);

    @Query("""
    SELECT u.id, u.username, u.first_name, u.last_name, u.password, r.name AS role
    FROM app_user u
    LEFT JOIN user_roles ur ON u.id = ur.user_id
    LEFT JOIN roles r ON ur.role_id = r.id
    """)
    Flux<User> findAll();

    @Query("""
    INSERT INTO user_roles (user_id, role_id)
    SELECT :userId, r.id
    FROM roles r
    WHERE r.name = :roleName
    """)
    Mono<Void> saveUserRole(@Param("userId") Long userId, @Param("roleName") String roleName);
}

