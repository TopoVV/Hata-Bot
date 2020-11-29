package com.topov.hatabot.page;

import com.topov.hatabot.model.Announcement;
import com.topov.hatabot.parser.Parser;
import org.jsoup.nodes.Document;

import java.util.stream.Stream;

public class OlxPage implements Page {
    private final Document document;

    public OlxPage(Document document) {
        this.document = document;
    }

    @Override
    public Stream<Announcement> parseAnnouncements(Parser parser) {
        return parser.parseAnnouncements(this.document).stream();
    }
}
