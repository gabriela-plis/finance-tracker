package com.financetracker.app.user;

import com.financetracker.app.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);
}
