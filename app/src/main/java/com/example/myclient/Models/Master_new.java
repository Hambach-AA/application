package com.example.myclient.Models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Objects;

public class Master_new {
    private String first_name = "";
    private String second_name = "";
    private String email = "";
    private String phone = "";
    private String address = "";
    private String info = "";
    private String uid = "";

    ArrayList<String> friends = new ArrayList<>();
    ArrayList<String> List_services = new ArrayList<>();
    ArrayList<Schedule> schedule = new ArrayList<>();

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public Master_new() {
    }

    public Master_new(String name, String email, String pass, String phone) {
        this.first_name = name;
        this.email = email;
        this.phone = phone;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getInfo() {
        return info;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public void setList_services(ArrayList<String> list_services) {
        List_services = list_services;
    }

    public ArrayList<String> getList_services() {
        return List_services;
    }

    public ArrayList<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Schedule> schedule) {
        this.schedule = schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Master_new master_new = (Master_new) o;
        return Objects.equals(uid, master_new.uid) &&
                Objects.equals(first_name, master_new.first_name) &&
                Objects.equals(second_name, master_new.second_name) &&
                Objects.equals(email, master_new.email) &&
                Objects.equals(phone, master_new.phone) &&
                Objects.equals(address, master_new.address) &&
                Objects.equals(info, master_new.info) &&
                Objects.equals(List_services, master_new.List_services) &&
                Objects.equals(schedule, master_new.schedule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, first_name, second_name, email, phone, address, info, List_services, schedule);
    }
}
