package com.reactive.apis.reactive.customApplicationEvent;
import org.springframework.context.ApplicationEvent;
import com.reactive.apis.reactive.model.Profile;


public class ProfileCreatedEvent extends ApplicationEvent {
    public ProfileCreatedEvent(Profile source) {
        super(source);
    }
}
