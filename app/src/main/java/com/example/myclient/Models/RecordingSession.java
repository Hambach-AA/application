package com.example.myclient.Models;

public class RecordingSession {
    private String date, id_client, id_master, price, service;

    public RecordingSession(){

    }

    public RecordingSession(String date, String id_client, String id_master, String price, String service) {
        this.date = date;
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