package com.lk.collaborative.blogging.data.repository;

import com.lk.collaborative.blogging.data.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
}
