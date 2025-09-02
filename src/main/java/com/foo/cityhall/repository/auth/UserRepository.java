package com.foo.cityhall.repository.auth;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.foo.cityhall.model.entity.user.User;
import com.foo.cityhall.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

	public Optional<User> findByUsername(String username);
	public boolean existsByUsername(String username);


}
