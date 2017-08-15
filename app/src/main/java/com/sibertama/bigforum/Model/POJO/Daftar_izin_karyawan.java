package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Daftar_izin_karyawan {



    String nomor_id_izin;
    String pengajuan_awal_izin;
    String pengajuan_akhir_izin;
    String jumlah_hari_izin;
    String jenis_izin;
    String nama;
    String nama_atasan;
    String status_atasan;
    String keterangan_izin;

    public Daftar_izin_karyawan(){
    }

    public Daftar_izin_karyawan(String keterangan_izin ,String status_atasan,String nama_atasan,String nama,String nomor_id_izin, String pengajuan_awal_izin, String pengajuan_akhir_izin, String jumlah_hari_izin, String jenis_izin){
        this.nomor_id_izin = nomor_id_izin;
        this.pengajuan_awal_izin = pengajuan_awal_izin;
        this.pengajuan_akhir_izin = pengajuan_akhir_izin;
        this.jumlah_hari_izin = jumlah_hari_izin;
        this.jenis_izin = jenis_izin;
        this.nama = nama;
        this.nama_atasan = nama_atasan;
        this.status_atasan = status_atasan;
        this.keterangan_izin = keterangan_izin;
    }

    public String getNomor_id_izin() {
        return nomor_id_izin;
    }

    public void setNomor_id_izin(String nomor_id_izin) {
        this.nomor_id_izin = nomor_id_izin;
    }

    public String getPengajuan_awal_izin() {
        return pengajuan_awal_izin;
    }

    public void setPengajuan_awal_izin(String pengajuan_awal_izin) {
        this.pengajuan_awal_izin = pengajuan_awal_izin;
    }

    public String getPengajuan_akhir_izin() {
        return pengajuan_akhir_izin;
    }

    public void setPengajuan_akhir_izin(String pengajuan_akhir_izin) {
        this.pengajuan_akhir_izin = pengajuan_akhir_izin;
    }

    public String getJumlah_hari_izin() {
        return jumlah_hari_izin;
    }

    public void setJumlah_hari_izin(String jumlah_hari_izin) {
        this.jumlah_hari_izin = jumlah_hari_izin;
    }

    public String getJenis_izin() {
        return jenis_izin;
    }

    public void setJenis_izin(String jenis_izin) {
        this.jenis_izin = jenis_izin;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama_atasan(String nama_atasan) {
        this.nama_atasan = nama_atasan;
    }

    public String getNama_atasan() {
        return nama_atasan;
    }

    public void setStatus_atasan(String status_atasan) {
        this.status_atasan = status_atasan;
    }

    public String getStatus_atasan() {
        return status_atasan;
    }

    public void setKeterangan_izin(String keterangan_izin) {
        this.keterangan_izin = keterangan_izin;
    }

    public String getKeterangan_izin() {
        return keterangan_izin;
    }
}
