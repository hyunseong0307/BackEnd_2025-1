package com.example.bcsd.dtos;

public class boardDto {
    private Long id;
    private String name;

    public boardDto() {
    }

    public boardDto(Long id, String title) {
        this.id = id;
        this.name = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String title) {
        this.name = title;
    }
}
