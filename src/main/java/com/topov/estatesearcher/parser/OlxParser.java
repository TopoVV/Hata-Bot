package com.topov.estatesearcher.parser;

import com.topov.estatesearcher.model.Announcement;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OlxParser implements Parser {

    @Override
    public int parsePagesQuantity(Document document) {
        String pagesAmount = document.select(".pager")
            .select(".item")
            .last()
            .select("a")
            .text();

        return Integer.parseInt(pagesAmount);
    }

    @Override
    public List<Announcement> parseAnnouncements(Document document) {
        Elements offers = document.select("#offers_table").select(".offer");

        List<Announcement> announcements = new ArrayList<>();
        offers.forEach(element -> {
            Element table = element.select("table").first();
            if (table != null) {
                Element titleCell = table.select(".title-cell").first();
                Element link = titleCell.select(".link").first();

                String url = link.attr("href");
                String description = link.text();
                String priceString = table.select(".price").select("strong").text().replaceAll("\\D", "");

                LocalDateTime extractionDateTime = LocalDateTime.now();
                Integer price = Integer.parseInt(priceString);

                Elements bottomCell = table.select(".bottom-cell");
                Element cityBlock = bottomCell.select(".breadcrumb").get(0);
                String cityName = cityBlock.text().split(",")[0];

                announcements.add(new Announcement(url, price, extractionDateTime, description, cityName));
            }
        });
        return announcements;
    }
}
