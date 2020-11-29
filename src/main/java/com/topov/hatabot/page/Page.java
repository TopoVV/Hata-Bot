package com.topov.hatabot.page;

import com.topov.hatabot.model.Announcement;
import com.topov.hatabot.parser.Parser;

import java.util.stream.Stream;

public interface Page {
    Stream<Announcement> parseAnnouncements(Parser parser);
}
