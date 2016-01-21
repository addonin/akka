package com.luxoft.akkalabs.day2.sessions.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;
import com.luxoft.akkalabs.day2.sessions.messages.OutgoingBroadcast;
import com.luxoft.akkalabs.day2.sessions.messages.OutgoingToSession;
import com.luxoft.akkalabs.day2.sessions.messages.RegisterSession;
import com.luxoft.akkalabs.day2.sessions.messages.UnregisterSession;

public class SessionsHubActor extends UntypedActor {

    private final Class<? extends SessionProcessor> processorClass;

    public SessionsHubActor(Class<? extends SessionProcessor> processor) {
        this.processorClass = processor;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof RegisterSession) {
            RegisterSession register = (RegisterSession) message;
            String sessionId = register.getSessionId();
            context().actorOf(
                    Props.create(SessionActor.class, sessionId, register.getSession(), processorClass.newInstance()),
                            sessionId);
        } else if (message instanceof UnregisterSession) {
            UnregisterSession unregister = (UnregisterSession) message;
            ActorRef child = getContext().getChild(unregister.getSessionId());
            if (child != null) {
                context().stop(child);
            }
        } else if (message instanceof OutgoingToSession) {
            OutgoingToSession outgoing = (OutgoingToSession) message;
            ActorRef child = getContext().getChild(outgoing.getSessionId());
            if (child != null) {
                child.forward(outgoing, context());    
            }            
        } else if (message instanceof OutgoingBroadcast) {
            OutgoingBroadcast outgoingBroadcast = (OutgoingBroadcast) message;
            for (ActorRef child : getContext().getChildren()) {
                if (child != null) {
                    child.forward(outgoingBroadcast, context());
                }
            }
        }
    }

}
