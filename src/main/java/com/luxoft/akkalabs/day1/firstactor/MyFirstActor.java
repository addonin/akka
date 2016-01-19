package com.luxoft.akkalabs.day1.firstactor;

import akka.actor.UntypedActor;

/**
 * @author dimon
 * @since 19/01/16.
 */
public class MyFirstActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof String && "ping".equals(message)) {
            System.out.println("pong!");
        } else {
            System.out.println("I don't know what to do!");
        }

    }
}
