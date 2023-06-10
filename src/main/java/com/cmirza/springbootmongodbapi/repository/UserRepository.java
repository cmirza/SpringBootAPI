package com.cmirza.springbootmongodbapi.repository;

import com.cmirza.springbootmongodbapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {}
