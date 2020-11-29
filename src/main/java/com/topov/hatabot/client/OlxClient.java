package com.topov.hatabot.client;

import com.topov.hatabot.page.OlxPage;
import com.topov.hatabot.page.Page;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
public class OlxClient implements Client {
    private static final String OLX_PAGE_URL =
        "https://www.olx.ua/nedvizhimost/q-аренда/?search\\%5Border\\%5D=created_at\\%3Adesc&page=";
    private static final int PAGES_SCANNED_PER_CHECK = 5;

    @Override
    public List<Page> receiveAllPages() {
        List<Document> pages = new ArrayList<>();

        for (int i = 1; i <= PAGES_SCANNED_PER_CHECK; i++) {
            getHTMLDocument(i).ifPresent(pages::add);
        }

        return pages.stream()
            .map(OlxPage::new)
            .collect(toList());
    }

    private Optional<Document> getHTMLDocument(int i) {
        try {
            final Document document = Jsoup.connect(OLX_PAGE_URL + i).get();
            return Optional.of(document);
        } catch (IOException e) {
            log.error("Error receiving {} page. It was skipped", i, e);
            return Optional.empty();
        }
    }
}
