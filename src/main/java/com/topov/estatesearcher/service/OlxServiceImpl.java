package com.topov.estatesearcher.service;

import com.topov.estatesearcher.client.Client;
import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.parser.OlxParser;
import com.topov.estatesearcher.parser.Parser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Log4j2
@Service("olxService")
public class OlxServiceImpl implements SourceService {
    private final Client client;
    private final AnnouncementService announcementService;
    private final NotificationService notificationService;

    private final Parser olxParser = new OlxParser();

    @Autowired
    public OlxServiceImpl(Client client, AnnouncementService announcementService, NotificationService notificationService) {
        this.client = client;
        this.announcementService = announcementService;
        this.notificationService = notificationService;
    }

    @Async
    @Override
    public void checkAnnouncements() {
        log.info("Checking announcements at OLX");
        final List<Announcement> announcements = this.client.receiveAllPages()
            .stream()
            .flatMap(page -> page.parseAnnouncements(this.olxParser))
            .collect(toCollection(ArrayList::new));

        final List<Announcement> newAnnouncements = this.announcementService.filterNewAnnouncements(announcements);
        this.announcementService.saveAnnouncements(newAnnouncements);
        this.notificationService.notifySubscribers(newAnnouncements);
    }
}
