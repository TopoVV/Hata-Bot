package com.topov.estatesearcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementChecker {
    private final SourceService olxService;

    @Autowired
    public AnnouncementChecker(@Qualifier("olxService") SourceService olxService) {
        this.olxService = olxService;
    }

    @Scheduled(fixedDelay = 1000*60, initialDelay = 1000)
    public void checkOlx() {
        this.olxService.checkAnnouncements();
    }
}
