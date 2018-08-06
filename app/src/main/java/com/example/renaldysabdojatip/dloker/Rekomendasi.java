package com.example.renaldysabdojatip.dloker;

public class Rekomendasi  {

    private String title, perusahaan, lokasi;

    public Rekomendasi() {
    }

    public Rekomendasi(String title, String perusahaan, String lokasi) {
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
