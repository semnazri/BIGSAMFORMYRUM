package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 9/21/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class Daftar_Cuti_bawahan {

    String id;
    String nama_bawahan;
    String nama_atasan1;
    String status_atasan1;
    String nama_atasan2;
    String status_atasan2;
    String tgl_pengajuan;
    String tgl_akhir;
    String keterangan;
    String jml_hari;
    String noForm;
    String nip_a1;
    String nip_a2;
    String status_akhir;

    public Daftar_Cuti_bawahan() {

    }

    public Daftar_Cuti_bawahan(String status_akhir,String nip_a1, String nip_a2,String noForm,String id, String nama_bawahan, String nama_atasan1, String status_atasan1, String nama_atasan2, String status_atasan2, String tgl_pengajuan, String tgl_akhir, String keterangan, String jml_hari) {
        this.id = id;
        this.nama_bawahan = nama_bawahan;
        this.nama_atasan1 = nama_atasan1;
        this.status_atasan1 = status_atasan1;
        this.nama_atasan2 = nama_atasan2;
        this.status_atasan2 = status_atasan2;
        this.tgl_pengajuan = tgl_pengajuan;
        this.tgl_akhir = tgl_akhir;
        this.keterangan = keterangan;
        this.jml_hari = jml_hari;
        this.noForm = noForm;
        this.nip_a1 = nip_a1;
        this.nip_a2 = nip_a2;
        this.status_akhir = status_akhir;
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

    public String getNama_atasan1() {
        return nama_atasan1;
    }

    public void setNama_atasan1(String nama_atasan1) {
        this.nama_atasan1 = nama_atasan1;
    }

    public String getStatus_atasan1() {
        return status_atasan1;
    }

    public void setStatus_atasan1(String status_atasan1) {
        this.status_atasan1 = status_atasan1;
    }

    public String getNama_atasan2() {
        return nama_atasan2;
    }

    public void setNama_atasan2(String nama_atasan2) {
        this.nama_atasan2 = nama_atasan2;
    }

    public String getStatus_atasan2() {
        return status_atasan2;
    }

    public void setStatus_atasan2(String status_atasan2) {
        this.status_atasan2 = status_atasan2;
    }

    public String getJml_hari() {
        return jml_hari;
    }

    public void setJml_hari(String jml_hari) {
        this.jml_hari = jml_hari;
    }

    public String getTgl_pengajuan() {
        return tgl_pengajuan;
    }

    public void setTgl_pengajuan(String tgl_pengajuan) {
        this.tgl_pengajuan = tgl_pengajuan;
    }

    public String getTgl_akhir() {
        return tgl_akhir;
    }

    public void setTgl_akhir(String tgl_akhir) {
        this.tgl_akhir = tgl_akhir;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public void setNoForm(String noForm) {
        this.noForm = noForm;
    }

    public String getNoForm() {
        return noForm;
    }

    public void setNip_a1(String nip_a1) {
        this.nip_a1 = nip_a1;
    }

    public String getNip_a1() {
        return nip_a1;
    }

    public void setNip_a2(String nip_a2) {
        this.nip_a2 = nip_a2;
    }

    public String getNip_a2() {
        return nip_a2;
    }

    public void setStatus_akhir(String status_akhir) {
        this.status_akhir = status_akhir;
    }

    public String getStatus_akhir() {
        return status_akhir;
    }
}
