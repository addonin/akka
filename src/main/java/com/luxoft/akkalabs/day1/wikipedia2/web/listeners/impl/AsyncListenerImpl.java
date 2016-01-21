package com.luxoft.akkalabs.day1.wikipedia2.web.listeners.impl;

import akka.actor.ActorSelection;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Unregister;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;

/**
 * @author dimon
 * @since 21/01/16.
 */
public class AsyncListenerImpl implements AsyncListener {

    private String streamId;
    private ActorSelection actorSelection;

    public AsyncListenerImpl(String streamId, ActorSelection actorSelection) {
        this.streamId = streamId;
        this.actorSelection = actorSelection;
    }



    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        unregister(actorSelection);
    }

    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        unregister(actorSelection);
        asyncEvent.getAsyncContext().complete();
    }

    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {
        unregister(actorSelection);
    }

    @Override
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

    }

    private void unregister(ActorSelection selection) {
        selection.tell(new Unregister(streamId), null);
    }

}
