package com.zaprogramujzycie.finanse.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    void delete(Optional<User> existingUser);

    Optional<User> save(Optional<User> existingUser);
}
