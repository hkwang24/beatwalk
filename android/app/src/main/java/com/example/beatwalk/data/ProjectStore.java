package com.example.beatwalk.data;

import java.util.Map;

public interface ProjectStore {

    public void addProject(String id, String user, String creationDate);
    public String getUser(String id);
    public String getCreationDate(String id);
    public void modifyProject(String id, String user, String creationDate);
    public void deleteProject(String id);
    public void setUserMap();

}
