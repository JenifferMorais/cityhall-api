package com.foo.portifolio.allspring.repository.auth;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.foo.portifolio.allspring.model.entity.user.User;
import com.foo.portifolio.allspring.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

	public Optional<User> findByUsername(String username);
	public boolean existsByUsername(String username);
	

}
