package com.luxoft.akkalabs.day1.wikipedia2.web.listeners;

import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.NotDeliveredException;

public interface WikipediaListener {

    void deliver(WikipediaPage page) throws NotDeliveredException;
    public String getStreamId();
    public void close();
}
