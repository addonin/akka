package com.luxoft.akkalabs.day1.firstactor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class FirstActorApp {

    public static void main(String[] args) throws Exception {

        ActorSystem actorSystem = ActorSystem.create();

        ActorRef actor = actorSystem.actorOf(Props.create(MyFirstActor.class));
        actor.tell(new Object(), null);
        actor.tell("blabla", null);
        actor.tell("ping", null);

        Thread.sleep(1000);
        actorSystem.shutdown();
    }

}
