package com.s10r.photosearch;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.utils.URIBuilder;

/**
 * Created by bschmeckpeper on 11/1/15.
 */
public class PhotoSearch {
    private static String GIS_SCHEME = "https";
    private static String GIS_HOST = "ajax.googleapis.com";
    private static String GIS_PATH = "/ajax/services/search/images";
    private static int GIS_RESULT_SIZE = 8;

    private String query;
    private SearchCallback callback;
    private int start;
    private boolean requestInFlight = false;

    public boolean prepare(String query, SearchCallback callback) {
        if (this.requestInFlight) {
            return false;
        }
        this.query = query;
        this.callback = callback;
        this.start = 0;
        this.requestInFlight = false;

        return true;
    }

    public void search() {
        if (this.requestInFlight) {
            return;
        }
        this.requestInFlight = true;
        URIBuilder builder = new URIBuilder();
        builder.setScheme(GIS_SCHEME);
        builder.setHost(GIS_HOST);
        builder.setPath(GIS_PATH);
        builder.addParameter("v", "1.0");
        builder.addParameter("q", query);
        builder.addParameter("rsz", Integer.toString(GIS_RESULT_SIZE));
        builder.addParameter("start", Integer.toString(this.start));
        AsyncHttpClient client = new AsyncHttpClient();
        URI uri;
        try {
            uri = builder.build();
            Log.i("DEBUG", "URI: " + uri.toString());
        } catch (URISyntaxException e) {
            return;
        }
        client.get(uri.toString(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject responseData = response.getJSONObject("responseData");
                    JSONArray jsonResults = responseData.getJSONArray("results");
                    Log.i("DEBUG", "Got " + jsonResults.length() + " results");
                    for (int i = 0; i < jsonResults.length(); i++) {
                        JSONObject result = jsonResults.getJSONObject(i);
                        SearchResult searchResult = new SearchResult(query,
                                                                    result.getString("url"),
                                                                    result.getString("title"));
                        callback.result(searchResult);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    PhotoSearch.this.requestInFlight = false;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public static abstract class SearchCallback {
        public abstract void result(SearchResult searchResult);
    }
}
