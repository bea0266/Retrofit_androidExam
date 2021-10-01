package com.androidtest.retroboard;

public class ListviewItem {
    private String title;
    private String writer;
    private String write_date;
    private int hits = 0;

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
