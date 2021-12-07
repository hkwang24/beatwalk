package com.example.beatwalk.data;

import java.util.HashMap;
import java.util.Map;

public class RegistrationStoreLocal implements RegistrationStore {

    HashMap<String, User> data;
    static RegistrationStore instance = new RegistrationStoreLocal();


    private RegistrationStoreLocal () {
        data  = new HashMap<String, User>();
    }

    public static RegistrationStore getInstance() {
        return instance;
    }

    @Override
    public void addUser(String email, String password, String name, int year, String phone) {
        data.put(email, new User(email, password, name, year, phone));
    }

    @Override
    public boolean verifyLogin(String name, String password) {
        if (name == null) {
            return false;
        }
        User user = data.get(name);
        if (user == null) {
            return false;
        }
        String passwordActual = user.getPassword();
        if (password == null || passwordActual == null || !password.equals(passwordActual)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean accountExists(String name) {
        if (name != null) {
            return data.containsKey(name);
        }
        throw new NullPointerException("Null username");
    }

    @Override
    public String getName(String email) {
        return data.get(email).getName();
    }

    @Override
    public int getClassYear(String email) {
        return data.get(email).getClassYear();
    }

    @Override
    public String getPhone(String email) {
        return data.get(email).getPhoneNumber();
    }

    @Override
    public void modifyUser(String email, String name, int year, String phone) {
        User currentUser = data.get(email);
        currentUser.setName(name);
        currentUser.setClassYear(year);
        currentUser.setPhoneNumber(phone);
    }

    @Override
    public Map<String, String> getUsers() {
        return null;
    }

    @Override
    public void rateUser(String email, int rating) {

    }

    @Override
    public double getRating(String email) {
        return 0;
    }
}