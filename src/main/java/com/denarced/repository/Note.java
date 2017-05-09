package com.denarced.repository;

public class Note {
    private String uuid;
    private String value;

    public Note(String uuid, String value) {
        this.uuid = uuid;
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public Note setUuid(String uuid) {
        this.uuid = uuid;
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
