package com.reactive.apis.reactive.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.reactive.apis.reactive.model.Profile;


public interface ProfileRepository extends ReactiveMongoRepository<Profile, String> {
}