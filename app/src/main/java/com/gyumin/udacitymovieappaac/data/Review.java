package com.gyumin.udacitymovieappaac.data;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("author")
    String author;
    @SerializedName("content")
    String content;
    @SerializedName("id")
    String id;
    @SerializedName("url")
    String url;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
