package com.example.myclient.Models;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private String first_name,second_name,email,phone,uid;
    private ArrayList<String> friends;
    public User() {
    }

    public User(String first_name, String second_name, String email, String phone, String uid, ArrayList<String> friends) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.email = email;
        this.phone = phone;
        this.uid = uid;
        this.friends = friends;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String name) {
        this.first_name = name;
    }

    public String getSecond_name() { return this.second_name; }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public  String getUid() {return  this.uid; }

    public  void  setUid(String uid) {this.uid = uid; }

    public ArrayList<String> getFriends() { return this.friends; }

    public void setFriends(ArrayList<String> friends) { this.friends = friends; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(first_name, user.first_name) &&
                Objects.equals(second_name, user.second_name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(uid, user.uid) &&
                Objects.equals(friends, user.friends);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, second_name, email, phone, uid, friends);
    }
}
