package com.topov.hatabot.client;

import com.topov.hatabot.page.Page;

import java.util.List;

public interface Client {
    List<Page> receiveAllPages();
}
