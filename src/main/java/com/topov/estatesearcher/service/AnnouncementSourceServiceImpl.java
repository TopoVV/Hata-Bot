package com.topov.estatesearcher.service;

import com.topov.estatesearcher.client.Client;
import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.parser.OlxParser;
import com.topov.estatesearcher.parser.Parser;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
public class AnnouncementSourceServiceImpl {
    private final Client client;
    private final AnnouncementService announcementService;

    private final Parser olxParser = new OlxParser();

    @Autowired
    public AnnouncementSourceServiceImpl(Client client, AnnouncementService announcementService) {
        this.client = client;
        this.announcementService = announcementService;
    }

   // @Scheduled(fixedDelay = 120000, initialDelay = 1000)
    public void receiveAnnouncements() {
        log.info("Checking announcements at OLX");
        final int pagesQuantity = client.receivePagesAmount(this.olxParser);
        final List<Announcement> announcements = this.client.receiveAllPages(pagesQuantity)
            .stream()
            .flatMap(page -> page.parseAnnouncements(this.olxParser))
            .collect(toList());

        this.announcementService.saveAnnouncementsAndNotifySubscribers(announcements);
    }
}
