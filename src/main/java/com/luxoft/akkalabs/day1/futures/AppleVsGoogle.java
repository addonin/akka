package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;

public class AppleVsGoogle {

    public static void main(String[] args) throws Exception {

        ActorSystem actorSystem = ActorSystem.create("AppleVsGoogle");

        CollectTweets apple = new CollectTweets(10, actorSystem, "Apple");
        CollectTweets google = new CollectTweets(10, actorSystem, "Google");

        Result appleResult = apple.call();
        Result googleResult = google.call();

        System.out.println("Apple: ");
        appleResult.getTweets().stream().forEach(tweet -> System.out.print(tweet.getLanguage() + " "));
        System.out.println("\nGoogle: ");
        googleResult.getTweets().stream().forEach(tweet -> System.out.print(tweet.getLanguage() + " "));
    }
}
