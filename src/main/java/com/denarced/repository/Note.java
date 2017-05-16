package com.denarced.repository;

public class Note {
    private String uuid;
    private String title;
    private String value;

    public Note(String uuid, String title, String value) {
        this.uuid = uuid;
        this.title = title;
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public Note setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Note setValue(String value) {
        this.value = value;
        return this;
    }
}
