package com.example.renaldysabdojatip.dloker;

import java.util.Date;

public class Timeline {

    private int id;
    private String title, perusahaan, lokasi;
    private int imagePerusahaan;

    public Timeline(int id, String title, String perusahaan, String lokasi, int imagePerusahaan) {
        this.id = id;
        this.title = title;
        this.perusahaan = perusahaan;
        this.lokasi = lokasi;
        this.imagePerusahaan = imagePerusahaan;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPerusahaan() {
        return perusahaan;
    }

    public String getLokasi() {
        return lokasi;
    }

    public int getImagePerusahaan() {
        return imagePerusahaan;
    }

}
