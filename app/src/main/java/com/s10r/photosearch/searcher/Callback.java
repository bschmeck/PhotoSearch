package com.s10r.photosearch.searcher;

import com.s10r.photosearch.SearchResult;

/**
 * Created by bschmeckpeper on 11/2/15.
 */

public abstract class Callback {
    public abstract void result(SearchResult searchResult);
}