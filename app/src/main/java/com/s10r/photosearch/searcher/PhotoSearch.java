package com.s10r.photosearch.searcher;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.s10r.photosearch.SearchResult;

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
    private Callback callback;
    private int start;
    private boolean requestInFlight = false;
    private Settings settings;

    public boolean prepare(String query, Settings settings, Callback callback) {
        if (this.requestInFlight) {
            return false;
        }
        this.query = query;
        this.callback = callback;
        this.start = 0;
        this.requestInFlight = false;
        this.settings = settings;

        return true;
    }

    public void search() {
        if (this.requestInFlight) {
            return;
        }
        this.requestInFlight = true;
        AsyncHttpClient client = new AsyncHttpClient();
        String uri = uri();
        if (uri == null) {
            Log.i("DEBUG", "Couldn't build URI.");
            return;
        }
        client.get(uri, null, new JsonHttpResponseHandler() {
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

    private String uri() {
        URIBuilder builder = new URIBuilder();
        builder.setScheme(GIS_SCHEME);
        builder.setHost(GIS_HOST);
        builder.setPath(GIS_PATH);
        builder.addParameter("v", "1.0");
        builder.addParameter("q", query);
        builder.addParameter("rsz", Integer.toString(GIS_RESULT_SIZE));
        builder.addParameter("start", Integer.toString(this.start));
        if (this.settings.isImageColorSet()) {
            builder.addParameter("imgcolor", this.settings.getImageColor());
        }
        if (this.settings.isImageTypeSet()) {
            builder.addParameter("imgtype", this.settings.getImageType());
        }
        if (this.settings.isSiteSet()) {
            builder.addParameter("as_sitesearch", this.settings.getSite());
        }
        if (this.settings.imageSizeIsSet()) {
            builder.addParameter("imgsz", this.settings.getImageSize());
        }

        URI uri;
        try {
            uri = builder.build();
            return uri.toString();
        } catch (URISyntaxException e) {
            return null;
        }
    }
}
