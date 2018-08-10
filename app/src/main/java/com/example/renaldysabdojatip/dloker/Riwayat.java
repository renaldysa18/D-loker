package com.example.renaldysabdojatip.dloker;

public class Riwayat {

    private String title, kategori, lokasi, detail,idCompany, idLowongan, status;

    public Riwayat() {
    }

    public Riwayat(String title, String kategori, String lokasi, String detail, String idCompany, String idLowongan, String status) {
        this.title = title;
        this.kategori = kategori;
        this.lokasi = lokasi;
        this.detail = detail;
        this.idCompany = idCompany;
        this.idLowongan = idLowongan;
        this.status = status;
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

    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getIdLowongan() {
        return idLowongan;
    }

    public void setIdLowongan(String idLowongan) {
        this.idLowongan = idLowongan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
