package com.example.giddu.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by giddu on 3/26/17.
 */

public class NewsItemListAdapter extends ArrayAdapter<NewsItem> {



    public NewsItemListAdapter(@NonNull Context context, List<NewsItem> newsItemList) {
        super(context, 0, newsItemList);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_view, parent, false);
        }

        NewsItem currentNewsItem = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentNewsItem.getTitle());


        TextView section = (TextView) listItemView.findViewById(R.id.section);
        section.setText(currentNewsItem.getSection());

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText(currentNewsItem.getDate());


        return listItemView;

    }
}

