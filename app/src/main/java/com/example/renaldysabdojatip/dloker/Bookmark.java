package com.example.renaldysabdojatip.dloker;

public class Bookmark {

    private String title, perusahaan, lokasi, detail, uid;

    public Bookmark() {
    }

    public Bookmark(String title, String perusahaan, String lokasi, String detail) {
        this.title = title;
        this.perusahaan = perusahaan;
        this.lokasi = lokasi;
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

    public String getDetail() {
        return detail;
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

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
