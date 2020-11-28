package com.topov.estatesearcher.client;

import com.topov.estatesearcher.page.Page;

import java.util.List;

public interface Client {
    List<Page> receiveAllPages();
}
