package com.s10r.photosearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.s10r.photosearch.searcher.Callback;
import com.s10r.photosearch.searcher.PhotoSearch;
import com.s10r.photosearch.searcher.Settings;

import java.util.ArrayList;

public class PhotoGrid extends AppCompatActivity {

    private ArrayList<SearchResult> results;
    private SearchResultAdapter adapter;
    private PhotoSearch searcher;
    private Settings settings;
    private static int SETTINGS_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_grid);
        results = new ArrayList<>();
        adapter = new SearchResultAdapter(this, results);
        GridView gvResults = (GridView)findViewById(R.id.gridView);
        gvResults.setAdapter(adapter);
        this.searcher = new PhotoSearch();
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult searchResult = results.get(position);
                Intent intent = new Intent(PhotoGrid.this, PhotoView.class);
                intent.putExtra("url", searchResult.getImageUrl());
                startActivity(intent);
            }
        });
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (searcher != null) {
                    return searcher.next();
                }
                return false;
            }
        });
        this.settings = new Settings();
        this.settings.setImageType(Settings.Type.CLIPART);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_grid, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                adapter.clear();
                if (searcher.prepare(query, settings, new Callback(){
                    @Override
                    public void result(SearchResult searchResult) {
                        adapter.add(searchResult);
                    }
                })) {
                    searcher.search();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(PhotoGrid.this, SearchSettings.class);
            intent.putExtra("settings", settings);
            startActivityForResult(intent, SETTINGS_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTINGS_CODE && resultCode == RESULT_OK) {
            this.settings = (Settings)data.getParcelableExtra("settings");
        }
    }
}
