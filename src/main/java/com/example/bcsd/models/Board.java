package com.example.bcsd.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Article> articles = new ArrayList<>();
    public Board() {

    }

    public Board(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addArticle(Article article) {
        articles.add(article);
        article.setBoard(this);
    }

    public void removeArticle(Article article) {
        articles.remove(article);
        article.setBoard(null);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}