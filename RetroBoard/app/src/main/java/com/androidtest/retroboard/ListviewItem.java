package com.androidtest.retroboard;

import com.google.gson.annotations.SerializedName;

public class ListviewItem  {
    @SerializedName("title")
    private String title;
    @SerializedName("writer")
    private String writer;
    @SerializedName("description")
    private String description;
    @SerializedName("write_date")
    private String write_date;
    @SerializedName("hits")
    private int hits;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getWrite_date() {
        return write_date;
    }

    public int getHits() {
        return hits;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setWrite_date(String write_date) {
        this.write_date = write_date;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }


}
