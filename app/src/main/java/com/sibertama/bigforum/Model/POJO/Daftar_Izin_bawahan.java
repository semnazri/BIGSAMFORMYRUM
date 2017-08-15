package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/22/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Daftar_Izin_bawahan {
    String id;
    String nama_bawahan;
    String jumlah_hari;
    String nama_atasan;
    String status_atasan;
    String noform;
    String keterangan;
    String tanggal_awal;
    String tanggal_akhir;
    String nip_atasan;
    String alasan;

    public Daftar_Izin_bawahan(){

    }

    public Daftar_Izin_bawahan(String alasan,String nip_atasan, String keterangan,String tanggal_awal,String tanggal_akhir,String id, String nama_bawahan, String jumlah_hari, String nama_atasan, String status_atasan,String noform) {
        this.id = id;
        this.nama_bawahan = nama_bawahan;
        this.jumlah_hari = jumlah_hari;
        this.nama_atasan = nama_atasan;
        this.status_atasan = status_atasan;
        this.noform = noform;
        this.keterangan = keterangan;
        this.tanggal_awal = tanggal_awal;
        this.tanggal_akhir = tanggal_akhir;
        this.nip_atasan = nip_atasan;
        this.alasan = alasan;
    }

    public String getStatus_atasan() {
        return status_atasan;
    }

    public void setStatus_atasan(String status_atasan) {
        this.status_atasan = status_atasan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_bawahan() {
        return nama_bawahan;
    }

    public void setNama_bawahan(String nama_bawahan) {
        this.nama_bawahan = nama_bawahan;
    }

    public String getJumlah_hari() {
        return jumlah_hari;
    }

    public void setJumlah_hari(String jumlah_hari) {
        this.jumlah_hari = jumlah_hari;
    }

    public String getNama_atasan() {
        return nama_atasan;
    }

    public void setNama_atasan(String nama_atasan) {
        this.nama_atasan = nama_atasan;
    }

    public void setNoform(String noform) {
        this.noform = noform;
    }

    public String getNoform() {
        return noform;
    }
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getTanggal_awal() {
        return tanggal_awal;
    }

    public void setTanggal_awal(String tanggal_awal) {
        this.tanggal_awal = tanggal_awal;
    }

    public String getTanggal_akhir() {
        return tanggal_akhir;
    }

    public void setTanggal_akhir(String tanggal_akhir) {
        this.tanggal_akhir = tanggal_akhir;
    }

    public void setNip_atasan(String nip_atasan) {
        this.nip_atasan = nip_atasan;
    }

    public String getNip_atasan() {
        return nip_atasan;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }
}
