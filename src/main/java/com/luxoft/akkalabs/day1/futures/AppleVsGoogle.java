package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.dispatch.OnSuccess;
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

        result.onSuccess(new OnSuccess<Iterable<FinalResult>>() {
            @Override
            public void onSuccess(Iterable<FinalResult> success) throws Throwable {
                for (FinalResult finalResult : success) {
                    System.out.println("Result for " + finalResult.getKeyword() + " is " + finalResult);
                }
            }
        }, actorSystem.dispatcher());

    }
}
