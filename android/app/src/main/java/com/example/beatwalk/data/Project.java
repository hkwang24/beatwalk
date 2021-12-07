package com.example.beatwalk.data;

import java.util.ArrayList;

public class Project {

    private String id, user, creationDate;

    public Project (String id, String user, String creationDate) {
        this.id = id;
        this.user = user;
        this.creationDate = creationDate;

    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return id + ";" + user + ";" + creationDate;
    }

    public static Project fromString(String string) {
        String[] data = string.split(";");
        if (data.length != 3) {
            return null;
        } else {
            return new Project(data[0], data[1], data[2]);
        }
    }
}
