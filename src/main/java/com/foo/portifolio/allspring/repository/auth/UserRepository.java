package com.foo.portifolio.allspring.repository.auth;

import org.springframework.stereotype.Repository;

import com.foo.portifolio.allspring.model.entity.user.User;
import com.foo.portifolio.allspring.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

}
