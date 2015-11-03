package com.s10r.photosearch;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by bschmeckpeper on 11/1/15.
 */
public class SearchResultAdapter extends ArrayAdapter<SearchResult> {
    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
    }

    public SearchResultAdapter(Context context, List<SearchResult> results) {
       super(context, com.s10r.photosearch.R.layout.result_image, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchResult searchResult = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_image, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivImage = (ImageView)convertView.findViewById(R.id.ivImage);
            viewHolder.tvTitle = (TextView)convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        populate(searchResult, viewHolder);

        return convertView;
    }



    private void populate(SearchResult searchResult, ViewHolder viewHolder) {
        //viewHolder.ivResult.setImageResource(0);
        viewHolder.tvTitle.setText(Html.fromHtml(searchResult.getTitle()));
        Picasso.with(getContext())
                .load(searchResult.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.broken_image)
                .fit()
                .into(viewHolder.ivImage);
    }
}
