package com.luxoft.akkalabs.day2.sessions.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;
import com.luxoft.akkalabs.day2.sessions.messages.Incoming;
import com.luxoft.akkalabs.day2.sessions.messages.Outgoing;

import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class SessionActor extends UntypedActor {

    private final String sessionId;
    private final Session session;
    private final SessionProcessor processor;
    private final MessageHandler handler;

    public SessionActor(String sessionId, Session session, SessionProcessor processor) {
        this.sessionId = sessionId;
        this.session = session;
        this.processor = processor;
        this.handler = new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String message) {
                self().tell(new Incoming(message), self());
            }
        };
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        System.out.println("Session is started");
        session.addMessageHandler(handler);
        processor.started(sessionId, context(), session);
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        System.out.println("Session is stopped");
        processor.stopped();
    }

    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof Incoming) {
            Incoming incoming = (Incoming) message;
            processor.incoming(incoming.getMessage());
        } else if (message instanceof Outgoing) {
            Outgoing outgoing = (Outgoing) message;
            processor.outgoing(outgoing.getMessage());
        } else {
            processor.outgoing(message);
        }
    }
}
