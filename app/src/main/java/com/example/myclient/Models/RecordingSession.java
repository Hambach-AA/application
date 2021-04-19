package com.example.myclient.Models;

public class RecordingSession {
    private String date,day_week, start_service, end_service, id_client, id_master, price, service;

    public RecordingSession() {
    }

    public RecordingSession(String date, String day_week, String start_service, String end_service, String id_client, String id_master, String price, String service) {
        this.date = date;
        this.day_week = day_week;
        this.start_service = start_service;
        this.end_service = end_service;
        this.id_client = id_client;
        this.id_master = id_master;
        this.price = price;
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay_week() {
        return day_week;
    }

    public void setDay_week(String day_week) {
        this.day_week = day_week;
    }

    public String getStart_service() {
        return start_service;
    }

    public void setStart_service(String start_service) {
        this.start_service = start_service;
    }

    public String getEnd_service() {
        return end_service;
    }

    public void setEnd_service(String end_service) {
        this.end_service = end_service;
    }

    public String getId_client() {
        return id_client;
    }

    public void setId_client(String id_client) {
        this.id_client = id_client;
    }

    public String getId_master() {
        return id_master;
    }

    public void setId_master(String id_master) {
        this.id_master = id_master;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}