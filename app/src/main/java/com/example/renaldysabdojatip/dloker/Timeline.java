package com.example.renaldysabdojatip.dloker;

public class Timeline {

    private String title, perusahaan, lokasi, detail;

    public Timeline(){

    }

    public Timeline(String title, String perusahaan, String lokasi, String detail) {
        this.title = title;
        this.perusahaan = perusahaan;
        this.lokasi = lokasi;
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}

