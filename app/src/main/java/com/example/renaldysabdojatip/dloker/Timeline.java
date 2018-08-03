package com.example.renaldysabdojatip.dloker;

public class Timeline {

    private String title;
    private String perusahaan;
    private String lokasi;

    public Timeline(){

    }

    public Timeline(String title, String perusahaan, String lokasi) {
        this.title = title;
        this.perusahaan = perusahaan;
        this.lokasi = lokasi;
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
}
