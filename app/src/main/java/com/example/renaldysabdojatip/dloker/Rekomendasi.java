package com.example.renaldysabdojatip.dloker;

public class Rekomendasi  {

    private String title, kategori, lokasi, detail;

    public Rekomendasi() {
    }

    public Rekomendasi(String title, String kategori, String lokasi, String detail) {
        this.title = title;
        this.kategori = kategori;
        this.lokasi = lokasi;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
