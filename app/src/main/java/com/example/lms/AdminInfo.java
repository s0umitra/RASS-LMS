package com.example.lms;

public class AdminInfo {
    private String name,username,pass;
    private int id;

    public int getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }

    public String getUsername() {
        return username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
