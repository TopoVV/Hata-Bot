package com.topov.estatesearcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementResolver {
    private final OlxServiceImpl olxService;

    @Autowired
    public AnnouncementResolver(OlxServiceImpl olxService) {
        this.olxService = olxService;
    }

    @Scheduled(fixedDelay = 60000, initialDelay = 1000)
    public void receiveAnnouncements() {
        this.olxService.receiveAnnouncements();
    }
}
