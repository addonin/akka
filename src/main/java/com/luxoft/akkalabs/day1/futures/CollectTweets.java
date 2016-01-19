package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import com.luxoft.akkalabs.clients.twitter.QueueTwitterClient;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.clients.twitter.TwitterClients;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author dimon
 * @since 19/01/16.
 */
public class CollectTweets implements Callable<Result> {

    private int tweetsAmount;
    private ActorSystem actorSystem;
    private String keyword;

    public CollectTweets(int tweetsAmount, ActorSystem actorSystem, String keyword) {
        this.tweetsAmount = tweetsAmount;
        this.actorSystem = actorSystem;
        this.keyword = keyword;
    }

    @Override
    public Result call() throws Exception {
        QueueTwitterClient queueTwitterClient = TwitterClients.start(actorSystem, keyword);
        List<TweetObject> results = new LinkedList<>();
        for (int i = 0; i < tweetsAmount; i++) {
            results.add(queueTwitterClient.next());
        }
        queueTwitterClient.stop();
        return new Result(keyword, results);
    }
}
