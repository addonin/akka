package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class AppleVsGoogle {

    public static void main(String[] args) throws Exception {

        ActorSystem actorSystem = ActorSystem.create("AppleVsGoogle");

        CollectTweets apple = new CollectTweets(10, actorSystem, "Apple");
        CollectTweets google = new CollectTweets(10, actorSystem, "Google");

        Future<Result> futureAppleResult = Futures.future(apple, actorSystem.dispatcher());
        Future<Result> futureGoogleResult = Futures.future(google, actorSystem.dispatcher());

        Result appleResult = Await.result(futureAppleResult, Duration.create(60, TimeUnit.SECONDS));
        Result googleResult = Await.result(futureGoogleResult, Duration.create(60, TimeUnit.SECONDS));

        System.out.println("Apple: ");
        appleResult.getTweets().stream().forEach(tweet -> System.out.print(tweet.getLanguage() + " "));
        System.out.println("\nGoogle: ");
        googleResult.getTweets().stream().forEach(tweet -> System.out.print(tweet.getLanguage() + " "));
    }
}
