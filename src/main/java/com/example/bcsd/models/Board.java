package com.example.bcsd.models;

public class Board {
    private final Long id;
    private final String title;

    public Board(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Board changeTitle(String title) {
        return new Board(id, title);
    }

    public Long getId() {
        return id;
    }
}