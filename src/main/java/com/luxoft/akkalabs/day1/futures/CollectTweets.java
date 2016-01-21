package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import com.luxoft.akkalabs.clients.twitter.QueueTwitterClient;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.clients.twitter.TwitterClients;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * @author dimon
 * @since 19/01/16.
 */
public class CollectTweets implements Callable<Result> {

    private ActorSystem actorSystem;
    private String keyword;

    public CollectTweets(ActorSystem actorSystem, String keyword) {
        this.actorSystem = actorSystem;
        this.keyword = keyword;
    }

    @Override
    public Result call() throws Exception {
        QueueTwitterClient queueTwitterClient = TwitterClients.start(actorSystem, keyword);
        ArrayList<TweetObject> results = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            results.add(queueTwitterClient.next());
        }
        queueTwitterClient.stop();
        return new Result(keyword, results);
    }
}
