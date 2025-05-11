package com.example.emtlab.listeners;

import com.example.emtlab.events.AuthorCreatedEvent;
import com.example.emtlab.service.domain.AuthorService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventHandles {
    private final AuthorService authorService;

    public AuthorEventHandles(AuthorService authorService) {
        this.authorService = authorService;
    }

    @EventListener
    public void onAuthorCreated(AuthorCreatedEvent event) {
        this.authorService.refreshMaterializedView();
    }

}
