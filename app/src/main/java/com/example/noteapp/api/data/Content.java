package com.example.noteapp.api.data;

public class Content {

    private String name;
    private String publishAt;
    private String noteUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getNoteUrl() {
        return noteUrl;
    }

    public void setNoteUrl(String noteUrl) {
        this.noteUrl = noteUrl;
    }
}
