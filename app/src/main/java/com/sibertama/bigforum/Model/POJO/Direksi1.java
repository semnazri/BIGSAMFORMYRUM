package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Direksi1 {

    String KODE_KANTOR;
    String KODE_ANAK;
    String NAMA_ANAKPERUSH;

    public Direksi1(){

    }

    public Direksi1 (String KODE_KANTOR , String KODE_ANAK, String NAMA_ANAKPERUSH){
        this.KODE_KANTOR = KODE_KANTOR;
        this.KODE_ANAK = KODE_ANAK;
        this.NAMA_ANAKPERUSH = NAMA_ANAKPERUSH;
    }

    public void setKODE_KANTOR(String KODE_KANTOR) {
        this.KODE_KANTOR = KODE_KANTOR;
    }

    public String getKODE_KANTOR() {
        return KODE_KANTOR;
    }

    public void setKODE_ANAK(String KODE_ANAK) {
        this.KODE_ANAK = KODE_ANAK;
    }

    public String getKODE_ANAK() {
        return KODE_ANAK;
    }

    public void setNAMA_ANAKPERUSH(String NAMA_ANAKPERUSH) {
        this.NAMA_ANAKPERUSH = NAMA_ANAKPERUSH;
    }

    public String getNAMA_ANAKPERUSH() {
        return NAMA_ANAKPERUSH;
    }


}
