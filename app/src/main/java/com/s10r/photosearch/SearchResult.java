package com.s10r.photosearch;

/**
 * Created by bschmeckpeper on 11/1/15.
 */
public class SearchResult {
    private String query;
    private String src;
    private String title;

    public SearchResult(String query, String src, String title) {
        this.query = query;
        this.src = src;
        this.title = title;
    }

    public String getImageUrl() {
        return src;
    }

    public String getQuery() {
        return query;
    }

    public String getTitle() {
        return title;
    }
}
