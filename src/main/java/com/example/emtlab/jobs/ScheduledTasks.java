package com.example.emtlab.jobs;

import com.example.emtlab.service.domain.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private final BookService bookService;

    public ScheduledTasks(BookService bookService) {
        this.bookService = bookService;
    }

    @Scheduled(cron = "0 * * * * *")
    public void refreshMaterializedView() {
        this.bookService.refreshMaterializedView();
    }

}
