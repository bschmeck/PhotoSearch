package com.s10r.photosearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.s10r.photosearch.searcher.Settings;

import java.util.Arrays;

public class SearchSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_settings);
        Intent intent = getIntent();
        Settings settings = (Settings)intent.getParcelableExtra("settings");
        populate(settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void applySettings(View view) {
        Intent data = new Intent();
        data.putExtra("settings", settings());
        setResult(RESULT_OK, data);
        this.finish();
    }

    private Settings settings() {
        Settings ret = new Settings();

        Spinner sImgType = (Spinner)findViewById(R.id.spImgType);
        ret.setImageType(Settings.Type.values()[sImgType.getSelectedItemPosition()]);

        Spinner sImgColor = (Spinner)findViewById(R.id.sImgColor);
        ret.setImageColor(Settings.Color.values()[sImgColor.getSelectedItemPosition()]);

        Spinner sImgSize = (Spinner)findViewById(R.id.sImgSize);
        ret.setImageSize(Settings.Size.values()[sImgSize.getSelectedItemPosition()]);

        EditText etSite = (EditText)findViewById(R.id.etSite);
        if (etSite.getText().toString() != getString(R.string.site_search)) {
            ret.setSite(etSite.getText().toString());
        }

        return ret;
    }

    private void populate(Settings settings) {
        // Finding the correct Enum this way only works because the Enum is defined in the same
        // order that the options are displayed in the spinner.
        
        Spinner sImgType = (Spinner)findViewById(R.id.spImgType);
        sImgType.setSelection(Arrays.asList(Settings.Type.values()).indexOf(settings.getImageType()));

        Spinner sImgColor = (Spinner)findViewById(R.id.sImgColor);
        sImgColor.setSelection(Arrays.asList(Settings.Color.values()).indexOf(settings.getImageColor()));

        Spinner sImgSize = (Spinner)findViewById(R.id.sImgSize);
        sImgSize.setSelection(Arrays.asList(Settings.Size.values()).indexOf(settings.getImageSize()));

        if (settings.isSiteSet()) {
            EditText etSite = (EditText)findViewById(R.id.etSite);
            etSite.setText(settings.getSite());
        }

    }
}
