package com.luxoft.akkalabs.day2.sessions.session;

import akka.actor.ActorContext;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by dmytro_adonin on 1/21/2016.
 */
public class EchoSessionProcessor implements SessionProcessor {

    private String sessionId;
    private ActorContext context;
    private Session session;


    @Override
    public void started(String sessionId, ActorContext context, Session session) throws IOException {
        this.sessionId = sessionId;
        this.context = context;
        this.session = session;
    }

    @Override
    public void stopped() throws IOException {
    }

    @Override
    public void incoming(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    @Override
    public void outgoing(Object message) throws IOException {
        session.getBasicRemote().sendText(message.toString());
    }
}
