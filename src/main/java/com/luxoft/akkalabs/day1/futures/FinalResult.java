package com.luxoft.akkalabs.day1.futures;

import java.util.Map;

/**
 * @author dimon
 * @since 20/01/16.
 */
public class FinalResult {

    private String keyword;
    private Map<String, Integer> languages;

    public FinalResult(String keyword, Map<String, Integer> languages) {
        this.keyword = keyword;
        this.languages = languages;
    }

    public String getKeyword() {
        return keyword;
    }

    public Map<String, Integer> getLanguages() {
        return languages;
    }
}
