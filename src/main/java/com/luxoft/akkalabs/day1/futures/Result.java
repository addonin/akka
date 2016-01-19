package com.luxoft.akkalabs.day1.futures;

import com.luxoft.akkalabs.clients.twitter.TweetObject;

import java.util.Collection;

/**
 * @author dimon
 * @since 19/01/16.
 */
public class Result {

    private String keyword;
    private Collection<TweetObject> tweets;

    public Result(String keyword, Collection<TweetObject> tweets) {
        this.keyword = keyword;
        this.tweets = tweets;
    }

    public String getKeyword() {
        return keyword;
    }

    public Collection<TweetObject> getTweets() {
        return tweets;
    }
}
