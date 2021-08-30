package com.techm.crypton.jericho.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.techm.crypton.jericho.models.Users;

public interface UserRepository extends MongoRepository<Users,UUID> {
	Optional<Users> findByUsername(String username);
	Boolean existsByUsername(String username);
}
