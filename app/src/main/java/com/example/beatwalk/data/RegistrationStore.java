package com.example.beatwalk.data;

import java.util.Map;

public interface RegistrationStore {
    public void addUser(String email, String password, String name, int year, String phone);
    public boolean verifyLogin(String name, String password);
    public boolean accountExists(String name);
    public String getName(String email);
    public int getClassYear(String email);
    public String getPhone(String email);
    public void modifyUser(String email, String name, int year, String phone);
    public Map<String, String> getUsers();
    public void rateUser(String email, int rating);
    public double getRating(String email);
}
