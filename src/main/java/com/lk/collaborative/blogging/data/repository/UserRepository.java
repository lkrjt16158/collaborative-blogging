package com.lk.collaborative.blogging.data.repository;

import com.lk.collaborative.blogging.data.domain.User;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserName(@NonNull String userName);
    Optional<User> findByEmail(@NonNull String email);
}
