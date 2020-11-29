package com.topov.estatesearcher.parser;

import com.topov.estatesearcher.model.Announcement;
import org.jsoup.nodes.Document;

import java.util.List;

public interface Parser {
    List<Announcement> parseAnnouncements(Document document);
}
