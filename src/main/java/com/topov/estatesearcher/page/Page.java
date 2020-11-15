package com.topov.estatesearcher.page;

import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.parser.Parser;

import java.util.stream.Stream;

public interface Page {
    int parsePagesQuantity(Parser parser);
    Stream<Announcement> parseAnnouncements(Parser parser);
}
