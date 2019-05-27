package com.example.route.Adapter;

public class Sessiondata {

    public static Sessiondata Instance = null;

    public static Sessiondata getInstance(){

        if (Instance == null){
            Instance = new Sessiondata();
        }
        return Instance;
    }

    String src_and_des = "";
    String src_and_des_ID = "";

    String id_stops = "";
    String name_stops = "";

    public String getId_stops() {
        return id_stops;
    }

    public void setId_stops(String id_stops) {
        this.id_stops = id_stops;
    }

    public String getName_stops() {
        return name_stops;
    }

    public void setName_stops(String name_stops) {
        this.name_stops = name_stops;
    }

    public String getSrc_and_des_ID() {
        return src_and_des_ID;
    }

    public void setSrc_and_des_ID(String src_and_des_ID) {
        this.src_and_des_ID = src_and_des_ID;
    }

    public String getSrc_and_des() {
        return src_and_des;
    }

    public void setSrc_and_des(String src_and_des) {
        this.src_and_des = src_and_des;
    }
}
