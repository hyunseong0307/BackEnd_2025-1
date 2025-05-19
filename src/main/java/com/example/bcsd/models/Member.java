package com.example.bcsd.models;

public class Member {
    private Long id;
    private String name;
    private String email;
    private String password;

    public Member(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Member changeName(String name) {
        return new Member(id, name, email, password);
    }

    public String getEmail() {
        return email;
    }

    public Member changeEmail(String email) {
        return new Member(id, name, email, password);
    }

    public String getPassword() {
        return password;
    }

    public Member changePassword(String password) {
        return new Member(id, name, email, password);
    }
}