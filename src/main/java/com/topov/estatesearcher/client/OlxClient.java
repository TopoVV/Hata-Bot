package com.topov.estatesearcher.client;

import com.topov.estatesearcher.page.OlxPage;
import com.topov.estatesearcher.page.Page;
import com.topov.estatesearcher.parser.Parser;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Log4j2
@Service
public class OlxClient implements Client {

    @Override
    public int receivePagesAmount(Parser parser) {
        try {
            final Document document = Jsoup.connect("https://www.olx.ua/nedvizhimost/q-аренда/?page=1").get();
            final OlxPage olxPage = new OlxPage(document);
            return olxPage.parsePagesQuantity(parser);
        } catch (IOException e) {
            log.error("Cannot receive page from OLX", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Page> receiveAllPages(int pagesAmount) {
        final String template = "https://www.olx.ua/nedvizhimost/q-аренда/?page=%d";
        
        List<Document> pages = new ArrayList<>();
        for (int i = 1; i < pagesAmount; i++) {
            try {
                Document document = Jsoup.connect(String.format(template, i)).get();
                pages.add(document);
            } catch (IOException e) {
                log.error("Error receiving {} page. It was skipped", i, e);
            }
        }
        
        return pages.stream()
            .map(OlxPage::new)
            .collect(toList());
    }
}
