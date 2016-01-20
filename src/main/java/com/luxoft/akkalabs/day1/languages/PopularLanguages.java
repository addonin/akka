package com.luxoft.akkalabs.day1.languages;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.dispatch.OnSuccess;
import com.luxoft.akkalabs.day1.futures.CollectTweets;
import com.luxoft.akkalabs.day1.futures.FinalResult;
import com.luxoft.akkalabs.day1.futures.FinalResultCalculator;

import java.util.Arrays;
import java.util.List;

public class PopularLanguages {

    public static void main(String[] args) throws Exception {

        ActorSystem actorSystem = ActorSystem.create("PopularLanguages");
        ActorRef actorRef = actorSystem.actorOf(Props.create(LanguagesCounterActor.class));
        List<String> keywords = Arrays.asList("Google", "Apple", "Android", "iPhone", "Lady Gaga");

        for (String keyword : keywords) {
            Futures.future(new CollectTweets(10, actorSystem, keyword), actorSystem.dispatcher())
                    .map(new FinalResultCalculator(), actorSystem.dispatcher())
                            .onSuccess(new SendToActor(actorRef), actorSystem.dispatcher());
        }
    }

    private static class SendToActor extends OnSuccess<FinalResult> {

        private final ActorRef actor;

        public SendToActor(ActorRef actor) {
            this.actor = actor;
        }

        @Override
        public void onSuccess(FinalResult result) throws Throwable {
            actor.tell(result, null);
        }
    }
}
