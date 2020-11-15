package com.topov.estatesearcher.page;

import com.topov.estatesearcher.model.Announcement;
import com.topov.estatesearcher.parser.Parser;
import org.jsoup.nodes.Document;

import java.util.stream.Stream;

public class OlxPage implements Page {
    private final Document document;

    public OlxPage(Document document) {
        this.document = document;
    }

    @Override
    public int parsePagesQuantity(Parser parser) {
        return parser.parsePagesQuantity(this.document);
    }

    @Override
    public Stream<Announcement> parseAnnouncements(Parser parser) {
        return parser.parseAnnouncements(this.document).stream();
    }
}
