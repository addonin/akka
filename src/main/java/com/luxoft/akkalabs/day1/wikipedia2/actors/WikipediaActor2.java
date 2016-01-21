package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaClient;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Deliver;

import java.net.URL;
import java.util.concurrent.Callable;

public class WikipediaActor2 extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            URL url = new URL(message.toString());
            if (url.getHost().toLowerCase().endsWith(".wikipedia.org") && url.getPath().length() > 6) {
                final String lang = url.getHost().substring(0, 2);
                final String term = url.getPath().substring(6);
                final ActorSystem actorSystem = context().system();

                Futures.future((Callable<WikipediaPage>) () -> {
                    WikipediaPage page = WikipediaClient.getPage(lang, term);
                    if (page != null) {
                        System.out.println(page.getTitle());
                        actorSystem.actorSelection("/user/connections").tell(new Deliver(page), null);
                    }
                    return null;
                }, actorSystem.dispatcher());
            }
        }
    }
}
