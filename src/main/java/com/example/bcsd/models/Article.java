package com.example.bcsd.models;

public class Article {
    private final Long id;
    private final String title;
    private final Long authorId;
    private final Long boardId;
    private final String createdDate;
    private final String content;
    private final String modifiedDate;

    public Article(Long id, String title, Long authorId, Long boardId, String createdAt, String content, String modifiedDate) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.boardId = boardId;
        this.createdDate = createdAt;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Article changeArticle(String title, String content, String modifiedDate) {
        return new Article(id, title, authorId, boardId, createdDate, content, modifiedDate);
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getBoardId() {
        return boardId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public Article updateModificationDate(String date) {
        return new Article(id, title, authorId, boardId, createdDate, content, date);
    }

    public String getContent() {
        return content;
    }
}