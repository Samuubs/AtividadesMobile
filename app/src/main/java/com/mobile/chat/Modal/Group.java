package com.mobile.chat.Modal;

public class Group {
    private String id;
    private String username;

    public Group(String id, String username){
        this.id=id;
        this.username=username;
    }
    public Group(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
