package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/14/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Daftar_Cuti_karyawan {

    String nomor_id_cuti;
    String periode_awal_cuti;
    String periode_akhir_cuti;
    String pengajuan_awal_cuti;
    String pengajuan_akhir_cuti;
    String jumlah_hari_cuti;
    String keterangan_cuti;
    String nama_atasan1_cuti;
    String nama_atasan2_cuti;
    String app_status_a1;
    String app_status_a2;
    String app_status_akhir;
    String nama_karyawan;
    String jenis_Cuti;
    String no_form;


    public Daftar_Cuti_karyawan(){
    }

    public Daftar_Cuti_karyawan(String nomor_id_cuti, String periode_awal_cuti, String periode_akhir_cuti, String pengajuan_awal_cuti, String pengajuan_akhir_cuti,String jumlah_hari_cuti, String keterangan_cuti,
                                String nama_atasan1_cuti, String nama_atasan2_cuti, String app_status_a1, String app_status_a2, String app_status_akhir, String nama_karyawan, String jenis_Cuti, String no_form){
        this.nomor_id_cuti = nomor_id_cuti;
        this.periode_awal_cuti = periode_awal_cuti;
        this.periode_akhir_cuti = periode_akhir_cuti;
        this.pengajuan_awal_cuti = pengajuan_awal_cuti;
        this.pengajuan_akhir_cuti = pengajuan_akhir_cuti;
        this.jumlah_hari_cuti = jumlah_hari_cuti;
        this.keterangan_cuti = keterangan_cuti;
        this.nama_atasan1_cuti = nama_atasan1_cuti;
        this.nama_atasan2_cuti = nama_atasan2_cuti;
        this.app_status_a1 = app_status_a1;
        this.app_status_a2 = app_status_a2;
        this.app_status_akhir = app_status_akhir;
        this.nama_karyawan = nama_karyawan;
        this.jenis_Cuti = jenis_Cuti;
        this.no_form = no_form;

    }

    public String getNomor_id_cuti() {
        return nomor_id_cuti;
    }

    public void setNomor_id_cuti(String nomor_id_cuti) {
        this.nomor_id_cuti = nomor_id_cuti;
    }

    public String getPeriode_awal_cuti() {
        return periode_awal_cuti;
    }

    public void setPeriode_awal_cuti(String periode_awal_cuti) {
        this.periode_awal_cuti = periode_awal_cuti;
    }

    public String getPeriode_akhir_cuti() {
        return periode_akhir_cuti;
    }

    public void setPeriode_akhir_cuti(String periode_akhir_cuti) {
        this.periode_akhir_cuti = periode_akhir_cuti;
    }

    public String getPengajuan_awal_cuti() {
        return pengajuan_awal_cuti;
    }

    public void setPengajuan_awal_cuti(String pengajuan_awal_cuti) {
        this.pengajuan_awal_cuti = pengajuan_awal_cuti;
    }

    public String getPengajuan_akhir_cuti() {
        return pengajuan_akhir_cuti;
    }

    public void setPengajuan_akhir_cuti(String pengajuan_akhir_cuti) {
        this.pengajuan_akhir_cuti = pengajuan_akhir_cuti;
    }

    public String getJumlah_hari_cuti() {
        return jumlah_hari_cuti;
    }

    public void setJumlah_hari_cuti(String jumlah_hari_cuti) {
        this.jumlah_hari_cuti = jumlah_hari_cuti;
    }

    public String getKeterangan_cuti() {
        return keterangan_cuti;
    }

    public void setKeterangan_cuti(String keterangan_cuti) {
        this.keterangan_cuti = keterangan_cuti;
    }

    public String getNama_atasan1_cuti() {
        return nama_atasan1_cuti;
    }

    public void setNama_atasan1_cuti(String nama_atasan1_cuti) {
        this.nama_atasan1_cuti = nama_atasan1_cuti;
    }

    public String getNama_atasan2_cuti() {
        return nama_atasan2_cuti;
    }

    public void setNama_atasan2_cuti(String nama_atasan2_cuti) {
        this.nama_atasan2_cuti = nama_atasan2_cuti;
    }

    public String getApp_status_a1() {
        return app_status_a1;
    }

    public void setApp_status_a1(String app_status_a1) {
        this.app_status_a1 = app_status_a1;
    }

    public String getApp_status_a2() {
        return app_status_a2;
    }

    public void setApp_status_a2(String app_status_a2) {
        this.app_status_a2 = app_status_a2;
    }

    public String getApp_status_akhir() {
        return app_status_akhir;
    }

    public void setApp_status_akhir(String app_status_akhir) {
        this.app_status_akhir = app_status_akhir;
    }

    public String getNama_karyawan() {
        return nama_karyawan;
    }

    public void setNama_karyawan(String nama_karyawan) {
        this.nama_karyawan = nama_karyawan;
    }

    public String getJenis_Cuti() {
        return jenis_Cuti;
    }

    public void setJenis_Cuti(String jenis_Cuti) {
        this.jenis_Cuti = jenis_Cuti;
    }

    public void setNo_form(String no_form) {
        this.no_form = no_form;
    }

    public String getNo_form() {
        return no_form;
    }
}
