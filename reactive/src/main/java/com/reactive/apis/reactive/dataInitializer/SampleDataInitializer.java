package com.reactive.apis.reactive.dataInitializer;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import java.util.UUID;

import com.reactive.apis.reactive.model.Profile;
import com.reactive.apis.reactive.repo.ProfileRepository;

@Log4j2 
@Component
@org.springframework.context.annotation.Profile("reactive")
public class SampleDataInitializer implements ApplicationListener<ApplicationReadyEvent>{

    private final ProfileRepository profileRepo;

    public SampleDataInitializer(ProfileRepository profileRepo) {
        this.profileRepo = profileRepo;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
      profileRepo
            .deleteAll()
            .thenMany(
                Flux
                    .just("A", "B", "C", "D")
                    .map(name -> new Profile(UUID.randomUUID().toString(), name + "@email.com"))
                    .flatMap(profileRepo::save)
            )
            .thenMany(profileRepo.findAll())
            .subscribe(profile -> log.info("saving " + profile.toString()));
        
    }
    
}
