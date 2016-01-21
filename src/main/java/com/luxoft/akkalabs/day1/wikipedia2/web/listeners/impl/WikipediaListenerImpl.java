package com.luxoft.akkalabs.day1.wikipedia2.web.listeners.impl;

import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;
import com.luxoft.akkalabs.day1.wikipedia2.web.listeners.WikipediaListener;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.NotDeliveredException;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.Writer;

/**
 * @author dimon
 * @since 21/01/16.
 */
public class WikipediaListenerImpl implements WikipediaListener {

    private String streamId;
    private AsyncContext asyncContext;
    private Writer out;

    public WikipediaListenerImpl(String streamId, AsyncContext asyncContext) {
        this.streamId = streamId;
        this.asyncContext = asyncContext;
    }

    @Override
    public void deliver(WikipediaPage page) throws NotDeliveredException {
        try {
            if (out == null) {
                out = asyncContext.getResponse().getWriter();
            }
            String data = page.toJSONString();
            String eventId = Long.toString(System.currentTimeMillis());
            String[] lines = data.split("\n");
            out.append("id: ").append(eventId).append("\n");
            for (String line : lines) {
                out.append("data: ").append(line).append("\n");
            }
            out.append("\n\n");
            asyncContext.getResponse().flushBuffer();
        } catch (IOException e) {
            throw new NotDeliveredException();
        }
    }

    @Override
    public String getStreamId() {
        return streamId;
    }

    @Override
    public void close() {
        asyncContext.complete();
    }
}
