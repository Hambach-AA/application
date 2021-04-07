package com.example.myclient.Models;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class Master {
    private String
            first_name ="unknown",
            second_name ="unknown",
            email ="unknown",
            phone ="unknown",
            address ="unknown",
            info ="unknown";
    ArrayList<String> List_services = new ArrayList<>();
    ArrayList<Schedule> schedule = new ArrayList<>();

//    public Master(String first_name, String second_name, String email, String phone, String address, String info, ArrayList<String> List_services, ArrayList<Schedule> schedule){
//        this.first_name = first_name;
//        this.second_name = second_name;
//        this.email = email;
//        this.phone = phone;
//        this.address = address;
//        this.info = info;
//        this.List_services = List_services;
//        this.schedule = schedule;
//    }
    public Master() {
    }

    public Master(String name, String email, String pass, String phone) {
        this.first_name = name;
        this.email = email;
        //this.pass = pass;
        this.phone = phone;
    }
//    public Master(String first_name, String second_name){
//        this.first_name=first_name;
//        this.second_name=second_name;
//    }

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
}