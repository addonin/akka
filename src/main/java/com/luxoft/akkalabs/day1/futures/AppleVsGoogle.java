package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;

public class AppleVsGoogle {

    public static void main(String[] args) throws Exception {

        ActorSystem actorSystem = ActorSystem.create("AppleVsGoogle");
        CollectTweets apple = new CollectTweets(10, actorSystem, "Apple");
        Result result = apple.call();
        result.getTweets().stream().forEach(tweet -> System.out.println(tweet.getLanguage()));
    }
}
