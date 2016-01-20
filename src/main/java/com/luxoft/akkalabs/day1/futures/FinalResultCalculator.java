package com.luxoft.akkalabs.day1.futures;

import akka.dispatch.Mapper;
import com.luxoft.akkalabs.clients.twitter.TweetObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dimon
 * @since 20/01/16.
 */
public class FinalResultCalculator extends Mapper<Result, FinalResult> {

    @Override
    public FinalResult apply(Result parameter) {
        Map<String, Integer> languges = new HashMap<>();
        for (TweetObject tweet : parameter.getTweets()) {
            String language = tweet.getLanguage();
            Integer counter = languges.get(language);
            counter = (counter == null) ? 1 : counter + 1;
            languges.put(language, counter);
        }
        return new FinalResult(parameter.getKeyword(), languges);
    }
}
