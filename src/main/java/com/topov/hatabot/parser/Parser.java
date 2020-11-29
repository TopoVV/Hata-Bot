package com.topov.hatabot.parser;

import com.topov.hatabot.model.Announcement;
import org.jsoup.nodes.Document;

import java.util.List;

public interface Parser {
    List<Announcement> parseAnnouncements(Document document);
}
