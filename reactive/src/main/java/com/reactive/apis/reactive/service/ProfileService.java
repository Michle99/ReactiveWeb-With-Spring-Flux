package com.reactive.apis.reactive.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.reactive.apis.reactive.customApplicationEvent.ProfileCreatedEvent;
import com.reactive.apis.reactive.model.Profile;
import com.reactive.apis.reactive.repo.ProfileRepository;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class ProfileService {
    private final ApplicationEventPublisher publisher;
    private final ProfileRepository profileRepo;

    ProfileService(ApplicationEventPublisher publisher, ProfileRepository profileRepo){
        this.publisher = publisher;
        this.profileRepo = profileRepo;
    }

    public Flux<Profile> all() {
        return this.profileRepo.findAll();
    }

    public Mono<Profile> get(String id){
        return this.profileRepo.findById(id);
    }

    public Mono<Profile> update(String id, String email){
        return this.profileRepo
            .findById(id)
            .map(p -> new Profile(p.getId(), email))
            .flatMap(this.profileRepo::save);
    }

    public Mono<Profile> delete(String id){
        return this.profileRepo
            .findById(id)
            .flatMap(p -> this.profileRepo.deleteById(p.getId()).thenReturn(p));
    }

    public Mono<Profile> create(String email){
        return this.profileRepo
            .save(new Profile(null, email))
            .doOnSuccess(profile -> this.publisher.publishEvent(new ProfileCreatedEvent(profile)));
    }
}
