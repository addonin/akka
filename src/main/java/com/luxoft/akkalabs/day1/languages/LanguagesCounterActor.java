package com.luxoft.akkalabs.day1.languages;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day1.futures.FinalResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dmytro_adonin on 1/20/2016.
 */
public class LanguagesCounterActor extends UntypedActor {

    private final Map<String, Integer> languages = new HashMap<>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof FinalResult) {
            FinalResult result = (FinalResult) message;
            for (Map.Entry<String, Integer> e : result.getLanguages().entrySet()) {
                String key = e.getKey();
                Integer counter = languages.get(key);
                counter = (counter == null ? 0 : counter) + e.getValue();
                languages.put(key, counter);
            }
        } else if ("get".equals(message)) {
            getSender().tell(new HashMap(languages), getSelf());
        }
    }
}
