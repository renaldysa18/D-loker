package com.example.renaldysabdojatip.dloker;

public class Bookmark {

    private String title, perusahaan, lokasi, detail, uid, idCompany, idLowongan, status, pict, nama, alamat, email;

    public Bookmark() {
    }

    public Bookmark(String title, String perusahaan, String lokasi, String detail, String idCompany, String idLowongan, String status, String pict, String nama, String alamat, String email) {
        this.title = title;
        this.perusahaan = perusahaan;
        this.lokasi = lokasi;
        this.detail = detail;
        this.idCompany = idCompany;
        this.idLowongan = idLowongan;
        this.status = status;
        this.pict = pict;
        this.nama = nama;
        this.alamat = alamat;
        this.email = email;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPict() {
        return pict;
    }

    public void setPict(String pict) {
        this.pict = pict;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
