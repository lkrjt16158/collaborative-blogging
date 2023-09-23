package com.lk.collaborative.blogging.data.repository;

import com.lk.collaborative.blogging.data.domain.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}
