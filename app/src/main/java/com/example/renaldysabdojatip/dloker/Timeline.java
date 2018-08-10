package com.example.renaldysabdojatip.dloker;

public class Timeline {

    private String title, perusahaan, lokasi, detail, idCompany, idLowongan, status;

    public Timeline(){

    }

    public Timeline(String title, String perusahaan, String lokasi, String detail, String idCompany, String idLowongan, String status) {
        this.title = title;
        this.perusahaan = perusahaan;
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

    public String getPerusahaan() {
        return perusahaan;
    }

    public void setPerusahaan(String perusahaan) {
        this.perusahaan = perusahaan;
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

