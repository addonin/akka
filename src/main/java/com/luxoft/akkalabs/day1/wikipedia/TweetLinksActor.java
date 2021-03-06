package com.luxoft.akkalabs.day1.wikipedia;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.twitter.TweetObject;

public class TweetLinksActor extends UntypedActor {

    private final ActorRef linksActor;

    public TweetLinksActor(ActorRef linksActor) {
        this.linksActor = linksActor;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof TweetObject) {
            TweetObject tweet = (TweetObject) message;
            for (String url : tweet.getUrls()) {
                linksActor.tell(url, null);
            }
        }
    }
}
