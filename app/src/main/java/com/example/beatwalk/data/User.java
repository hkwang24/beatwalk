package com.example.beatwalk.data;

public class User {
    private String email, name, phoneNumber, password;
    private int classYear;
    private double rating;

    public User (String email, String password, String name, int year, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.classYear = year;
        this.phoneNumber = phoneNumber;
        rating = 5;
    }

    public User (String email, String password, String name, int year, String phoneNumber, double rating) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.classYear = year;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public int getClassYear() {
        return classYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setClassYear(int classYear) {
        this.classYear = classYear;
    }

    @Override
    public String toString() {
        return email + ";" + password + ";" + name + ";" + phoneNumber + ";" + classYear;
    }

    public static User fromString(String s) {
        String[] data = s.split(";");
        if (data.length != 5) {
            return null;
        } else {
            return new User(data[0], data[1], data[2], Integer.valueOf(data[4]), data[3]);
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
