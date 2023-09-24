package com.lk.collaborative.blogging.data.repository;

import com.lk.collaborative.blogging.data.domain.Profile;
import com.lk.collaborative.blogging.data.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
}
