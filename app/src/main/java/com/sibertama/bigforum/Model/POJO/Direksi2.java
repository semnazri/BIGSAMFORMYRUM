package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/20/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Direksi2 {

    String ID;
    String KODE_DIREKTORAT;
    String NAMA_DIREKTORAT;

    public Direksi2(){

    }

    public Direksi2 (String ID, String KODE_DIREKTORAT, String NAMA_DIREKTORAT){
        this.ID = ID;
        this.KODE_DIREKTORAT = KODE_DIREKTORAT;
        this.NAMA_DIREKTORAT = NAMA_DIREKTORAT;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setKODE_DIREKTORAT(String KODE_DIREKTORAT) {
        this.KODE_DIREKTORAT = KODE_DIREKTORAT;
    }

    public String getKODE_DIREKTORAT() {
        return KODE_DIREKTORAT;
    }

    public void setNAMA_DIREKTORAT(String NAMA_DIREKTORAT) {
        this.NAMA_DIREKTORAT = NAMA_DIREKTORAT;
    }

    public String getNAMA_DIREKTORAT() {
        return NAMA_DIREKTORAT;
    }
}
