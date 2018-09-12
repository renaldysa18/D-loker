package com.example.renaldysabdojatip.dloker;

public class Acara {
    private String desc;
    private String img;
    private String idCompany;
    private String lokasi;
    private String namaAcara;
    private String tanggal;
    private String contact;
    public Acara() {
    }

    public String getContact() {
        return contact;
    }

    public String getImg() {
        return img;
    }

    public Acara(String desc, String idCompany, String lokasi, String namaAcara, String tanggal, String img, String contact) {
        this.desc = desc;
        this.idCompany = idCompany;
        this.lokasi = lokasi;
        this.namaAcara = namaAcara;
        this.tanggal = tanggal;
        this.img = img;
        this.contact = contact;
    }

    public String getDesc() {
        return desc;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getNamaAcara() {
        return namaAcara;
    }

    public String getTanggal() {
        return tanggal;
    }
}
