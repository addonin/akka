package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Deliver;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Register;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Unregister;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.WikipediaListener;

import java.util.HashMap;
import java.util.Map;

public class ConnectionsActor extends UntypedActor {

    private final Map<String, WikipediaListener> connections = new HashMap<>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Deliver) {
            Deliver deliver = (Deliver) message;
            for (WikipediaListener listener : connections.values()) {
                listener.deliver(deliver.getPage());
            }
        } else if (message instanceof Register) {
            Register register = (Register) message;
            connections.put(register.getListener().getStreamId(), register.getListener());
        }
        if (message instanceof Unregister) {
            Unregister unregister = (Unregister) message;
            WikipediaListener listener = connections.get(unregister.getId());
            if (listener != null) {
                connections.remove(listener.getStreamId());
            }
        }
    }
}
