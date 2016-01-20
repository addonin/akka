package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import scala.concurrent.Future;

import java.util.Arrays;
import java.util.List;

public class AppleVsGoogle {

    public static void main(String[] args) throws Exception {

        ActorSystem actorSystem = ActorSystem.create("AppleVsGoogle");

        CollectTweets apple = new CollectTweets(10, actorSystem, "Apple");
        CollectTweets google = new CollectTweets(10, actorSystem, "Google");

        Future<FinalResult> futureAppleResult =
                Futures.future(apple, actorSystem.dispatcher())
                        .map(new FinalResultCalculator(), actorSystem.dispatcher());

        Future<FinalResult> futureGoogleResult =
                Futures.future(google, actorSystem.dispatcher()).
                        map(new FinalResultCalculator(), actorSystem.dispatcher());

        List<Future<FinalResult>> allResults = Arrays.asList(futureAppleResult, futureGoogleResult);
        Future<Iterable<FinalResult>> result = Futures.sequence(allResults, actorSystem.dispatcher());

        //System.out.println("Apple: ");
        //appleResult.getTweets().stream().forEach(tweet -> System.out.print(tweet.getLanguage() + " "));
        //System.out.println("\nGoogle: ");
        //googleResult.getTweets().stream().forEach(tweet -> System.out.print(tweet.getLanguage() + " "));
    }
}
