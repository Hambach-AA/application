package com.example.myclient.Models;

public class User {
    private String first_name, second_name,email, phone, uid;

    public User() {
    }

    public User(String first_name, String second_name, String email, String phone, String uid) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.email = email;
        this.phone = phone;
        this.uid = uid;
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
}
