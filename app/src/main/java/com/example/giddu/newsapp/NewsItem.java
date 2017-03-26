package com.example.giddu.newsapp;

import java.net.URL;

/**
 * Created by giddu on 3/26/17.
 */

public class NewsItem {

    private String title;
    private String link;
    private String date;
    private String section;

    public NewsItem(String title, String link, String date, String section) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public String getSection(){
        return section;
    }

}
