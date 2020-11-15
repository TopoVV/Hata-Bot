package com.topov.estatesearcher.client;

import com.topov.estatesearcher.page.Page;
import com.topov.estatesearcher.parser.Parser;

import java.util.List;

public interface Client {
    int receivePagesAmount(Parser parser);
    List<Page> receiveAllPages(int pagesAmount);
}
