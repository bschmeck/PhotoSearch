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
                if (searcher.prepare(query, new Settings(), new Callback(){
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



        return super.onOptionsItemSelected(item);
    }
}
